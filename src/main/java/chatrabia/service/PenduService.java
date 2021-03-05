package chatrabia.service;

import chatrabia.domain.Message;
import chatrabia.util.MyRunnable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PenduService {
    private boolean isActivated = false;
    private String word = "pasteque";
    private String pattern = "pendu";
    private int totalError = 0;
    private RegexService regexService;
    private ArrayList<Integer> listIndexFound= new ArrayList<>();
    public  PenduService(RegexService regexService ){
        this.regexService = regexService;
    }

    public boolean getActivated() {
        return isActivated;
    }

    public Runnable createRunnable( Message message, String serviceActivated){
        return () -> myRun(message, serviceActivated);
    }

    protected void myRun(Message message, String serviceActivated) {
        if(!isActivated && !regexService.check2Words(message.getUserMessage(),pattern)){
            Thread.currentThread().interrupt();
        }else

        if(!isActivated && regexService.check2Words(message.getUserMessage(),pattern)){
            if (serviceActivated == null) {
                isActivated = true;
                String[] wordSplit = word.split("");

                String messageBot = "";
                for (int i = 0; i < wordSplit.length; i++) {
                    if (listIndexFound.contains(i)) {
                        messageBot += wordSplit[i] + " ";
                    } else {
                        messageBot += "_ ";
                    }
                }
                message.addBotMessage(messageBot);
            }
            Thread.currentThread().interrupt();
        }else if(isActivated && regexService.check2Words(message.getUserMessage(),"[Ss]top") ){
            isActivated = false;
            message.addBotMessage("Ok, pendu mis en attente. Tapes pendu pour reprendre");
            Thread.currentThread().interrupt();
        }else
        if(isActivated){
            message.setOption("pendu");
            String messageUser = message.getUserMessage();
            if(messageUser.length() > 1){
                String messageBot = "Ecris une seule lettre à la fois stp.";
                message.addBotMessage(messageBot);
                Thread.currentThread().interrupt();
            }else{
                String[] wordSplit = word.split("");
                boolean matching = false;
                for (int i = 0; i < wordSplit.length; i++) {
                    if(wordSplit[i].equals(messageUser)){
                       listIndexFound.add(i);
                       matching = true;
                    }
                }
                if(!matching){
                    totalError++;
                    if(totalError < 7){
                        message.addBotMessage("#NOP");
                    }

                }else
                if(wordSplit.length == listIndexFound.toArray().length){
                    message.addBotMessage("Bravo !");
                    isActivated = false;
                }
                String messageBot = "";
                if(totalError >= 7){
                    message.addBotMessage("Perdu");
                    message.addBotMessage("pendu7.png");
                    isActivated = false;
                    totalError = 0;
                    listIndexFound.clear();
                    for (int i = 0; i < wordSplit.length; i++) {
                        messageBot += wordSplit[i] + " ";
                    }
                    message.addBotMessage(messageBot);
                }else {
                    for (int i = 0; i < wordSplit.length; i++) {
                        if (listIndexFound.contains(i)) {
                            messageBot += wordSplit[i] + " ";
                        } else {
                            messageBot += "_ ";
                        }
                    }
                    if(totalError > 0){
                        message.addBotMessage("pendu"+totalError+".png");
                    }
                    message.addBotMessage(messageBot);
                }
            }
        }
    }
}
