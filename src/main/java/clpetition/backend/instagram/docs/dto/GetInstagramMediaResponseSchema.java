package clpetition.backend.instagram.docs.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "인스타그램 사용자 미디어 조회 응답")
public interface GetInstagramMediaResponseSchema {

    @Schema(description = "미디어 URL 리스트", example = """
                                                        [
                                                            "https://example.com/1.jpg",
                                                            "https://example.com/2.mp4"
                                                        ]
                                                        """)
    List<String> mediaUrlList();
}
