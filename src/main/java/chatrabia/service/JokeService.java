package chatrabia.service;

import chatrabia.domain.Message;
import chatrabia.util.Util;
import org.springframework.stereotype.Service;

@Service
public class JokeService extends MyRunnable{
    private boolean isActivated = false;
    private String favouriteJoke = null;
    private String[] allJokes = {"ChuckNorris", "Geek", "KnockKnock"};
    private final RegexService regexService;
    private final ChuckNorrisService chuckNorrisService;

    public JokeService(RegexService regexService, ChuckNorrisService chuckNorrisService){
        this.regexService = regexService;
        this.chuckNorrisService = chuckNorrisService;
    }

    @Override
    protected void myRun(Message message){
        String messageUser = message.getUserMessage();
        //Si pas activé && messageUser != blague/joke -> Fin thread
        if(!isActivated && !checkStarting(messageUser)){
            Thread.currentThread().interrupt();
        }else
        //Si pas activé && messageUser == blague/joke  && on connait pas son style de blague
        if(!isActivated && checkStarting(messageUser) && favouriteJoke == null){
            message.addBotMessage(startJoke());
            Thread.currentThread().interrupt();
        }else if(!isActivated && checkStarting(messageUser)&& favouriteJoke != null){
            getJoke(message);
        }else if(isActivated && favouriteJoke == null){
            String choixUser = checkResponseUser(messageUser);
            if(choixUser == null){
                message.addBotMessage("Ok, j'ai pas compris ce que tu as voulu me dire. Mais je vais te faire une blague quand même.");
            }
            getJoke(message);
        }
    }
    public boolean getActivated(){
        return isActivated;
    }
    public void setActivated(boolean isActivated){
        this.isActivated = isActivated;
    }
    private boolean checkStarting(String messageUser){
        return regexService.check2Words(messageUser, "([B|b]lague)|([J|j]oke)");
    }
    private String startJoke(){
        isActivated = true;
        return "Quel type de blagues aimes-tu ? Knock Knock / Chuck Norris / Geek / Toutes ?";
    }

    private String checkResponseUser(String messageUser){
        if(regexService.check2Words(messageUser, "([C|c]huck)|([N|n]orris)")){
            favouriteJoke = "ChuckNorris";
            return "ChuckNorris";
        }
        if(regexService.check2Words(messageUser, "([K|k]nock)")){
            favouriteJoke = "KnockKnock";
            return "KnockKnock";
        }
        if(regexService.check2Words(messageUser, "([G|g]eek)")){
            favouriteJoke = "Geek";
            return "Geek";
        }
        if(regexService.check2Words(messageUser, "([T|t]ou)|(importe)")){
            favouriteJoke = "All";
            return "All";
        }
        favouriteJoke = "All";
        return null;
    }

    private void getJoke(Message message){
        String joke = null;
        String jokeType = favouriteJoke == "All" ? allJokes[Util.getRandom(0,allJokes.length-1)] : favouriteJoke;


        switch (jokeType){
            case "ChuckNorris":
                joke = chuckNorrisService.getChuckNorrisRandomJoke();
                break;
            case "KnockKnock":
                //TODO
                joke = chuckNorrisService.getChuckNorrisRandomJokeByName("Peter");
                break;
            case "Geek":
                //TODO
                break;
            default:
                break;
        }
        message.addBotMessage(joke);
        isActivated = false;
    }
}
