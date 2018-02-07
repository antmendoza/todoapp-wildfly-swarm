package com.antmendoza.todoapp.query;

import com.antmendoza.todoapp.persistence.PersistCallback;
import com.antmendoza.todoapp.persistence.TransactionalOperation;
import com.antmendoza.todoapp.model.Task;
import org.hamcrest.CoreMatchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class FindAllTaskTest {


    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;


    @Deployment
    public static JAXRSArchive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "todoapp.war");
        deployment.addClass(FindAllTask.class);
        deployment.addClass(Task.class);
        deployment.addPackage(TransactionalOperation.class.getPackage());
        deployment.addAsResource("META-INF/persistence.xml");
        deployment.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addAllDependencies();
        return deployment;
    }


    @Test
    public void testFindAllTask() throws Exception {
        final TransactionalOperation transactionalOperation = new TransactionalOperation(this.em, this.utx);
        transactionalOperation.execute(new PersistCallback<Task>(
                            em, new Task("task 1", false, 1)));

        transactionalOperation.execute(new PersistCallback<Task>(
                            em, new Task("task 2", false, 1)));

        final List<Task> tasks = new FindAllTask(em).executeQuery();
        assertEquals(2, tasks.size());

        final List<String> taskDescriptions = tasks.stream().map(t -> t.getDescription()).collect(Collectors.toList());
        assertThat(taskDescriptions, CoreMatchers.hasItem("task 1"));
        assertThat(taskDescriptions, CoreMatchers.hasItem("task 2"));

    }


}
