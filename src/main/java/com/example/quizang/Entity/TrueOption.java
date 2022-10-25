/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quizang.Entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

/**
 *
 * @author salguero
 */
@Entity
@Table(name = "trueOption")
public class TrueOption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String opt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    @JsonProperty(access = Access.WRITE_ONLY)
    private Question question;
    
    

    public int getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }

    public String getOpt() {
            return opt;
    }

    public void setOpt(String opt) {
            this.opt = opt;
    }

    public Question getQuestion() {
            return question;
    }

    public void setQuestion(Question question) {
            this.question =question;
    }
    

    
}
