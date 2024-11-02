package clpetition.backend.member.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.docs.AuthInstagramApiDocs;
import clpetition.backend.member.docs.HandleInstagramCallbackApiDocs;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.service.AuthInstagramService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthInstagramController implements
        AuthInstagramApiDocs,
        HandleInstagramCallbackApiDocs {

    @Value("${spring.security.oauth2.client.registration.instagram.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.instagram.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.instagram.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.instagram.authorization-uri}")
    private String authUri;

    @Value("${spring.security.oauth2.client.provider.instagram.long-authorization-uri}")
    private String longAuthUri;

    @Value("${instagram.callback-uri}")
    private String callbackUri;

    private final AuthInstagramService authInstagramService;

    @GetMapping("/instagram")
    public void authInstagram(
            @FindMember Member member, HttpServletResponse response
    ) throws IOException {
        String authUrl = authUri
                + "?client_id=" + clientId
                + "&redirect_uri=" + callbackUri
                + "&scope=user_profile,user_media"
                + "&response_type=code";

        response.sendRedirect(authUrl);
    }

    @GetMapping("/instagram/callback")
    public ResponseEntity<BaseResponse<Void>> handleInstagramCallback(
            @FindMember Member member, @RequestParam String code
    ) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", callbackUri);
        formData.add("code", code);

        String longLivedAccessToken = authInstagramService.handleInstagramCallback(tokenUri, formData, longAuthUri, clientSecret);
        authInstagramService.updateInstagramAccessToken(member, longLivedAccessToken);

        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }
}