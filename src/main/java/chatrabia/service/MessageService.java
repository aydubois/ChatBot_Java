package chatrabia.service;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.domain.Kaamelott;
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

    private final ShifumiService shifumiService;
    private final JokeService jokeService;
    private final RegexService regexService;
    private final CitationService citationService;
    private final KaamelottService kaamelottService;
    private final AleatoireService aleatoireService;
    private final ChuckNorrisService chuckNorrisService;
    private final DogImageService dogImageService;

    private final ChatBotData cbd = ChatBotData.getInstance();

    public MessageService(ShifumiService shifumiService, JokeService jokeService, RegexService regexService, CitationService citationService, KaamelottService kaamelottService, AleatoireService aleatoireService, ChuckNorrisService chuckNorrisService, DogImageService dogImageService){
        this.shifumiService = shifumiService;
        this.jokeService = jokeService;
        this.regexService = regexService;
        this.citationService = citationService;
        this.kaamelottService = kaamelottService;
        this.aleatoireService = aleatoireService;
        this.chuckNorrisService = chuckNorrisService;
        this.dogImageService = dogImageService;
    }

    public Message getMessageUser(String message, String user){
        Message msg = this.createMessage(user, message);
        String multiService = this.checkMultiServiceActivated(msg);
        System.out.println("MULTISERVICE " +multiService);
        if(multiService != null){
            getResponseMultiService(msg, multiService);
        }else if(message.equals("unevachedansunpresquimangedesfourmisarcenciel")){
            String aleatoire = aleatoireService.getAleatoire();
            msg.addBotMessage(aleatoire);
            msg.setUserMessage("");
        }
        else if(message.matches(ChuckNorrisService.getPattern())){
            String chuckMessage = chuckNorrisService.getChuckNorrisRandomJoke();
            msg.addBotMessage(chuckMessage);

        } else if(message.matches(DogImageService.getPattern())) {
            String dogUrl = dogImageService.getRandomDogImage();
            msg.addBotMessage(dogUrl);

        } else {

            Thread threadShifumi = new Thread(shifumiService.createRunnable(msg));
            threadShifumi.start();
            Thread threadJoke = new Thread(jokeService.createRunnable(msg));
            threadJoke.start();
            Thread threadRegex = new Thread(this.createRunnable(msg));
            threadRegex.start();
            Thread threadCitation = new Thread(citationService.createRunnable(msg));
            threadCitation.start();
            try {
                threadShifumi.join();
                threadJoke.join();
                threadRegex.join();
                threadCitation.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("thread interrupted");
            }
        }
        if(msg.getBotMessage().size() == 0){
            String kaamelottCitation = kaamelottService.getKaamelott();
            msg.addBotMessage(kaamelottCitation);
        }

        return msg;

    }

    public Runnable createRunnable( Message message){
        return () -> myRun(message);
    }
    private String checkMultiServiceActivated(Message message){
        String serviceActivated = getServiceActivated();
        System.out.println("Service activé ==>" +serviceActivated);
        if(serviceActivated == null){
            return null;
        }
        String patternRegex = regexService.checkMessageWithServices(message.getUserMessage());
        System.out.println("PATTERN REGEX  "+patternRegex);
        if(patternRegex == null){
            return null;
        }
        if(regexService.check2Words(serviceActivated, patternRegex)){
            return null;
        }
        return patternRegex;
    }
    private String getServiceActivated(){
        if(jokeService.getActivated()){
            return "Joke";
        }else if (shifumiService.getActivated()){
            return "Shifumi";
        }
        return null;
    }
    private void getResponseMultiService(Message message, String pattern){
        if(getServiceActivated().equals("Shifumi")){
            if(pattern.equals("([B|b]lague)|([J|j]oke)")){
                message.addBotMessage("Désolé, mais je ne fais pas de blague en milieu de partie de Shifumi. Tu choisis quoi : Pierre, Feuille ou Ciseaux ?");
            }
        }
        if(getServiceActivated().equals("Joke")){
            if(pattern.equals("[S|s]h[i|y]fum[i|y]")){
                message.addBotMessage("Tu n'as pas répondu à ma question :'(");
                jokeService.setActivated(false);
            }
        }
    }

    private void myRun(Message message){
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
