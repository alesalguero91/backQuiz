/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quizang.Controller;

import com.example.quizang.Entity.FalseOption;
import com.example.quizang.Entity.Question;
import com.example.quizang.Repository.FalseOptionRepository;
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
@RequestMapping("/api/false")
public class FalseOptionController {
    
    @Autowired
    private FalseOptionRepository falseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public ResponseEntity<Page<FalseOption>> listarFalseOption(Pageable pageable){
            return ResponseEntity.ok(falseRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<FalseOption> guardarFalseOption(@Valid @RequestBody FalseOption falseOption){
            Optional<Question> questionOptional = questionRepository.findById(falseOption.getQuestion().getId());

            if(!questionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            falseOption.setQuestion(questionOptional.get());
            FalseOption libroGuardado = falseRepository.save(falseOption);
            URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                            .buildAndExpand(libroGuardado.getId()).toUri();

            return ResponseEntity.created(ubicacion).body(libroGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FalseOption> actualizarFalseOption(@Valid @RequestBody FalseOption falseOption,@PathVariable Integer id){
            Optional<Question> questionOptional = questionRepository.findById(falseOption.getQuestion().getId());

            if(!questionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            Optional<FalseOption> falseOptionOptional = falseRepository.findById(id);
            if(!falseOptionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            falseOption.getQuestion();
            falseOption.setId(questionOptional.get().getId());
            falseRepository.save(falseOption);

            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FalseOption> eliminarFalseOption(@PathVariable Integer id){
            Optional<FalseOption> falseOptionOptional = falseRepository.findById(id);

            if(!falseOptionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            falseRepository.delete(falseOptionOptional.get());
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FalseOption> obtenerTrueOptionPorId(@PathVariable Integer id){
            Optional<FalseOption> falseOptionOptional = falseRepository.findById(id);

            if(!falseOptionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            return ResponseEntity.ok(falseOptionOptional.get());
    }
    
}
