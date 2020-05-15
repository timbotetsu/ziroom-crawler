package timbo.homebrew.ziroomcrawler.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BCNotification {

    private String text;
    private List<BCAttachment> attachments = new ArrayList<>();

}
