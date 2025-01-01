package lipesc.github.transcicaoaudios.service;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;
import lipesc.github.transcicaoaudios.model.TranscriptionResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranscriptService {

    private static final String KEY = System.getenv("apiKey");

    public TranscriptionResult processFile(MultipartFile file) throws Exception {
        AssemblyAI cleint = AssemblyAI.builder()
                .apiKey(KEY)
                .build();

        File tempfile = File.createTempFile("audio-", ".mp3");
        file.transferTo(tempfile);

        TranscriptOptionalParams params = TranscriptOptionalParams.builder()
                .summarization(true)
                .summaryModel(SummaryModel.INFORMATIVE)
                .summaryType(SummaryType.BULLETS)
                .autoChapters(true)
                .languageDetection(true)
                .build();


        Transcript transcript = cleint.transcripts().transcribe(tempfile, params);

        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            throw new RuntimeException("Erro no processamento audio: " + transcript.getError().orElse("Erro desconhecido"));
        }

        List<TranscriptionResult.Chapter> chapters = transcript.getChapters()
                .orElse(List.of())
                .stream()
                .map(ch -> new TranscriptionResult.Chapter(ch.getStart(), ch.getEnd(), ch.getHeadline()))
                .collect(Collectors.toList());

        return new TranscriptionResult(
                transcript.getText().orElse(""),
                transcript.getSummary().orElse(""),
                chapters,
                String.valueOf(transcript.getLanguageCode().orElse(TranscriptLanguageCode.valueOf("Unknown")))
        );
    }
}
