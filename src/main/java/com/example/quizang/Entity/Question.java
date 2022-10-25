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
@Table(name = "question",uniqueConstraints = {@UniqueConstraint(columnNames = {"pregunta"})})
public class Question {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private String pregunta;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "exams_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Exams exams;

        @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private Set<TrueOption> trueO = new HashSet<>();
    
         @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private Set<FalseOption> falseO = new HashSet<>();
         
        
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public Exams getExams() {
		return exams;
	}

	public void setExams(Exams exams) {
		this.exams =exams;
	}
        
        public Set<TrueOption> getTrueO() {
		return trueO;
	}

	public void setTrueO(Set<TrueOption> trueO) {
		this.trueO = trueO;
		for(TrueOption trueo : trueO) {
                        trueo.setQuestion(this);
		}
	}
        
        public Set<FalseOption> getFalseO() {
		return falseO;
	}

	public void setFalseO(Set<FalseOption> falseO) {
		this.falseO = falseO;
		for(FalseOption falseo : falseO) {
                        falseo.setQuestion(this);
		}
	}
}
