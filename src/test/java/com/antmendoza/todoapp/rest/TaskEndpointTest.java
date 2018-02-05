package com.antmendoza.todoapp.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import com.antmendoza.todoapp.model.Task;
import com.antmendoza.todoapp.query.FindAllTask;
import com.antmendoza.todoapp.query.FindTaskById;

import java.util.List;


@RunWith(Arquillian.class)
public class TaskEndpointTest {

	@Deployment(testable = false)
	public static JAXRSArchive createDeployment() throws Exception {
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "todoapp.war");
		deployment.addClass(Task.class);
		deployment.addClass(TaskEndpoint.class);
		deployment.addClass(FindAllTask.class);
		deployment.addClass(FindTaskById.class);
		deployment.addAsResource("META-INF/load.sql");
		deployment.addAsResource("META-INF/persistence.xml");
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


}
