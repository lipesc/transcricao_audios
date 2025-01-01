package lipesc.github.transcicaoaudios.controller;

import lipesc.github.transcicaoaudios.service.TranscriptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audio")
public class Controller {
    private TranscriptService transcriptService;

    public ResponseEntity<?> handleFileUpload(){
        try {
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
