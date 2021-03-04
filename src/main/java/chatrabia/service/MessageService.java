package chatrabia.service;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.domain.Message;
import chatrabia.util.ChatBotData;
import chatrabia.util.MyRunnable;
import chatrabia.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService extends MyRunnable {

    private final ShifumiService shifumiService;
    private final RegexService regexService;
    private final CitationService citationService;
    private final KaamelottService kaamelottService;
    private final AleatoireService aleatoireService;
    private final ChuckNorrisService chuckNorrisService;
    private final DogImageService dogImageService;
    private final InsulteRusseService insulteRusseService;

    private final ChatBotData cbd = ChatBotData.getInstance();

    public MessageService(ShifumiService shifumiService,InsulteRusseService insulteRusseService, RegexService regexService, CitationService citationService, KaamelottService kaamelottService, AleatoireService aleatoireService, ChuckNorrisService chuckNorrisService, DogImageService dogImageService){
        this.shifumiService = shifumiService;
        this.regexService = regexService;
        this.citationService = citationService;
        this.kaamelottService = kaamelottService;
        this.aleatoireService = aleatoireService;
        this.chuckNorrisService = chuckNorrisService;
        this.dogImageService = dogImageService;
        this.insulteRusseService = insulteRusseService;
    }

    public Message getMessageUser(String message, String user){
        Message msg = this.createMessage(user, message);

        if(message.equals("unevachedansunpresquimangedesfourmisarcenciel")){
            int randomInt = Util.getRandom(0,10);
            if(randomInt <8){
                String aleatoire = aleatoireService.get();
                msg.addBotMessage(aleatoire);
            }else{
                String[] aleatoire = aleatoireService.getAleatoireJoieCode();
                msg.addBotMessage(aleatoire[0]);
                msg.addBotMessage(aleatoire[1]);
            }
            msg.setUserMessage("");
        }
        else if(message.matches(chuckNorrisService.getPattern())){
            String chuckMessage = chuckNorrisService.get();
            msg.addBotMessage(chuckMessage);

        } else if(message.matches(dogImageService.getPattern())) {
            String dogUrl = dogImageService.get();
            msg.addBotMessage(dogUrl);

        }else if(message.matches(insulteRusseService.getPattern())) {
            String[] arrayEmoji = {"\uD83D\uDD95", "\uD83D\uDCA3","\uD83D\uDE21","\uD83D\uDD2B" };
            String emoji = arrayEmoji[Util.getRandom(0,arrayEmoji.length-1)];
            String insulte = insulteRusseService.get()+" "+emoji;
            msg.addBotMessage(insulte);

        } else {

            Thread threadShifumi = new Thread(shifumiService.createRunnable(msg));
            threadShifumi.start();
            Thread threadRegex = new Thread(this.createRunnable(msg));
            threadRegex.start();
            Thread threadCitation = new Thread(citationService.createRunnable(msg));
            threadCitation.start();
            try {
                threadShifumi.join();
                threadRegex.join();
                threadCitation.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("thread interrupted");
            }
        }
        if(msg.getBotMessage().size() == 0){
            String kaamelottCitation = kaamelottService.get();
            msg.addBotMessage(kaamelottCitation);
        }

        return msg;

    }

    private String getServiceActivated(){
        if (shifumiService.getActivated()){
            return "Shifumi";
        }
        return null;
    }

    @Override
    protected void myRun(Message message){
        if(getServiceActivated() == null){
            String messageUser = message.getUserMessage();
            AssocPatternResponse assocPR = regexService.checkMessageWithAllData(messageUser);
            System.out.println("THREADREGEX ="+assocPR);
            if(assocPR != null){
                ArrayList<String> templates = assocPR.getTemplates();
                int rdmInt = Util.getRandom(0, templates.size()-1);
                message.addBotMessage(templates.get(rdmInt));
            }
        }
    }
    private Message createMessage(String user, String message){
        Message msg = new Message();
        msg.setUserName(user);
        msg.setUserMessage(message);
        return msg;
    }

}
