package com.antmendoza.todoapp.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "LIST")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class List implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(required = true)
    @Column
    private Integer id;


    @Column(length = 200)
    @XmlElement(required = true)
    private String description;


    public List() {
    }

    public List(final String description) {
        this.description = description;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        List list = (List) o;
        return Objects.equals(id, list.id) &&
                Objects.equals(description, list.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
