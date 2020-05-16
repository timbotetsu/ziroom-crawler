package timbo.homebrew.ziroomcrawler.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BCAttachment {

    private String title;
    private String text;
    private String color;
    private List<Image> images = new ArrayList<>();

}
