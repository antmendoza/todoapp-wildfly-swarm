package com.antmendoza.todoapp.todoapp.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import com.antmendoza.todoapp.todoapp.model.Task;
import com.antmendoza.todoapp.todoapp.query.FindAllTask;
import com.antmendoza.todoapp.todoapp.query.FindTaskById;

@RunWith(Arquillian.class)
public class TaskEndpointTest {


	
	@Deployment(testable = false)
	public static Archive createDeployment() throws Exception {
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "todoapp.war");
		deployment.addClass(Task.class);
		deployment.addClass(TaskEndpoint.class);
		deployment.addClass(FindAllTask.class);
		deployment.addClass(FindTaskById.class);
		deployment.addAsResource("META-INF/persistence.xml", "META-INF/load.sql");
		deployment.addAllDependencies();
		return deployment;
	}

	@Test
	@Ignore 		//FIXME test are not loading persistence configuration.

	@RunAsClient
	public void test() {
		
		int taskId = 2;
		
		final Client client = ClientBuilder.newBuilder().build();
		final WebTarget target = client.target("http://localhost:8080/tasks/"+taskId);
		final Response response = target.request().get();
		final Task task = response.readEntity(Task.class);

		assertEquals(2, task.getId().intValue());

	}

}
