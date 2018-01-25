package com.antmendoza.todoapp.todoapp.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(required = true)
	private String description;

	@XmlElement(required = true)
	private Integer id;

	@XmlElement(required = true)
	private String url;

	@XmlElement(required = true)
	private boolean done;


	public Task() {
	}
	public Task(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
