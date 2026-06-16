/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.controller;
import com.bidding.system.frontend.model.LanceDTO;
import com.bidding.system.frontend.model.EditalDTO;
import com.bidding.system.frontend.service.AuthRestClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author joaop
 */
@Controller
@RequestMapping("/editais")
public class EditalPageController {

    @Autowired
    private AuthRestClientService service;

    @GetMapping("/list")
    public String listar(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";
        }

        model.addAttribute("editais", service.listarEditais(token));

        return "editais";
    }

    @GetMapping("/novo")
    public String novo(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";
        }

        model.addAttribute("edital", new EditalDTO());
        return "novo-edital";
    }

@PostMapping("/criar")
public String criar(@ModelAttribute EditalDTO edital, HttpSession session) {
         System.out.println("Titulo: " + edital.getTitulo());
    System.out.println("Descricao: " + edital.getDescricao());
    System.out.println("Data: " + edital.getDataFechamento());
    System.out.println("Status: " + edital.getStatus());

        String token = (String) session.getAttribute("token");

        service.criarEdital(edital, token);

        return "redirect:/editais/list";
    }

    @GetMapping("/lances/{id}")
    public String telaLance(@PathVariable Long id, Model model) {

        model.addAttribute("lance", new LanceDTO());
        model.addAttribute("id", id);

        return "lance";
    }
    @PostMapping("/{id}/lances")
public String criarLance(@PathVariable Long id,
                         LanceDTO lance,
                         HttpSession session) {

    String token = (String) session.getAttribute("token");

    if (token == null || token.isBlank()) {
        return "redirect:/login";
    }

    service.criarLance(id, lance, token);

    return "redirect:/editais/list";
}
}