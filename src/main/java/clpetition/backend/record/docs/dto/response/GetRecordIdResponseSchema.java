package clpetition.backend.record.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "등반 기록 ID 응답")
public interface GetRecordIdResponseSchema {

    @Schema(description = "등반 기록 ID", example = "3")
    Long recordId();

    @Schema(description = "첨부 이미지 URL 목록", example = """
                                                        [
                                                            "url",
                                                            "url2"
                                                        ]
                                                        """)
    List<String> imageUrls();
}
