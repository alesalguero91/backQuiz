/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quizang.Controller;

import com.example.quizang.Entity.Exams;
import com.example.quizang.Entity.Question;
import com.example.quizang.Repository.ExamsRepository;
import com.example.quizang.Repository.QuestionRepository;
import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author salguero
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamsRepository examRepository;

    @GetMapping
    public ResponseEntity<Page<Question>> listarLibros(Pageable pageable){
            return ResponseEntity.ok(questionRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Question> guardarQuestion(@Valid @RequestBody Question question){
            Optional<Exams> ExamOptional = examRepository.findById(question.getExams().getId());

            if(!ExamOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            question.setExams(ExamOptional.get());
            Question QuestionGuardado = questionRepository.save(question);
            URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                            .buildAndExpand(QuestionGuardado.getId()).toUri();

            return ResponseEntity.created(ubicacion).body(QuestionGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> actualizarQuestion(@Valid @RequestBody Question question,@PathVariable Integer id){
            Optional<Exams> bibliotecaOptional = examRepository.findById(question.getExams().getId());

            if(!bibliotecaOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            Optional<Question> questionOptional = questionRepository.findById(id);
            if(!questionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            question.setExams(bibliotecaOptional.get());
            question.setId(questionOptional.get().getId());
            questionRepository.save(question);

            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> eliminarQuestion(@PathVariable Integer id){
            Optional<Question> questionOptional = questionRepository.findById(id);

            if(!questionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            questionRepository.delete(questionOptional.get());
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> obtenerQuestionPorId(@PathVariable Integer id){
            Optional<Question> questionOptional = questionRepository.findById(id);

            if(!questionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            return ResponseEntity.ok(questionOptional.get());
    }
}
