package chatrabia.service;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.domain.Message;
import chatrabia.util.ChatBotData;
import chatrabia.util.Matching;
import chatrabia.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageService {
    private final ChatBotData cbd = ChatBotData.getInstance();
    public Message getMessageUser(String message, String user){
        String messageBot = this.regexMsg(message);
        Message msg = this.createMessage(user, message, messageBot);
        return msg;
    }

    private String regexMsg(String message){
        //Get all Patterns
        ArrayList<AssocPatternResponse> patternResponse = cbd.getPatternResponse();
        AssocPatternResponse assocPRUseRegex = null;
        AssocPatternResponse assocPRUseMatching = null;

        for (int i = 0; i < patternResponse.size() ; i++) {
            // 1er Test => Regex
            if(assocPRUseRegex == null)
            assocPRUseRegex = this.testRegex(message,patternResponse.get(i));
            // 2eme Test => Matching
            if(assocPRUseMatching == null)
                assocPRUseMatching = this.testMatching(message, patternResponse.get(i));

        }
        if(assocPRUseRegex == null && assocPRUseMatching == null ){
            return " J'ai pas compris.";
        }
        if(assocPRUseRegex == assocPRUseMatching || assocPRUseMatching != null){
            if(assocPRUseMatching.getOption() == ""){
                ArrayList<String> templates = assocPRUseMatching.getTemplates();
                int randomInt = Util.getRandom(0, templates.size()-1);
                return templates.get(randomInt);
            }else{
                System.out.println("OPTION MATCHING "+assocPRUseMatching.getOption());
                return this.checkOption(assocPRUseMatching);
            }
        }
        if(assocPRUseRegex != null && assocPRUseRegex.getOption() != ""){
            System.out.println("OPTION REGEX "+assocPRUseRegex.getOption());
            return this.checkOption((assocPRUseRegex));
        }
        ArrayList<String> templates = assocPRUseRegex.getTemplates();
        int randomInt = Util.getRandom(0, templates.size()-1);
        return templates.get(randomInt);

    }
    private AssocPatternResponse testRegex(String message, AssocPatternResponse assocPR){
        System.out.println(assocPR.getPattern());
        Pattern patt = Pattern.compile(assocPR.getPattern());
        Matcher matcher = patt.matcher(message);
        if(matcher.find()){
            return assocPR;
        }
        return null;
    }
    private AssocPatternResponse testMatching(String message, AssocPatternResponse assocPR){
        String pattern = assocPR.getPattern();
        int percentage = Matching.lock_match(pattern, message);
        if(percentage > 50){
            return assocPR;
        }
        return null;
    }
    private Message createMessage(String user, String message, String messageBot){
        Message msg = new Message();
        msg.setUserName(user);
        msg.setUserMessage(message);
        msg.setBotMessage(messageBot);
        return msg;
    }
    private String checkOption(AssocPatternResponse assocPR){
        return "blah";
    }

}
