package com.antmendoza.todoapp.persistence;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

@Transactional
public class TransactionalOperation<T> {


    private final EntityManager em;
    private final UserTransaction utx;

    public TransactionalOperation(final EntityManager em, final UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    public T execute(PersistenceCallback<T> persistenceCallback) throws Exception {
        utx.begin();
        em.joinTransaction();
        T t = persistenceCallback.execute();
        utx.commit();
        return t;
    }
}
