package org.xdl.crawler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xdl.crawler.Constants.WebSiteConstants;
import org.xdl.crawler.crawlers.BaseCrawler;
import org.xdl.crawler.data.BaseItem;
import org.xdl.crawler.data.SSEKCProjectDynamicItem;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class CrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class,args);
    }
}
