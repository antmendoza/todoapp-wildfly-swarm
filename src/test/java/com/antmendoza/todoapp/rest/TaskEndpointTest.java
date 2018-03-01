package com.antmendoza.todoapp.rest;

import com.antmendoza.todoapp.model.Task;
import com.antmendoza.todoapp.persistence.TransactionalOperation;
import com.antmendoza.todoapp.query.CreateTask;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(Arquillian.class)
public class TaskEndpointTest {


    @Deployment
    public static JAXRSArchive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "todoapp.war");
        deployment.addClass(Task.class);
        deployment.addClass(TaskEndpoint.class);
        deployment.addClass(Callback.class);

        deployment.addPackage(CreateTask.class.getPackage());
        deployment.addPackage(TransactionalOperation.class.getPackage());

        deployment.addAsResource("META-INF/load-tasks.sql" , "META-INF/data.sql" );
        deployment.addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml");
        deployment.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addAllDependencies();
        return deployment;
    }

    @Test
    @RunAsClient
    public void retrieveAllTask() {

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/tasks/");


        final Response response = target.request().get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        final List tasks = response.readEntity(List.class);
        assertEquals(4, tasks.size());
    }


    @Test
    @RunAsClient
    public void retrieveTaskById() {
        final int taskId = 1;

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/tasks/" + taskId);

        final Response response = target.request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        final Task task = response.readEntity(Task.class);
        assertEquals(taskId, task.getId().intValue());
    }

    @Test
    @RunAsClient
    public void retrieveTaskByIdNotFound() {
        final int taskId = 88;

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/tasks/" + taskId);

        final Response response = target.request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }


    @Test
    @RunAsClient
    public void createTask() throws Exception {

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/tasks");

        final Response response = target.request().post(Entity.json(new Task("task created", false, 1)));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        final Task task = response.readEntity(Task.class);
        assertEquals("task created", task.getDescription());
    }

    @Test
    @RunAsClient
    public void deleteTaskOK() throws Exception {
        final int taskId = 4;

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/tasks/" + taskId);
        final Response response = target.request().delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertFalse(response.hasEntity());
    }

    @Test
    @RunAsClient
    public void deleteTaskNotFound() throws Exception {
        final int taskId = 88;

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/tasks/" + taskId);
        final Response response = target.request().delete();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }


}
