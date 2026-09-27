package com.example.biblio1_api.Controller;

import com.example.biblio1_api.Dto.AuteurCreationRequest;
import com.example.biblio1_api.Dto.AuteurResponse;
import com.example.biblio1_api.Dto.AuteurUpdateRequest;
import com.example.biblio1_api.Entity.Auteur;
import com.example.biblio1_api.Mapper.AuteurMapperGenere;
import com.example.biblio1_api.Service.AuteurService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auteur")
public class AuteurController {
    private final AuteurService service;

    public AuteurController(AuteurService service)
    {
        this.service = service;
    }
    @GetMapping
    public Page<AuteurResponse> lister(Pageable pageable)
    {
        return service.lister(pageable);
    }
    @GetMapping("/{id}")
    public AuteurResponse getById(@PathVariable Long id)
    {
        return service.byId(id);
    }
    @GetMapping("/par-livre/{livreId}")
    public AuteurResponse parLivre(@PathVariable Long livreId)
    {
        return service.findByLivresId(livreId);
    }
    @PostMapping
    ResponseEntity<AuteurResponse> cree(@Valid @RequestBody AuteurCreationRequest request, UriComponentsBuilder uri){
        Auteur auteur= service.cree(request);
        URI location = uri.path("/api/auteur/{id}").buildAndExpand(auteur.getId()).toUri();
        AuteurResponse response=new AuteurResponse(auteur.getCodeAuteur(), auteur.getNom(),
                                                    auteur.getPrenom(), auteur.getNationalite());
        return  ResponseEntity.created(location).body(response);

    }

    @PatchMapping("/{id}")
    public AuteurResponse modifier(@PathVariable Long id,@Valid @RequestBody AuteurUpdateRequest request){
        return service.modification(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.supprimerAuteur(id);
    }

}
