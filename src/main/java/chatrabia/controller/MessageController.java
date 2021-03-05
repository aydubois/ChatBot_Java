package chatrabia.controller;

import chatrabia.service.ChuckNorrisService;
import chatrabia.domain.Message;
import chatrabia.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class MessageController {

    private ChuckNorrisService chuckNorrisService;
    private MessageService messageService;

    public MessageController(ChuckNorrisService chuckNorrisService, MessageService messageService) {
        super();
        this.messageService = messageService;
        this.chuckNorrisService = chuckNorrisService;
    }

    @GetMapping()
    public String getQuestion() {
        return "test";
    }

    @CrossOrigin(origins = {"http://127.0.0.1:1234", "http://localhost:1234", "https://chatrabia.legeay.dev"})
    @GetMapping(value = "{user}/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message getQuestion(@PathVariable String user, @PathVariable String message) {
        Message currentMessage = messageService.getMessageUser(message, user);

        return currentMessage;
    }

    // [APIs]

    // https://official-joke-api.appspot.com/random_joke
    // https://official-joke-api.appspot.com/jokes/programming/random
    // https://official-joke-api.appspot.com/jokes/knock-knock/random

    // citations : https://fortuneapi.herokuapp.com/drugs
    //              on peut soit ne pas mettre de param soit remplacer "drugs" par un des nom de fichiers ici : https://github.com/sarah256/fortune-api/tree/master/datfiles

    // geek jokes : https://geek-jokes.sameerkumar.website/api  -> String
    //              https://geek-jokes.sameerkumar.website/api?format=json -> json

    // Cocktail api : https://www.thecocktaildb.com/api.php

    // https://jokeapi.dev/

    // https://icanhazdadjoke.com/api#fetch-a-random-dad-joke

    // random dog picture : https://random.dog/woof.json
}
