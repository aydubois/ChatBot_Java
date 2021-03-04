package chatrabia.bot;

import java.util.ArrayList;

public class AssocWordCitation {
    private String patternFr = "";
    private String wordEnglishAPI = "";

    public void setPatternFr(String patternFr){
        this.patternFr = patternFr;
    }
    public void setWordAPI(String wordEnglishAPI){
        this.wordEnglishAPI = wordEnglishAPI;
    }

    public String getPatternFr() {
        return patternFr;
    }
    public String getWordAPI() {
        return wordEnglishAPI;
    }

}
