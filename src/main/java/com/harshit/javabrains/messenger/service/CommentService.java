package com.harshit.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.harshit.javabrains.messenger.database.DatabaseClass;
import com.harshit.javabrains.messenger.model.Comment;
import com.harshit.javabrains.messenger.model.ErrorMessage;
import com.harshit.javabrains.messenger.model.Message;

public class CommentService {
private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public CommentService() {
		// TODO Auto-generated constructor stub
		messages.put(1L, new Message(1L, "Hello World", "Harshit"));
		messages.put(2L, new Message(1L, "Hello World Again", "Harshita"));
	}
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "www.javabrains.io");
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		Message message = messages.get(messageId);
		if(message == null)
			throw new WebApplicationException(response);
		Map<Long, Comment> comments = message.getComments();
		if(comments == null)
			throw new NotFoundException(response);
		return comments.get(commentId);
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment (long messageId, Comment comment) {
		Map<Long, Comment> comments  = messages.get(messageId).getComments();
		if(comment.getId() <= 0)
			return null;
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
