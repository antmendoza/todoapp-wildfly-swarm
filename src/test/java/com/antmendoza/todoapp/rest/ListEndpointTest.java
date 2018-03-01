package com.antmendoza.todoapp.rest;

import com.antmendoza.todoapp.query.FindAllList;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(Arquillian.class)
public class ListEndpointTest {


    @Deployment
    public static JAXRSArchive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "todoapp.war");
        deployment.addClass(com.antmendoza.todoapp.model.List.class);
        deployment.addClass(ListEndpoint.class);
        deployment.addClass(Callback.class);

        deployment.addPackage(FindAllList.class.getPackage());

        deployment.addAsResource("META-INF/load-lists.sql" , "META-INF/data.sql" );
        deployment.addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml");

        deployment.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addAllDependencies();
        return deployment;
    }

    @Test
    @RunAsClient
    public void retrieveAllLists() {

        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target("http://localhost:8080/lists/");

        final Response response = target.request().get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        final List tasks = response.readEntity(List.class);
        assertEquals(1, tasks.size());

    }



    @Test
    @RunAsClient
    public void retrieveListById() {

        final Client client = ClientBuilder.newBuilder().build();
        final int id = 1;
        final WebTarget target = client.target("http://localhost:8080/lists/"+id);

        final Response response = target.request().get();
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        final com.antmendoza.todoapp.model.List list = response.readEntity(com.antmendoza.todoapp.model.List.class);
        assertNotNull(list);

    }

}
