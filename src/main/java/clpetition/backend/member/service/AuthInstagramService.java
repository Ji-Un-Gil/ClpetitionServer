package clpetition.backend.member.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.InstagramAccessToken;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.repository.InstagramAccessTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthInstagramService {

    private final InstagramAccessTokenRepository instagramAccessTokenRepository;

    /**
     * 인스타그램 access token 업데이트
     * */
    public void updateInstagramAccessToken(Member member, String instagramAccessToken) {
        Long id = member.getId();
        updateInstagramAccessToken(id, instagramAccessToken);
    }

    /**
     * 인스타그램 access token 조회
     * */
    public String getInstagramAccessToken(Member member) {
        Long id = member.getId();
        return getInstagramAccessToken(id);
    }

    /**
     * 인스타그램 callback으로 access token 발급
     * */
    public String handleInstagramCallback(String tokenUri, MultiValueMap<String, String> formData, String longAuthUri, String clientSecret) {
        String shortLivedAccessToken = getShortLivedAccessToken(tokenUri, formData);
        return getLongLivedAccessToken(longAuthUri, clientSecret, shortLivedAccessToken);
    }

    /**
     * 인스타그램 access token 저장 or 최신화
     * */
    private void updateInstagramAccessToken(Long id, String instagramAccessToken) {
        instagramAccessTokenRepository.findById(id)
                .ifPresentOrElse(
                        token -> token.updateInstagramAccessToken(instagramAccessToken),
                        () -> instagramAccessTokenRepository.save(InstagramAccessToken.createInstagramAccessToken(id, instagramAccessToken))
                );
    }

    /**
     * 인스타그램 access token 조회 (예외처리)
     * */
    private String getInstagramAccessToken(Long id) {
        Optional<InstagramAccessToken> instagramAccessToken = instagramAccessTokenRepository.findById(id);

        if (instagramAccessToken.isEmpty())
            throw new BaseException(BaseResponseStatus.INVALID_INSTAGRAM_ACCESS_TOKEN);

        return instagramAccessToken.get().getToken();
    }

    /**
     * 인스타그램 callback으로 access token 발급 1) 짧은 수명의 access token 발급
     * */
    private String getShortLivedAccessToken(String tokenUri, MultiValueMap<String, String> formData) {
        WebClient webClient = WebClient.create();

        Mono<String> shortLivedAccessTokenMono = webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(response);
                        return jsonNode.get("access_token").asText();
                    } catch (JsonProcessingException e) {
                        throw new BaseException(BaseResponseStatus.BAD_GATEWAY);
                    }
                });

        return shortLivedAccessTokenMono.block();
    }

    /**
     * 인스타그램 callback으로 access token 발급 2) 긴 수명(60일)의 access token 발급
     * */
    private String getLongLivedAccessToken(String longAuthUri, String clientSecret, String shortLivedAccessToken) {
        WebClient webClient = WebClient.create();
        String longLivedTokenUri = longAuthUri
                + "?grant_type=ig_exchange_token"
                + "&client_secret=" + clientSecret
                + "&access_token=" + shortLivedAccessToken;

        Mono<String> longLivedAccessTokenMono = webClient.get()
                .uri(longLivedTokenUri)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(response);
                        return jsonNode.get("access_token").asText();
                    } catch (JsonProcessingException e) {
                        throw new BaseException(BaseResponseStatus.BAD_GATEWAY);
                    }
                });

        return longLivedAccessTokenMono.block();
    }
}
