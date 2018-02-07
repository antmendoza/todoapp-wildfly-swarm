package com.antmendoza.todoapp.persistence;

import javax.persistence.EntityManager;
import java.util.Optional;

public class DeleteTableCallback<T> implements PersistenceCallback {


    private final EntityManager em;
    private final String tableName;

    public DeleteTableCallback(EntityManager em, String tableName) {
        this.em = em;
        this.tableName = tableName;
    }

    @Override
    public Optional<T> execute() throws Exception {

        em.createQuery("delete from :tableName").setParameter("tableName", this.tableName).executeUpdate();
        return Optional.empty();
    }
}
