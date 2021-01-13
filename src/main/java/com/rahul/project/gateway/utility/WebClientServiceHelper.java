package com.rahul.project.gateway.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.rahul.project.gateway.dto.webclient.WebRequest;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Component
public class WebClientServiceHelper {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ObjectMapper objectMapper = new ObjectMapper().registerModules(
            new ParameterNamesModule(), new Jdk8Module(), new JavaTimeModule()
    );

    public <T> Mono<T> buildPostRequest(WebRequest request, Class<T> claz) {
        WebClient webClient = WebClient.builder().clientConnector(buildClientHttpConnector())
                .baseUrl(request.getApiUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, request.getContentType())
                .build();
        Map<String, String> reqHeaders = Objects.nonNull(request.getHeaders()) ? request.getHeaders() : new HashMap<>();

        if (request.getJsonPaylad() != null) {
            try {
                String requestPayload = objectMapper.writeValueAsString(request.getJsonPaylad());
                request.setPayload(requestPayload);
            } catch (Exception e) {
                logger.error("Error while building request body ");
            }
        }

        logger.info("Request body :: {}", request.getPayload());

        return webClient.post()
                .uri(getUri(request))
                .headers(httpHeaders -> httpHeaders.setAll(reqHeaders))
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request.getPayload()), String.class)
                .retrieve()
                .bodyToMono(claz)
                .timeout(Duration.ofMillis(request.getTimeOutMs()))
                .onErrorResume(e -> {
                    logger.error("Exception occurred {} :: {}", e.getClass(), e.getMessage());
                    return Mono.just(claz.cast("Timeout Error"));
                });
    }

    private URI getUri(WebRequest request) {
        URI uri;
        if (request.isBuildURI()) {
            uri = URI.create(buildURI(request.getQueryParams(), request.getApiUrl()));
        } else {
            uri = UriComponentsBuilder.fromUri(URI.create(request.getApiUrl())).queryParams(convertToMultiMap(request.getQueryParams())).build().toUri();
        }
        return uri;
    }

    private MultiValueMap<String, String> convertToMultiMap(Map<String, String> data) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        if (data != null) {
            multiValueMap.clear();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                multiValueMap.add(entry.getKey(), entry.getValue());
            }
        }
        return multiValueMap;
    }

    private String buildURI(Map<String, String> data, String baseURL) {
        String url = baseURL + "?";
        if (data != null) {
            int i = 0;
            for (Map.Entry<String, String> entry : data.entrySet()) {
                if (i == 0) {
                    url = url + entry.getKey() + "=" + entry.getValue();
                } else {
                    url = url + "&" + entry.getKey() + "=" + entry.getValue();
                }
                i++;
            }
        }
        return url;
    }

    private ClientHttpConnector buildClientHttpConnector() {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option
                                (ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10))
                                        .addHandlerLast(new WriteTimeoutHandler(10))));
        return new ReactorClientHttpConnector(httpClient);
    }


}
