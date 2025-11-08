package com.c4.routy.domain.plan.infrastructure;

import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteRequest;
import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class KakaoApiServiceImpl implements KakaoApiService {

    @Value("${maps.kakao.api_key}")
    private String kakaoApiKey;

    @Value("${maps.kakao.directions_uri}")
    private String kakaoDirectionsUri;

    @Value("${maps.kakao.waypoints_uri}")
    private String kakaoWaypointsUri;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public KakaoRouteResponse getDirection(KakaoRouteRequest request) {

        try{
            String url = kakaoWaypointsUri;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            HttpEntity<KakaoRouteRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<KakaoRouteResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    KakaoRouteResponse.class
            );

            return response.getBody();
        } catch (Exception e){
            throw new RuntimeException("Kakao API 호출 중 오류 발생", e);
        }
    }
}
