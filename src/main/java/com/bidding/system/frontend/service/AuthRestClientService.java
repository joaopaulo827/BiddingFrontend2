package com.bidding.system.frontend.service;

import com.bidding.system.frontend.model.EditalDTO;
import com.bidding.system.frontend.model.UserDTO;
import com.bidding.system.frontend.model.UserRequestDTO;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 */
@Service
public class AuthRestClientService {

    private final RestClient restClient;

    /**
     * 
     */
    public AuthRestClientService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:9000/api/auth")
                .build();
    }
    public String logar(UserRequestDTO user) {
        return restClient.post()
                .uri("/logar")
                .body(user)
                .retrieve()
                .body(String.class);
    }
    
    public void registrar(UserDTO user ) {
        if(!user.getSenha().equals(user.getConfirmarSenha())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(400), 
                    "Senha e Confirmar Senha Diferentes");
        }
        user.setRole("FORNECEDOR");
        String retorno = 
            restClient
                .post()
                .uri("/autenticar/registrar")
                .body(user)
                .retrieve()
                .body(String.class);
    }
    public List<EditalDTO> listarEditais(String token) {
        EditalDTO[] editais = restClient.get()
                .uri("/editais")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(EditalDTO[].class);

        return Arrays.asList(editais);
    }
}