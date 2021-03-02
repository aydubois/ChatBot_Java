package chatrabia.controller;

import chatrabia.domain.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping(value = "/")
public class MessageController {

    public MessageController() {
        super();
    }

    @GetMapping()
    public String getQuestion() {
        return "test";
    }

    @GetMapping(value = "{user}/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message getQuestion(@PathVariable String user, @PathVariable String message) {
        Message currentMessage = new Message();
        currentMessage.setUserMessage(message);
        currentMessage.setUserName(user);
        currentMessage.setBotMessage("c'est un cheval qui voit un zebre");
        return currentMessage;
    }

}
