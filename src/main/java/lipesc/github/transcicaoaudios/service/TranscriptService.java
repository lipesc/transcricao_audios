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

    String KEY = System.getenv("apikey");
    private String formatTime(int milliseconds) {
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
    public TranscriptionResult processFile(MultipartFile file) throws Exception {
        System.out.println("Iniciando processamento do arquivo...");
        System.out.println("Nome do arquivo recebido: " + file.getOriginalFilename());

        AssemblyAI client = AssemblyAI.builder()
                .apiKey(KEY)
                .build();

        File tempfile = File.createTempFile("audio-", ".mp3");
        file.transferTo(tempfile);
        System.out.println("Arquivo salvo temporariamente em: " + tempfile.getAbsolutePath());

        TranscriptOptionalParams params = TranscriptOptionalParams.builder()
                .summarization(true)
                .summaryModel(SummaryModel.INFORMATIVE)
                .summaryType(SummaryType.BULLETS)
//                .autoChapters(true)
                .languageDetection(true)
                .build();

        Transcript transcript = client.transcripts().transcribe(tempfile, params);

        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            System.err.println("Erro no processamento do áudio: " + transcript.getError().orElse("Erro desconhecido"));
            throw new RuntimeException("Erro no processamento do áudio: " + transcript.getError().orElse("Erro desconhecido"));
        }

        System.out.println("Transcrição concluída com sucesso.");


        List<TranscriptionResult.Chapter> chapters = transcript.getChapters()
                .orElse(List.of())
                .stream()
                .map(ch -> {
                    String startFormatted = formatTime(ch.getStart());
                    String endFormatted = formatTime(ch.getEnd());
                return new TranscriptionResult.Chapter(startFormatted, endFormatted, ch.getHeadline());
                })
                .collect(Collectors.toList());

        String languageCode = transcript.getLanguageCode()
                .map(TranscriptLanguageCode::toString)
                .orElse("null");

        return new TranscriptionResult(
                transcript.getText().orElse(""),
                transcript.getSummary().orElse(""),
                chapters,
                languageCode
        );
    }

}
