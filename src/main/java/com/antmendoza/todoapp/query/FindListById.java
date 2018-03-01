package com.antmendoza.todoapp.query;

import com.antmendoza.todoapp.model.List;

import javax.persistence.EntityManager;

public class FindListById {

    private final EntityManager em;
    private final int taskId;

    public FindListById(EntityManager em, int taskId) {
        this.em = em;
        this.taskId = taskId;
    }

    public List executeQuery() {
        return this.em.createQuery("select t from List t where t.id = :id", List.class)
                .setParameter("id", taskId).getSingleResult();

    }

}
