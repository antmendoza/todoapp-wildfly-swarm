package com.antmendoza.todoapp.query;

import javax.persistence.EntityManager;

import com.antmendoza.todoapp.model.Task;

public class FindTaskById {

	private final EntityManager em;
	private final int taskId;

	public FindTaskById(EntityManager em, int taskId) {
		this.em = em;
		this.taskId = taskId;
	}

	public Task executeQuery() {
		return this.em.createQuery("select t from Task t where t.id = :id", Task.class)
				.setParameter("id", taskId).getSingleResult();

	}

}
