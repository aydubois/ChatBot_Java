package chatrabia.controller;

import chatrabia.exception.ExternalAPIException;
import chatrabia.service.ChuckNorrisService;
import chatrabia.domain.Message;
import chatrabia.service.HttpService;
import chatrabia.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final HttpService httpService;

    public MessageController(MessageService messageService, HttpService httpService) {
        super();
        this.messageService = messageService;
        this.httpService = httpService;
    }

    @GetMapping()
    public String getQuestion() {
        return "test";
    }

    @CrossOrigin(origins = {"http://127.0.0.1:1234", "http://localhost:1234", "https://chatrabia.legeay.dev"})
    @GetMapping(value = "{user}/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message getQuestion(@PathVariable String user, @PathVariable String message) {
        return messageService.getMessageUser(message, user);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000", "https://zicap.legeay.info"})
    @GetMapping(value = "{origin}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TotalDispoCenter getTotalDispo(@PathVariable String origin) throws ExternalAPIException {
        return this.httpService.sendGetRequest(String.format("https://www.doctolib.fr/search_results/%s.json", origin), TotalDispoCenter.class, null);
    }

    private static class TotalDispoCenter {
        public Integer total;

        @Override
        public String toString() {
            return "TotalDispoCenter{" +
                    "total=" + total +
                    '}';
        }
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
