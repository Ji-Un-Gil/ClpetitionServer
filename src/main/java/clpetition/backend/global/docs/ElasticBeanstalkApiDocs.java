package clpetition.backend.global.docs;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;

@Hidden
public interface ElasticBeanstalkApiDocs {

    ResponseEntity<String> getHealthCheck();
}
