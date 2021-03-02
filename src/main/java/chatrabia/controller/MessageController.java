package chatrabia.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    public class Message implements Serializable {
        public Integer id;
        public String message;

        public Message(Integer id, String message) {
            this.id = id;
            this.message = message;
        }

        public Message(String message) {
            this(null, message);
        }
    }

    public MessageController() {
        super();
    }

    @GetMapping()
    public String getQuestion() {
        return "test";
    }

    @GetMapping(value = "/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message getQuestion(@PathVariable String message) {
        return new Message(1, message);
    }

}
