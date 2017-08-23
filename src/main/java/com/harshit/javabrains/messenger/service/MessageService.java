package com.harshit.javabrains.messenger.service;

import java.util.*;

import javax.ws.rs.QueryParam;

import com.harshit.javabrains.messenger.database.DatabaseClass;
import com.harshit.javabrains.messenger.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		// TODO Auto-generated constructor stub
		messages.put(1L, new Message(1L, "Hello World", "Harshit"));
		messages.put(2L, new Message(2L, "Hello World Again", "Harshita"));
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for(Message m : messages.values()) {
			cal.setTime(m.getCreated());
			if(cal.get(Calendar.YEAR) == year)
				messagesForYear.add(m);
		}
		
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		List<Message> res = new ArrayList<>(messages.values());
		if(start + size > res.size())
			return new ArrayList<Message>();
		return res.subList(start, start + size);
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
