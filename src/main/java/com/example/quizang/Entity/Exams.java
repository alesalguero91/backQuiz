/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quizang.Entity;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author salguero
 */
@Entity
@Table(name = "exams")
public class Exams {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "exams", cascade = CascadeType.ALL)
    private Set<Question> question = new HashSet<>();

    public int getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }

    public String getNombre() {
            return nombre;
    }

    public void setNombre(String nombre) {
            this.nombre = nombre;
    }

    public Set<Question> getQuestion() {
            return question;
    }

    public void setQuestion(Set<Question> question) {
            this.question = question;
            for(Question quest : question) {
                    quest.setExams(this);
            }
    }

    
}
