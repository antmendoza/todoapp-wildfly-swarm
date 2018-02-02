package com.antmendoza.todoapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TASK")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement(required = true)
	@Column
	private Integer id;

	
	@Column(name="LIST_ID")
	@XmlElement(required = true)
	private Integer listId;

	@Column(length = 200)
	@XmlElement(required = true)
	private String description;

	@Column(length = 1)
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getListId() {
		return listId;
	}
	
	public void setListId(Integer listId) {
		this.listId = listId;
	}

}
