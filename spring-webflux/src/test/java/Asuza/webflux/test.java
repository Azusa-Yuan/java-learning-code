package Asuza.webflux;

import Asuza.webflux.service.WebClientExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
@Slf4j
@SpringBootTest
public class test {

    @Autowired
    WebClientExample webClientExample;

    @Test
    void contextLoads() {
        webClientExample.crawlPage("http://www.princessconnect.so-net.tw/news/newsDetail/2398");
        log.info("Hello world!");
    }
}
