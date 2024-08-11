package clpetition.backend.global.controller;

import clpetition.backend.global.docs.S3ApiDocs;
import clpetition.backend.global.infra.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/health/file")
@RequiredArgsConstructor
public class S3HealthCheck implements S3ApiDocs {

    private final FileService fileService;

    @PostMapping("")
    public ResponseEntity<String> create(
            @RequestPart(value = "testImage", required = false) MultipartFile multipartFile) {

        String fileName = "";
        if (multipartFile != null) { // 파일 업로드한 경우에만

            try { // 파일 업로드
                fileName = fileService.upload(multipartFile, "test"); // S3 버킷의 images 디렉토리 안에 저장됨
                return ResponseEntity.ok(fileName);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return ResponseEntity.ok("Least One Image Required.");
    }
}

