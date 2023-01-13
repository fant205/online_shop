package com.alexey.shop.carts.configs;

import com.alexey.shop.carts.properties.ProductServiceIntegrationProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
@EnableConfigurationProperties(ProductServiceIntegrationProperties.class)
@RequiredArgsConstructor
public class AppConfig {

    private final ProductServiceIntegrationProperties productServiceIntegrationProperties;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient productServiceWebClient(){
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandler(new ReadTimeoutHandler(productServiceIntegrationProperties.getReadTimeout()));
                    connection.addHandler(new ReadTimeoutHandler(productServiceIntegrationProperties.getWriteTimeout()));
                });

        return WebClient.builder()
                .baseUrl(productServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
