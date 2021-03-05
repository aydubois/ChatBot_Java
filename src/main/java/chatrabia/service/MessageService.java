package chatrabia.service;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.domain.Message;
import chatrabia.util.ChatBotData;
import chatrabia.util.MyRunnable;
import chatrabia.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService extends MyRunnable {

    private static Logger log = LogManager.getRootLogger();

    private final ShifumiService shifumiService;
    private final RegexService regexService;
    private final CitationService citationService;
    private final KaamelottService kaamelottService;
    private final AleatoireService aleatoireService;
    private final ChuckNorrisService chuckNorrisService;
    private final DogImageService dogImageService;
    private final InsulteRusseService insulteRusseService;
    private final MiamService miamService;
    private final PenduService penduService;
    private final AdvertisingService advertisingService;

    private final ChatBotData cbd = ChatBotData.getInstance();

    public MessageService(ShifumiService shifumiService,InsulteRusseService insulteRusseService,
                          RegexService regexService, CitationService citationService, KaamelottService kaamelottService,
                          AleatoireService aleatoireService, ChuckNorrisService chuckNorrisService,
                          DogImageService dogImageService, MiamService miamService, PenduService penduService,
                          AdvertisingService advertisingService){
        this.shifumiService = shifumiService;
        this.regexService = regexService;
        this.citationService = citationService;
        this.kaamelottService = kaamelottService;
        this.aleatoireService = aleatoireService;
        this.chuckNorrisService = chuckNorrisService;
        this.dogImageService = dogImageService;
        this.insulteRusseService = insulteRusseService;
        this.miamService = miamService;
        this.penduService = penduService;
        this.advertisingService = advertisingService;
    }

    public Message getMessageUser(String message, String user){
        Message msg = this.createMessage(user, message);

        this.advertisingService.writeAWish(user, message);

        if(message.equals("unevachedansunpresquimangedesfourmisarcenciel")){

            int randomInt = Util.getRandom(0,10);
            boolean isAdvertising = false;

            if(randomInt < 3){
               String advertising = this.advertisingService.getMessage(user);

               if(advertising != null && !"".equals(advertising)) {
                   msg.addBotMessage(advertising);
                   isAdvertising = true;
               }
            }

            if(!isAdvertising && randomInt <8) {
                String aleatoire = aleatoireService.get();
                aleatoire = regexService.deleteAntiSlashN(aleatoire);
                msg.addBotMessage(aleatoire);
            }
            else if(!isAdvertising){
                String[] aleatoire = aleatoireService.getAleatoireJoieCode();
                if(aleatoire != null && aleatoire.length >= 2){
                    aleatoire[0] = regexService.deleteAntiSlashN(aleatoire[0]);
                    msg.addBotMessage(aleatoire[0]);
                    msg.addBotMessage(aleatoire[1]);
                }
            }
            msg.setUserMessage("");
        }
        else if(message.matches(chuckNorrisService.getPattern())){
            String chuckMessage = chuckNorrisService.get();
            msg.addBotMessage(chuckMessage);

        }else if(message.matches(miamService.getPattern())){
            String chuckMessage = miamService.get();
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
            Thread threadPendu = new Thread(penduService.createRunnable(msg, getServiceActivated()));
            threadPendu.start();

            Thread threadShifumi = new Thread(shifumiService.createRunnable(msg, getServiceActivated()));
            threadShifumi.start();

            Thread threadRegex = new Thread(this.createRunnable(msg));
            threadRegex.start();

            Thread threadCitation = new Thread(citationService.createRunnable(msg));
            threadCitation.start();
            try {
                threadShifumi.join();
                threadRegex.join();
                threadCitation.join();
                threadPendu.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("thread interrupted");
            }
        }
        if(msg.getBotMessage().size() == 0){
            KaamelottService.Citation citation = kaamelottService.getKaamelott();

            if(!"".equals(citation.getCitation())) msg.addBotMessage(citation.getCitation());
            if(!"".equals(citation.getPersonnage())) msg.setBotName(citation.getPersonnage());
        }

        return msg;

    }

    public String getServiceActivated(){
        if (shifumiService.getActivated()){
            return "Shifumi";
        }
        if(penduService.getActivated()){
            return "pendu";
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
