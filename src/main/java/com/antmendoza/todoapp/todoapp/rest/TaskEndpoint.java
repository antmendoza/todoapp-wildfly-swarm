package com.antmendoza.todoapp.todoapp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.antmendoza.todoapp.todoapp.model.Task;

@Path("/tasks")
//@Api(value = "/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskEndpoint {

	
	@GET
	@Path("{id}")
	//@ApiOperation(value = "Task for the provided id", response = Task.class)
	public Response getTask(@PathParam("id") int id) {

		return Response.ok(new Task(id)).build();
	}

}