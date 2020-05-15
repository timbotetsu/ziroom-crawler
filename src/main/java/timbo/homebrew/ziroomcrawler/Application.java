package timbo.homebrew.ziroomcrawler;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import timbo.homebrew.ziroomcrawler.config.ZiroomCrawlerConfig;

@Configuration
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZiroomCrawlerConfig.class).bannerMode(Banner.Mode.OFF).run(args);
    }

}
