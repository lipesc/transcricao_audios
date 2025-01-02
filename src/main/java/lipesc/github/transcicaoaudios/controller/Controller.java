package lipesc.github.transcicaoaudios.controller;

import lipesc.github.transcicaoaudios.model.TranscriptionResult;
import lipesc.github.transcicaoaudios.service.TranscriptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/audio")
public class Controller {

    private TranscriptService transcriptService;

    public Controller(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
        try {
            TranscriptionResult result =transcriptService.processFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        }
    }

