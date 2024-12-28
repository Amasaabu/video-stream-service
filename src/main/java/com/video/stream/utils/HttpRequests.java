package com.video.stream.utils;


import com.video.stream.models.HttpResponses.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Component
@AllArgsConstructor
public class HttpRequests {

   RestTemplate restTemplate;
    public void CustomizedRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        var timeout = Duration.ofMillis(2000);
        this.restTemplate = restTemplateBuilder.connectTimeout(timeout).readTimeout(timeout)
                .build();
    }
    public ResponseEntity<AuthResponse> sendHttpWithToken(String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        var entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, AuthResponse.class);
    }

    public <T> ResponseEntity<AuthResponse> ResponseEntitydoHttpPostRequest(String url, T requestPayLoad) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        var entity = new HttpEntity<>(requestPayLoad, headers);

        return restTemplate.postForEntity(url, entity, AuthResponse.class);
    }
}

