package chatrabia.service;

import chatrabia.bot.AssocWordCitation;
import chatrabia.domain.Message;
import chatrabia.exception.ExternalAPIException;
import chatrabia.util.ChatBotData;
import chatrabia.util.MyRunnable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CitationService extends MyRunnable{
    private boolean isActivated = false;
    private HttpService httpService;
    private RegexService regexService;
    private static final String fortuneApi = "https://fortuneapi.herokuapp.com/";
    private ArrayList<AssocWordCitation> assocCitation = ChatBotData.getInstance().getAssocCitations();

    public CitationService(@Qualifier("httpService") HttpService httpService, RegexService regexService) {
        this.httpService = httpService;
        this.regexService = regexService;
    }

    public String getCitationByWord(String word) {
        try {
            String citation= httpService.sendGetRequest(fortuneApi+word, String.class, null);

            return citation;

        } catch (ExternalAPIException e) {
            e.printStackTrace();
            System.out.println("API FORTUNE DOESN'T WORK");
        }
        return "";
    }
    
    @Override
    protected void myRun(Message message){
        AssocWordCitation assocWord = regexService.checkMessageWithFortune(message.getUserMessage());
        if(assocWord == null){
            Thread.currentThread().interrupt();
        }else{
            String messageBot = getCitationByWord(assocWord.getWordAPI());
            messageBot = regexService.deleteAntiSlashN(messageBot);
            message.addBotMessage(messageBot);
        }

    }
}
