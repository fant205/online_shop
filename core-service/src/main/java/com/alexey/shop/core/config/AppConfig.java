package com.alexey.shop.core.config;

import com.alexey.shop.core.properties.CartServiceIntegrationProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
@EnableConfigurationProperties(CartServiceIntegrationProperties.class)
@RequiredArgsConstructor
public class AppConfig {

    private final CartServiceIntegrationProperties cartServiceIntegrationProperties;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, cartServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandler(new ReadTimeoutHandler(cartServiceIntegrationProperties.getReadTimeout()));
                    connection.addHandler(new ReadTimeoutHandler(cartServiceIntegrationProperties.getWriteTimeout()));
                });

        return WebClient.builder()
                .baseUrl(cartServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
