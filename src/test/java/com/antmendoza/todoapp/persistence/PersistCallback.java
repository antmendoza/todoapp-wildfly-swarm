package com.antmendoza.todoapp.persistence;


import javax.persistence.EntityManager;

public class PersistCallback<T> implements PersistenceCallback{

    private final EntityManager em;
    private final T t;

    public PersistCallback(EntityManager em, T t) {
        this.em = em;
        this.t = t;
    }

    @Override
    public T execute() throws Exception {
        em.persist(t);
        return t;
    }
}