package timbo.homebrew.ziroomcrawler.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageData {

    private List<Room> rooms = new ArrayList<>();
    private int total;
    private int pages;

}
