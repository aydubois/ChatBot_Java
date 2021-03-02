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

    @GetMapping(value = "{user}/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message getQuestion(@PathVariable String user, @PathVariable String message) {
        Message currentMessage = messageService.getMessageUser(message, user);
        //Message currentMessage = new Message();
        //currentMessage.setUserMessage(message);
        //currentMessage.setUserName(user);

        String joke = chuckNorrisService.getChuckNorrisRandomJokeByName(user);

        currentMessage.setBotMessage(currentMessage.getBotMessage());
        return currentMessage;
    }

}
