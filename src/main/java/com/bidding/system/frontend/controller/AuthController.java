/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.controller;
import com.bidding.system.frontend.model.UserDTO;
import com.bidding.system.frontend.model.UserRequestDTO;
import com.bidding.system.frontend.service.AuthRestClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author Aluno
 */
@Controller
public class AuthController {
    @Autowired
    public AuthRestClientService authservice;
    
    @GetMapping("/")
    public String home(
    HttpSession session
    ){
        Object token = session.getAttribute("token");
        
        if(token == null) {
            return "redirect:/login";
        }
        
        return "index";
    }
    
    @GetMapping("/login")
    public String login(
            Model model
    ){
        UserRequestDTO credenciais = new UserRequestDTO();
        model.addAttribute("credenciais", credenciais);
        return "login";
    }
    
    @PostMapping("/logar")
    public String logar(
            @ModelAttribute UserRequestDTO credenciais,
            HttpSession session){
        String token = authservice.logar(credenciais);
        System.out.println("token: "+token);
        session.setAttribute("token", token);
        return "redirect:/";
    }
    
    @GetMapping("/registrar")
    public String registrar(
            Model model
    ){
        UserDTO newUser = new UserDTO();
        model.addAttribute("user", newUser);
        return "registrar";
    }
    
    @PostMapping("/registrar")
    public String mandarRegistro(
            @ModelAttribute UserDTO user,
            RedirectAttributes redirectAttributes
    ) {
        
        try {
            authservice.registrar(user);
            
            
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Faça o login.");
            return "redirect:/login";
            
        } catch (HttpStatusCodeException ex) {
            
            String mensagemErroDoBackend = new ObjectMapper()
                    .readTree(
                            ex.getResponseBodyAsString()
                    ).get("message").asString(); 
            redirectAttributes.addFlashAttribute(
                    "erroServidor", 
                    mensagemErroDoBackend
            );                       
            return "redirect:/registrar";
            
        } catch (Exception e) {
            
            redirectAttributes.addFlashAttribute("erroServidor", e.getMessage());
            return "redirect:/registrar";
        }
    }
    
@GetMapping("/logout")
public String logout(HttpSession session){
    session.invalidate();
    return "redirect:/login";
}
}