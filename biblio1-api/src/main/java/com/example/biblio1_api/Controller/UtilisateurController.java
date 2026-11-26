package com.example.biblio1_api.Controller;

import com.example.biblio1_api.Dto.UtilisateurCreationRequest;
import com.example.biblio1_api.Dto.UtilisateurResponse;
import com.example.biblio1_api.Dto.UtilisateurUpdateRequest;
import com.example.biblio1_api.Entity.Role;
import com.example.biblio1_api.Entity.Utilisateur;
import com.example.biblio1_api.Service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;


@RestController
@RequestMapping ("/api/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService service;

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping
    Page<UtilisateurResponse>  listeUtilisateurs(Pageable page)
    {return  service.listerAll(page);}
    @GetMapping("/par-role")
    Page<UtilisateurResponse> listeUtilisateurParRole(Pageable page, @RequestParam Role role)
    {return  service.findByRole(role,page);}
    @GetMapping("/{id}")
    UtilisateurResponse trouverUtilisateurById(@PathVariable Long id)
    {return service.getById(id);}
    @GetMapping("/par-email/{email}")
    UtilisateurResponse trouverUtilisateurByEmail(@PathVariable String email)
    {return service.getByEmail(email);}

    @PostMapping("/inscriptions")
    ResponseEntity<UtilisateurResponse> creerLecteur(@Valid @RequestBody UtilisateurCreationRequest request){
        Utilisateur utilisateur=service.create(request, Set.of(Role.LECTEUR));
        URI location=URI.create("/api/utilisateurs/"+utilisateur.getId());
        UtilisateurResponse response=new UtilisateurResponse(utilisateur.getEmail(),
                utilisateur.getNom(),  utilisateur.getPrenom());
        return ResponseEntity.created(location).body(response);
    }
    @PostMapping("/{id}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ajouterRole(
            @PathVariable Long id,
            @PathVariable Role role) {

        service.ajouterRole(id, role);
    }

    @PatchMapping("/{id}")
    public UtilisateurResponse modifier(@Valid @RequestBody UtilisateurUpdateRequest request, @PathVariable Long id){
        return service.update(request,id);
    }

    @DeleteMapping("/{id}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retirerRole(
            @PathVariable Long id,
            @PathVariable Role role) {

        service.retirerRole(id, role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id){
        service.delete(id);

    }



}
