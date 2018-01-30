package com.antmendoza.todoapp.todoapp.rest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.antmendoza.todoapp.todoapp.model.Task;
import com.antmendoza.todoapp.todoapp.query.FindTaskById;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskEndpoint {

	@PersistenceContext
	private EntityManager em;

	@GET
	@Path("{id}")
	public Response getTask(@PathParam("id") int id) {

		
		Task task;
		//FIXME test are not loading persistence configuration.
		try {
			task = new FindTaskById(em, id).executeQuery();
			return Response.ok(task).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.noContent().build();
		}


	}

}