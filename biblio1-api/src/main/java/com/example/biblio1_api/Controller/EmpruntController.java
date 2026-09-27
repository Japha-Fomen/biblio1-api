package com.example.biblio1_api.Controller;

import com.example.biblio1_api.Dto.EmpruntRequest;
import com.example.biblio1_api.Dto.EmpruntResponse;
import com.example.biblio1_api.Entity.Emprunt;
import com.example.biblio1_api.Service.EmpruntService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {
    EmpruntService service;

    public EmpruntController(EmpruntService service) {
        this.service = service;
    }

@PostMapping
    public ResponseEntity<EmpruntResponse> empunter(@Valid @RequestBody EmpruntRequest requete){
        Emprunt emprunt= service.emprunter(requete.livreId(),requete.membreId());
        URI location=java.net.URI.create("/api/emprunts/"+emprunt.getId());
        return ResponseEntity.created(location).body(EmpruntResponse.depuis(emprunt));
    }
    @PostMapping("/{id}/retour")
    public EmpruntResponse retourner(@PathVariable Long id){
        return EmpruntResponse.depuis(service.retourner(id));
    }
    @GetMapping("/{id}/emprunts")
    public List<EmpruntResponse> getEmprunts(@PathVariable Long id) {
        return service.listerEmpruntsDuMembre(id);

    }
}
