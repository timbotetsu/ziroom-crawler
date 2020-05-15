package timbo.homebrew.ziroomcrawler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ManualTriggerController {

    private final Crawler crawler;

    public ManualTriggerController(Crawler crawler) {
        this.crawler = crawler;
    }

    @GetMapping("trigger")
    public void trigger() {
        crawler.start();
    }

}
