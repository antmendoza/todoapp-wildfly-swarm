package com.antmendoza.todoapp.rest;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.antmendoza.todoapp.persistence.DeleteTableCallback;
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

import com.antmendoza.todoapp.model.Task;
import com.antmendoza.todoapp.query.FindAllTask;
import com.antmendoza.todoapp.query.FindTaskById;

import java.util.List;


@RunWith(Arquillian.class)
public class TaskEndpointTest {


    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;


	@Deployment
	public static JAXRSArchive createDeployment() throws Exception {
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "todoapp.war");
		deployment.addClass(Task.class);
		deployment.addClass(TaskEndpoint.class);

        deployment.addPackage(CreateTask.class.getPackage());
        deployment.addPackage(TransactionalOperation.class.getPackage());

        deployment.addAsResource("META-INF/load.sql");
		deployment.addAsResource("META-INF/persistence.xml");
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
        final Task task = response.readEntity(Task.class);
        assertEquals(taskId, task.getId().intValue());
    }



    @Test
    @RunAsClient
    public void createTask() throws Exception {

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/tasks");

        final Response response = target.request().post(Entity.json(new Task("task created", false, 1)));
        final Task task = response.readEntity(Task.class);
        assertEquals("task created", task.getDescription());
    }


}
