package timbo.homebrew.ziroomcrawler;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import timbo.homebrew.ziroomcrawler.domain.BCNotification;

@Component
public class BCSender {

    private final RestTemplate restTemplate;

    public BCSender(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void send(BCNotification notification) {
        restTemplate.postForEntity("https://hook.bearychat.com/=bw65h/incoming/27c56a011b356f84081ed1d34c6134e7", notification, String.class);
    }

}
