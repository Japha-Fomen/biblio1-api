package com.example.biblio1_api.Controller;

import com.example.biblio1_api.Dto.MembreCreationRequest;
import com.example.biblio1_api.Dto.MembreResponse;
import com.example.biblio1_api.Dto.MembreUpdateRequest;
import com.example.biblio1_api.Entity.Membre;
import com.example.biblio1_api.Service.MembreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.net.URI;

@RestController
@RequestMapping("/api/membres")
public class MembreController {
    private final MembreService service;
    public MembreController(MembreService service) {
        this.service = service;
    }
    @GetMapping
    public Page<MembreResponse> findAllMembre(Pageable pageable) {
        return service.findAllMembre(pageable);
    }
    @GetMapping("/{id}")
    public MembreResponse findMembreById(@PathVariable Long id) {
        return service.findMembreById(id);
    }
@GetMapping("/par-email/{email}")
    public MembreResponse findMembreByEmail(@PathVariable String email){
        return service.findMembreByEmail(email);
}
@GetMapping("/{id}/emprunts/nombre")
public int numbreEmpruntMembre(@PathVariable Long id){
        return service.compterEmprunts(id);
}
@PostMapping
    ResponseEntity<MembreResponse> cree(@Valid @RequestBody MembreCreationRequest request){
    Membre membre= service.creerMembre(request);
    URI location=java.net.URI.create("/api/membres/"+membre.getId());
    MembreResponse response= new MembreResponse(membre.getEmail(), membre.getPrenom(), membre.getNom(),
            membre.isActif());
    return ResponseEntity.created(location).body(response);
}
@PatchMapping("/{id}")
    public MembreResponse modifier(@Valid @RequestBody MembreUpdateRequest request, @PathVariable Long id){
        return service.updateMembre(id, request);
}
@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.deleteMembre(id);
}
}
