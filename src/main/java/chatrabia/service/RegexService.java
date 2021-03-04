package chatrabia.service;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.bot.AssocWordCitation;
import chatrabia.util.ChatBotData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegexService {
    private final ChatBotData cbd = ChatBotData.getInstance();

    public AssocPatternResponse checkMessageWithAllData(String message){
        ArrayList<AssocPatternResponse> patternResponse = cbd.getPatternResponse();
        ArrayList<AssocPatternResponse> patternResponseMatch = new ArrayList<>();

        for (int i = 0; i < patternResponse.size() ; i++) {
            AssocPatternResponse assocPR = patternResponse.get(i);
            Pattern patt = Pattern.compile(assocPR.getPattern());
            Matcher matcher = patt.matcher(message);
            if (matcher.find()) {
                patternResponseMatch.add(assocPR);
            }
        }

        if(patternResponseMatch.size() > 0){
            return patternResponseMatch.get(0);
            // TODO : Que faire si plusieurs match ???
        }
        return null;
    }

    public String checkMessageWithServices(String message){
        ArrayList<String> patternResponse = cbd.getServices();
        ArrayList<String> patternResponseMatch = new ArrayList<>();

        for (int i = 0; i < patternResponse.size() ; i++) {
            String assocPR = patternResponse.get(i);
            Pattern patt = Pattern.compile(assocPR);
            Matcher matcher = patt.matcher(message);
            if (matcher.find()) {
                patternResponseMatch.add(assocPR);
            }
        }

        if(patternResponseMatch.size() > 0){
            return patternResponseMatch.get(0);
            // TODO : Que faire si plusieurs match ???
        }
        return null;
    }

    public AssocWordCitation checkMessageWithFortune(String message){
        ArrayList<AssocWordCitation> assocCitation = cbd.getAssocCitations();
        ArrayList<AssocWordCitation> patternResponseMatch = new ArrayList<>();

        for (int i = 0; i < assocCitation.size() ; i++) {
            AssocWordCitation assocPR = assocCitation.get(i);
            Pattern patt = Pattern.compile(assocPR.getPatternFr());
            Matcher matcher = patt.matcher(message);
            if (matcher.find()) {
                patternResponseMatch.add(assocPR);
            }
        }

        if(patternResponseMatch.size() > 0){
            return patternResponseMatch.get(0);
            // TODO : Que faire si plusieurs match ???
        }
        return null;
    }
    public boolean check2Words(String wordA, String pattern){
        Pattern patt = Pattern.compile(pattern);
        Matcher matcher = patt.matcher(wordA);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public String deleteAntiSlashN(String sentence){
        return sentence.replaceAll("\\\\[a-z]"," ");
    }
}
