package com.harshit.javabrains.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.Uri;

import com.harshit.javabrains.messenger.beans.MessageFilterBean;
import com.harshit.javabrains.messenger.exception.DataNotFoundException;
import com.harshit.javabrains.messenger.model.Message;
import com.harshit.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJSONMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("JSON Method Called");
		if(filterBean.getYear() > 0)
			return messageService.getAllMessagesForYear(filterBean.getYear());
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0)
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		
		return messageService.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXMLMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("XML Method Called");
		if(filterBean.getYear() > 0)
			return messageService.getAllMessagesForYear(filterBean.getYear());
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0)
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		
		return messageService.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo){
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(newMessage)
				.build();
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
	public Message getMessage(@PathParam("messageId") String messageId, @Context UriInfo uriinfo) {
		Message message = messageService.getMessage(Long.parseLong(messageId));
		message.addLink(getUriForSelf(messageId, uriinfo), "self");
		message.addLink(getUriForProfiles(message, uriinfo), "profiles");
		message.addLink(getUriForComments(message, uriinfo), "profiles");
//		if(message == null)
//			throw new DataNotFoundException("Message with id " + messageId + " not found.");
		return message;
	}
	
	private String getUriForComments(Message message, UriInfo uriinfo) {
		// TODO Auto-generated method stub
		String uri = uriinfo.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(MessageResource.class, "getCommentResource")
		.path(CommentResource.class)
		.resolveTemplate("messageId", message.getId())
		.build()
		.toString();
		return uri;
	}

	private String getUriForProfiles(Message message, UriInfo uriinfo) {
		String uri = uriinfo.getBaseUriBuilder()
		.path(ProfileResource.class)
		.path(message.getAuthor() )
		.build()
		.toString();
		return uri;
	}

	private String getUriForSelf(String messageId, UriInfo uriinfo) {
		String uri = uriinfo.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(messageId)
		.build()
		.toString();
		return uri;
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
}
