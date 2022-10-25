/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quizang.Controller;

import com.example.quizang.Entity.Question;
import com.example.quizang.Entity.TrueOption;
import com.example.quizang.Repository.QuestionRepository;
import com.example.quizang.Repository.TrueOptionRepository;
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
@RequestMapping("/api/true")
public class TrueOptionController {
    
    @Autowired
    private TrueOptionRepository trueRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public ResponseEntity<Page<TrueOption>> listarTrueOption(Pageable pageable){
            return ResponseEntity.ok(trueRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<TrueOption> guardarLibro(@Valid @RequestBody TrueOption trueOption){
            Optional<Question> questionOptional = questionRepository.findById(trueOption.getQuestion().getId());

            if(!questionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            trueOption.setQuestion(questionOptional.get());
            TrueOption libroGuardado = trueRepository.save(trueOption);
            URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                            .buildAndExpand(libroGuardado.getId()).toUri();

            return ResponseEntity.created(ubicacion).body(libroGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrueOption> actualizarLibro(@Valid @RequestBody TrueOption trueOption,@PathVariable Integer id){
            Optional<Question> questionOptional = questionRepository.findById(trueOption.getQuestion().getId());

            if(!questionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            Optional<TrueOption> trueOptionOptional = trueRepository.findById(id);
            if(!trueOptionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            trueOption.getQuestion();
            trueOption.setId(questionOptional.get().getId());
            trueRepository.save(trueOption);

            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrueOption> eliminarTrueOption(@PathVariable Integer id){
            Optional<TrueOption> trueOptionOptional = trueRepository.findById(id);

            if(!trueOptionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            trueRepository.delete(trueOptionOptional.get());
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrueOption> obtenerTrueOptionPorId(@PathVariable Integer id){
            Optional<TrueOption> trueOptionOptional = trueRepository.findById(id);

            if(!trueOptionOptional.isPresent()){
                    return ResponseEntity.unprocessableEntity().build();
            }

            return ResponseEntity.ok(trueOptionOptional.get());
    }
}
