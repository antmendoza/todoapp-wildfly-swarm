package com.antmendoza.todoapp.todoapp.query;

import java.util.List;

import javax.persistence.EntityManager;

import com.antmendoza.todoapp.todoapp.model.Task;

public class FindAllTask {

	private final EntityManager em;

	public FindAllTask(EntityManager em) {
		this.em = em;
	}

	public List<Task> executeQuery() {
		return this.em.createQuery("select t from Task", Task.class).getResultList();

	}

}
