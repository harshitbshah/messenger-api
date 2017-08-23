package com.harshit.javabrains.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.harshit.javabrains.messenger.model.ErrorMessage;

public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

		@Override
		public Response toResponse(Throwable exception) {
			// TODO Auto-generated method stub
			ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "www.javabrains.io");
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(errorMessage)
					.build();
		}
}
