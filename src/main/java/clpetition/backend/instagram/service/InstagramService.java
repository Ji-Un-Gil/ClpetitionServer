package clpetition.backend.instagram.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.instagram.dto.response.GetInstagramMediaResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InstagramService {

    private final ObjectMapper objectMapper;

    public String extractHashtagId(String responseBody) throws Exception {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        // JSON에서 id 값 추출
        return rootNode.path("id").asText();
    }

    /**
     * 사용자 미디어 조회
     * */
    public GetInstagramMediaResponse getInstagramMedia(String userInfoUrl) {
        WebClient webClient = WebClient.create();
        String jsonResponse = webClient.get()
                .uri(userInfoUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return toGetInstagramMediaResponse(
                filterMediaUrlList(jsonResponse)
        );
    }

    /**
     * 사용자 정보 중 미디어 URL만 필터링
     * */
    private List<String> filterMediaUrlList(String jsonResponse) {
        List<String> mediaUrlList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = rootNode.get("data");

            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode mediaNode : dataNode) {
                    JsonNode mediaUrlNode = mediaNode.get("media_url");
                    if (mediaUrlNode != null) {
                        String mediaUrl = mediaUrlNode.asText().replace("\\/", "/");
                        mediaUrlList.add(mediaUrl);
                    }
                }
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.BAD_GATEWAY);
        }

        return mediaUrlList;
    }

    /**
     * 사용자 미디어 조회 to dto
     * */
    private GetInstagramMediaResponse toGetInstagramMediaResponse(List<String> mediaUrlList) {
        return GetInstagramMediaResponse.builder()
                .mediaUrlList(mediaUrlList)
                .build();
    }
}
