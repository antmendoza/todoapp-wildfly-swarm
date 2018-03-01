package com.antmendoza.todoapp.query;

import com.antmendoza.todoapp.model.Task;

import javax.persistence.EntityManager;

public class DeleteTask {

    private final EntityManager em;
    private final Task task;

    public DeleteTask(EntityManager em, Task task)
    {
        this.em = em;
        this.task = task;
    }
    
    public final void execute(){
        this.em.remove(this.task);
    }
}
