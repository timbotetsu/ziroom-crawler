package timbo.homebrew.ziroomcrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import timbo.homebrew.ziroomcrawler.domain.*;
import timbo.homebrew.ziroomcrawler.repository.RoomMapper;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
public class Crawler {

    private final Logger logger = LoggerFactory.getLogger(Crawler.class);

    private final String ZIROOM_CRAWLER_URL = "http://sh.ziroom.com/map/room/list?min_lng=121.53762&max_lng=121.555874&min_lat=31.199771&max_lat=31.209006&clng=121.546747&clat=31.204389&zoom=18&p=%s";
    private final String BLUE_COLOR = "#0000FF";
    private final String GREEN_COLOR = "#008000";
    private final String RED_COLOR = "#FF0000";

    private final RestTemplate restTemplate;
    private final RoomMapper roomMapper;
    private final BCSender sender;

    private final String PRICE_CHANGE_TEXT_TEMPLATE = "价格变动，从%s%s%s";

    public Crawler(RestTemplateBuilder restTemplateBuilder, RoomMapper roomMapper, BCSender bcSender) {
        this.restTemplate = restTemplateBuilder.build();
        this.roomMapper = roomMapper;
        this.sender = bcSender;
    }

    @Scheduled(cron = "0 0 0/1 * * *") // every hour
    public void start() {
        ResponseWrapper responseWrapper = restTemplate.getForObject(String.format(ZIROOM_CRAWLER_URL, 1), ResponseWrapper.class);
        if (Objects.nonNull(responseWrapper) && Objects.nonNull(responseWrapper.getData())) {
            IntStream.rangeClosed(1, responseWrapper.getData().getPages()).forEach(this::crawl);
        }
    }

    private void crawl(int page) {
        logger.info("crawl page {}", page);
        ResponseWrapper responseWrapper = restTemplate.getForObject(String.format(ZIROOM_CRAWLER_URL, page), ResponseWrapper.class);
        if (Objects.nonNull(responseWrapper) && Objects.nonNull(responseWrapper.getData()) && responseWrapper.getData().getRooms().size() > 0) {
            responseWrapper.getData().getRooms().forEach(this::handle);
        }
    }

    private void handle(Room room) {
        Room oldRoom = roomMapper.findOne(room.getId());
        if (Objects.isNull(oldRoom)) { // new room, save it
            save(room);
        } else {
            compare(oldRoom, room);
        }
    }

    private void save(Room room) {
        room.setPhoto("http:" + room.getPhoto());
        room.setDetailUrl("http:" + room.getDetailUrl());

        BCNotification notification = constructNewRoomNotification(room);
        sender.send(notification);

        roomMapper.save(room);
    }

    private void compare(Room oldR, Room newR) {
        if (oldR.getPrice() == newR.getPrice()) {
            return;
        }
        newR.setPhoto("http:" + newR.getPhoto());
        newR.setDetailUrl("http:" + newR.getDetailUrl());

        BCNotification notification = constructPriceChangeNotification(oldR, newR, oldR.getPrice() < newR.getPrice());
        sender.send(notification);

        roomMapper.deleteOne(oldR.getId());
        save(newR);
    }

    private BCNotification constructNewRoomNotification(Room room) {
        BCAttachment attachment = new BCAttachment();
        attachment.setTitle(room.getName() + "|" + room.getDescription());
        attachment.setText(room.getPrice() + room.getPriceUnit());
        attachment.setColor(BLUE_COLOR);
        attachment.setImages(Arrays.asList(new Image(room.getPhoto())));

        BCNotification notification = new BCNotification();
        notification.setText("新房源通知");
        notification.setAttachments(Arrays.asList(attachment));
        return notification;
    }

    private BCNotification constructPriceChangeNotification(Room oldR, Room newR, boolean raise) {
        BCAttachment attachment = new BCAttachment();
        attachment.setTitle(String.format(PRICE_CHANGE_TEXT_TEMPLATE, oldR.getPrice(), raise ? "涨价为" : "降价为", newR.getPrice()));
        attachment.setText(newR.getName() + "|" + newR.getDescription());
        attachment.setColor(raise ? RED_COLOR : GREEN_COLOR);
        attachment.setImages(Arrays.asList(new Image(newR.getPhoto())));

        BCNotification notification = new BCNotification();
        notification.setText("价格变动通知");
        notification.setAttachments(Arrays.asList(attachment));
        return notification;
    }

}
