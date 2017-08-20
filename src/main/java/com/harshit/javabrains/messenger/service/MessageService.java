package com.harshit.javabrains.messenger.service;

import java.util.*;
import com.harshit.javabrains.messenger.database.DatabaseClass;
import com.harshit.javabrains.messenger.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		// TODO Auto-generated constructor stub
		messages.put(1L, new Message(1L, "Hello World", "Harshit"));
		messages.put(2L, new Message(1L, "Hello World Again", "Harshita"));
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id) {
		return messages.get(id);
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage (Message message) {
		if(message.getId() <= 0)
			return null;
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
