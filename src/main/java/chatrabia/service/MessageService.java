package chatrabia.service;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.domain.Message;
import chatrabia.util.ChatBotData;
import chatrabia.util.Matching;
import chatrabia.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService {
    private final ChatBotData cbd = ChatBotData.getInstance();
    public Message getMessageUser(String message, String user){
        String messageBot = this.regexMsg(message);
        Message msg = this.createMessage(user, message, messageBot);
        return msg;
    }

    private String regexMsg(String message){
        ArrayList<AssocPatternResponse> patternResponse = cbd.getPatternResponse();
        AssocPatternResponse assocPRUse = null;
        for (int i = 0; i < patternResponse.size() ; i++) {
            String pattern = patternResponse.get(i).getPattern();
            int percentage = Matching.lock_match(pattern, message);
            if(percentage > 50){
                assocPRUse = patternResponse.get(i);
                break;
            }
        }
        if(assocPRUse != null){
            ArrayList<String> templates = assocPRUse.getTemplates();
            int randomInt = Util.getRandom(0, templates.size());
            System.out.printf(templates.get(randomInt));
            return templates.get(randomInt);
        }
        return " J'ai pas compris.";
    }

    private Message createMessage(String user, String message, String messageBot){
        Message msg = new Message();
        msg.setUserName(user);
        msg.setUserMessage(message);
        msg.setBotMessage(messageBot);
        return msg;
    }


}
