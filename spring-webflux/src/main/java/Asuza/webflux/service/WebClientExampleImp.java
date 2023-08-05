package Asuza.webflux.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.Executors;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@Slf4j
@Service
public class WebClientExampleImp implements WebClientExample {
    @Autowired
    private WebClient webClient;

    @Override
    public void crawlPage(String url) {
        Mono<String> response =  webClient.get().uri(url)
                                .retrieve()
                                .bodyToMono(String.class);
        System.out.println(response.block());
    }
}
