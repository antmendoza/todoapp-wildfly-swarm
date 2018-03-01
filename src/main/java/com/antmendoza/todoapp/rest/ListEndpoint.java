package com.antmendoza.todoapp.rest;

import com.antmendoza.todoapp.model.List;
import com.antmendoza.todoapp.query.FindAllList;
import com.antmendoza.todoapp.query.FindListById;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/lists")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Transactional
public class ListEndpoint {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("{id}")
    public Response getList(@PathParam("id") final int id) {
        return executeOnTask(id, new Callback<List, Response>() {
            @Override
            public Response call(List list) {
                return Response.ok(list).build();
            }
        });
    }


    @GET
    @Path("")
    public Response getLists() {
        final java.util.List<List> lists = new FindAllList(this.em).executeQuery();
        return Response.ok(new GenericEntity<java.util.List<List>>(lists) {
        }).build();
    }


    private Response executeOnTask(int id, Callback<List, Response> callback) {
        try {
            final List list = new FindListById(this.em
                    , id).executeQuery();
            return callback.call(list);
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}