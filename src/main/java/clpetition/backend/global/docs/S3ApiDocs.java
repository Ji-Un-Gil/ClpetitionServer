package clpetition.backend.global.docs;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Hidden
public interface S3ApiDocs {

    ResponseEntity<String> create(MultipartFile multipartFile);
}
