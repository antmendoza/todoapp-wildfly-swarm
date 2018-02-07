package com.antmendoza.todoapp.query;

import com.antmendoza.todoapp.model.Task;

import javax.persistence.EntityManager;

public class CreateTask {

    private final EntityManager em;
    private final Task task;

    public CreateTask(EntityManager em, Task task)
    {
        this.em = em;
        this.task = task;
    }
    
    
    public final void execute(){
        this.em.persist(this.task);
    }
}
