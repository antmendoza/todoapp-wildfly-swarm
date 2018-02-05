package com.antmendoza.todoapp.rest;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.antmendoza.todoapp.model.Task;
import com.antmendoza.todoapp.query.FindAllTask;
import com.antmendoza.todoapp.query.FindTaskById;

import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Transactional
public class TaskEndpoint {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("{id}")
    public Response getTask(@PathParam("id") int id) {
        final Task task = new FindTaskById(em, id).executeQuery();
        return Response.ok(task).build();
    }

    @GET
    @Path("")
    public Response getTask() {
        final List<Task> tasks = new FindAllTask(em).executeQuery();
        return Response.ok(new GenericEntity<List<Task>>(tasks) {
        }).build();
    }

}