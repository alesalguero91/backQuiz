/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quizang.Repository;

import com.example.quizang.Entity.FalseOption;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author salguero
 */
public interface FalseOptionRepository extends JpaRepository <FalseOption, Integer>{
    
}
