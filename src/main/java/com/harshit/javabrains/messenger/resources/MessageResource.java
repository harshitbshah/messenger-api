package com.harshit.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.harshit.javabrains.messenger.model.Message;
import com.harshit.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages() {
		return messageService.getAllMessages();
	}
	
	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(Message message, @PathParam("messageId") long messageId) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long messageId) {
		messageService.removeMessage(messageId);
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") String messageId) {	
		return messageService.getMessage(Long.parseLong(messageId));
	}
}
