/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quizang.Controller;

import com.example.quizang.Entity.Exams;
import com.example.quizang.Repository.ExamsRepository;
import java.net.URI;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/**
 *
 * @author salguero
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/exams")
public class ExamsController {
    
    @Autowired
	private ExamsRepository exRepo;
	
	@GetMapping
        @ResponseBody
	public ResponseEntity<Page<Exams>> listarExams(Pageable pageable){
		return ResponseEntity.ok(exRepo.findAll(pageable));
	}
        
	
	@PostMapping
	public ResponseEntity<Exams> guardarExams(@Valid @RequestBody Exams exams){
		Exams examsGuardada = exRepo.save(exams);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(examsGuardada.getId()).toUri();
		return ResponseEntity.created(ubicacion).body(examsGuardada);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Exams> actualizarExams(@PathVariable Integer id,@Valid @RequestBody Exams exams){
		Optional<Exams> examsOptional = exRepo.findById(id);
		
		if(!examsOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		exams.setId(examsOptional.get().getId());
		exRepo.save(exams);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Exams> eliminarExams(@PathVariable Integer id){
		Optional<Exams> examsOptional = exRepo.findById(id);
		
		if(!examsOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		exRepo.delete(examsOptional.get());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Exams> obtenerExamsById(@PathVariable Integer id){
		Optional<Exams> examsOptional = exRepo.findById(id);
		
		if(!examsOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		return ResponseEntity.ok(examsOptional.get());
	}
    
}
