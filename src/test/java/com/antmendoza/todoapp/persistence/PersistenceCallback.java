package com.antmendoza.todoapp.persistence;

public interface PersistenceCallback<T> {

    T execute() throws Exception;
}
