package com.antmendoza.todoapp.rest;

import com.antmendoza.todoapp.model.Task;
import com.antmendoza.todoapp.query.CreateTask;
import com.antmendoza.todoapp.query.DeleteTask;
import com.antmendoza.todoapp.query.FindAllTask;
import com.antmendoza.todoapp.query.FindTaskById;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getTask(@PathParam("id") final int id) {
        return executeOnTask(id, new Callback<Task, Response>() {
            @Override
            public Response call(Task task) {
                return Response.ok(task).build();
            }
        });
    }


    @GET
    @Path("")
    public Response getTask() {
        final List<Task> tasks = new FindAllTask(em).executeQuery();
        return Response.ok(new GenericEntity<List<Task>>(tasks) {
        }).build();
    }


    @POST
    @Path("")
    public Response createTask(final Task task) {
        new CreateTask(em, task).execute();
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTask(@PathParam("id") final int id) {

        return executeOnTask(id, new Callback<Task, Response>() {
            public Response call(Task task) {
                new DeleteTask(em, task).execute();
                return Response.noContent().build();
            }
        });


    }

    private Response executeOnTask(int id, Callback<Task, Response> callback) {
        try {
            final Task task = new FindTaskById(this.em
                    , id).executeQuery();
            return callback.call(task);
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



}