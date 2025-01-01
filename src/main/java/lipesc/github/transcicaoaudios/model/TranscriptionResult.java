package lipesc.github.transcicaoaudios.model;

//import com.assemblyai.api.resources.transcripts.types.Chapter;

import java.util.List;

public class TranscriptionResult {
    private String transcription;
    private String summary;
    private List<Chapter> chapters;
    private String language;

    public TranscriptionResult(String transcription, String summary, List<Chapter> chapters, String language) {
        this.transcription = transcription;
        this.summary = summary;
        this.chapters = chapters;

    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public  static class Chapter {
        private  int start;
        private int end;
        private  String headline;

    public Chapter(int start, int end, String headline) {
        this.start = start;
        this.end = end;
        this.headline = headline;
    }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getHeadline() {
            return headline;
        }

        public void setHeadline(String headline) {
            this.headline = headline;
        }
    }


}
