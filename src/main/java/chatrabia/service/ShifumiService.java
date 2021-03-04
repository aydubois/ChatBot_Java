package chatrabia.service;

import chatrabia.domain.Message;
import chatrabia.domain.Shifumi;
import chatrabia.util.MyRunnable;
import chatrabia.util.Util;
import org.springframework.stereotype.Service;

@Service
public class ShifumiService {

    private final RegexService regexService;
    private Shifumi shifumi = new Shifumi();
    private String[] response = {"Pierre", "Feuille", "Ciseaux", "Pierre", "Feuille", "Ciseaux", "Puit"}; // diminue les chances d'avoir puit xD
    private boolean isActivated = false;


    public ShifumiService(RegexService regexService){
        this.regexService = regexService;
    }

    public Runnable createRunnable( Message message, String serviceActivated){
        return () -> myRun(message, serviceActivated);
    }
    protected void myRun(Message message, String serviceActivated){
        String messageUser = message.getUserMessage();
        // Si pas activé && messageUser != Shifumi -> on passe notre chemin, Thread terminé
        if(!checkIsActivated() && !checkStarting(messageUser)){
            Thread.currentThread().interrupt();
        }else
        // Si pas activé && messageUser == Shifumi -> let's go
        if(!checkIsActivated() && checkStarting(messageUser)){
            if (serviceActivated == null) {
                message.addBotMessage(startShifumi());
                Thread.currentThread().interrupt();
            }
        }else {
            //Si activé -> verif choix utilisateur
            String choixUser = checkResponseUser(messageUser);
            if (choixUser == null) {
                message.addBotMessage("Je n'ai pas compris ta réponse.Réessaie.\n" +
                        "Choix possibles : Pierre / Feuille / Ciseaux");
                Thread.currentThread().interrupt();
            } else if (choixUser.equals("Stop")) {
                new Thread(this::resetShifumi).start();
                message.addBotMessage("Ok, on arrête de jouer. Mais j'allais gagner ...");
                Thread.currentThread().interrupt();
            } else if (choixUser.equals("Puit")) {
                message.addBotMessage("Tu n'as pas le droit, le puit c'est pour les noobs.Réessaie.");
                Thread.currentThread().interrupt();
            } else {

                //messageUser ok -> du coup choix du bot, setScore
                message.setOption("shifumi");
                String choixBot = randomResponse();
                message.addBotMessage(choixBot);
                if(choixBot.equals("Puit"))
                    message.addBotMessage("Oui, moi j'ai le droit de faire Puit, parce que c'est comme ça.");
                boolean botWin = setScoreBot(choixUser, choixBot);
                boolean userWin = setScoreUser(choixUser, choixBot);
                String reponseFin = checkTotalScore();
                if (reponseFin != null) {
                    message.addBotMessage(reponseFin);
                }else if(botWin){
                    message.addBotMessage("+1 point pour moi.");
                }else if(userWin){
                    message.addBotMessage("+1 point pour toi");
                }else{
                    message.addBotMessage("Ex æquo");
                }
            }
        }
    }
    public boolean getActivated(){
        return isActivated;
    }
    private boolean checkStarting(String messageUser){
        return regexService.check2Words(messageUser, "[S|s]h[i|y]fum[i|y]");
    }
    private String startShifumi(){
        isActivated = true;
        return "Let's go ! J'adore le Shifimu. Je te laisse commencer écris Pierre / Feuille ou Ciseaux";
    }
    private boolean checkIsActivated(){
        return isActivated;
    }

    private String checkResponseUser(String messageUser){
        if(regexService.check2Words(messageUser, "[P|p]ier"))
            return "Pierre";
        if(regexService.check2Words(messageUser, "[F|f]euil"))
            return "Feuille";
        if(regexService.check2Words(messageUser, "[C|c]is[ea|o]"))
            return "Ciseaux";
        if(regexService.check2Words(messageUser, "[P|p]ui"))
            return "Puit";
        if(regexService.check2Words(messageUser, "([S|s]top)|(arr[e|ê]t)"))
            return "Stop";
        return null;
    }
    private String randomResponse(){
        return response[Util.getRandom(0, response.length-1)];
    }

    private boolean setScoreBot(String messageUser, String responseBot){
        if((messageUser.equals("Pierre") && responseBot.equals("Feuille"))||
                (messageUser.equals("Ciseaux") && responseBot.equals("Pierre"))||
                (messageUser.equals("Feuille") && responseBot.equals("Ciseaux"))||
                (!messageUser.equals("Feuille") && responseBot.equals("Puit"))

        ){
            shifumi.setScoreBot(shifumi.getScoreBot()+1);
            return true;
        }
        return false;
    }
    private boolean setScoreUser(String messageUser, String responseBot){
        if((responseBot.equals("Pierre") && messageUser.equals("Feuille"))||
                (responseBot.equals("Ciseaux") && messageUser.equals("Pierre"))||
                (responseBot.equals("Feuille") && messageUser.equals("Ciseaux"))||
               ( messageUser.equals("Puit") && !responseBot.equals("Feuille"))
        ){
            shifumi.setScoreUser(shifumi.getScoreUser()+1);
            return true;
        }
        return false;
    }
    private String checkTotalScore(){
        String text = null;
        if(shifumi.getScoreBot() == 3){
            text =  "Score finaux :\n Toi -> "+shifumi.getScoreUser()+" // Moi -> "+shifumi.getScoreBot()+"\n" +
                    "J'ai gagné !";
        }
        if(shifumi.getScoreUser() == 3){
            text =  "Score finaux :\n Toi -> "+shifumi.getScoreUser()+" // Moi -> "+shifumi.getScoreBot()+"\n" +
                    "Bravo, tu m'as battu !";
        }
        if(shifumi.getScoreBot() == 3 || shifumi.getScoreUser() == 3 ){
            new Thread(this::resetShifumi).start();
        }
        return text;
    }

    private void resetShifumi(){
        shifumi.setScoreUser(0);
        shifumi.setScoreBot(0);
        isActivated = false;
    }


}
