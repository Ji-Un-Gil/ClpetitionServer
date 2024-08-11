package clpetition.backend.global.controller;

import clpetition.backend.global.docs.ElasticBeanstalkApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class ElasticBeanstalkHealthCheck implements ElasticBeanstalkApiDocs {
    @GetMapping
    public ResponseEntity<String> getHealthCheck() {
        return ResponseEntity.ok("ElasticBeanstalk success");
    }
}
