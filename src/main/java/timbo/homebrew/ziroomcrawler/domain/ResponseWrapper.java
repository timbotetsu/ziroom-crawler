package timbo.homebrew.ziroomcrawler.domain;

import lombok.Data;

@Data
public class ResponseWrapper {

    private int code;
    private String message;
    private PageData data;

}
