package com.antmendoza.todoapp.query;

import javax.persistence.EntityManager;
import java.util.List;

public class FindAllList {

    private final EntityManager em;

    public FindAllList(EntityManager em) {
        this.em = em;
    }

    public List<com.antmendoza.todoapp.model.List> executeQuery() {
        return this.em.createQuery("select t from List t", com.antmendoza.todoapp.model.List.class).getResultList();
    }

}
