package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import model.Message;
import service.MessageService;

@RestController
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Message>> findAllProduct() {
        List<Message> messages = messageService.findAll();
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/message/chat",method = RequestMethod.POST)
    public ResponseEntity<Message> createMessage(@RequestBody Message message, UriComponentsBuilder builder) {
		messageService.save(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/message/{id}").buildAndExpand(message.getId()).toUri());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
	
	@RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteProduct(@PathVariable("id") Long id) {
        Optional<Message> message = messageService.findById(id);
        if (!message.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        messageService.remove(message.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value = "/message/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessageById(@PathVariable("id") Long id) {
        Optional<Message> message = messageService.findById(id);

        if (!message.isPresent()) {
            return new ResponseEntity<>(message.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(message.get(), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/message",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Message>> getMessageByUsername(@RequestParam(value="username") String username) {
		 List<Message> messages = messageService.findByUsername(username);
		 if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 return new ResponseEntity<>(messages, HttpStatus.OK);
	}
}