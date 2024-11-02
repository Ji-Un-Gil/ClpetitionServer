package clpetition.backend.instagram.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.instagram.docs.GetInstagramMediaApiDocs;
import clpetition.backend.instagram.dto.response.GetInstagramMediaResponse;
import clpetition.backend.instagram.service.InstagramService;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.service.AuthInstagramService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instagram")
public class InstagramController implements
        GetInstagramMediaApiDocs {

    @Value("${spring.security.oauth2.client.provider.instagram.user-info-uri}")
    private String userInfoUri;

//    private final RestTemplate restTemplate;
    private final InstagramService instagramService;
    private final AuthInstagramService authInstagramService;

//    @GetMapping("/user_id")
//    public ResponseEntity<?> getInstagramUserId(@RequestParam String accessToken) {
//        try {
//            String userUrl = "https://graph.instagram.com/me/accounts?access_token=" + accessToken;
//            ResponseEntity<String> response = restTemplate.getForEntity(userUrl, String.class);
//
//            // Instagram Business 계정이 연결된 페이지 정보를 받아서 처리
//            return ResponseEntity.ok(response.getBody());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }

    @GetMapping("/media")
    public ResponseEntity<BaseResponse<GetInstagramMediaResponse>> getInstagramMedia(
            @FindMember Member member
    ) {
        String accessToken = authInstagramService.getInstagramAccessToken(member);

        String userInfoUrl = userInfoUri
                + "/media"
                + "?fields=id,caption,media_type,media_url,thumbnail_url,timestamp"
                + "&access_token=" + accessToken;

        return BaseResponse.toResponseEntityContainsResult(instagramService.getInstagramMedia(userInfoUrl));
    }

//    @GetMapping("/hashtag/{hashtagName}")
//    public ResponseEntity<?> getMediaByHashtag(@PathVariable String hashtagName) {
//        try {
//            String userId = "8488266107925416";
//            // 1. 해시태그 ID를 먼저 검색
//            String searchUrl = "https://graph.facebook.com/ig_hashtag_search?user_id=" + userId + "&q=" + hashtagName;
//            ResponseEntity<String> hashtagResponse = restTemplate.getForEntity(searchUrl, String.class);
//            String hashtagId = instagramService.extractHashtagId(hashtagResponse.getBody());
//
//            // 2. 해시태그 ID로 미디어 검색
//            String mediaUrl = "https://graph.facebook.com/" + hashtagId + "/recent_media?user_id=" + userId + "&fields=id,media_type,media_url,caption";
//            ResponseEntity<String> mediaResponse = restTemplate.getForEntity(mediaUrl, String.class);
//
//            return ResponseEntity.ok(mediaResponse.getBody());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
}
