/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.controller;

import com.bidding.system.frontend.model.EditalDTO;
import com.bidding.system.frontend.service.AuthRestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author joaop
 */
@Controller
public class EditalPageController {
    @Autowired
    public AuthRestClientService authservice;
    @GetMapping("/editais")
    public String editar(
            Model model
    ){
        EditalDTO newEdital= new EditalDTO();
        model.addAttribute("edital", newEdital);
        return "editais";
    }       
}
