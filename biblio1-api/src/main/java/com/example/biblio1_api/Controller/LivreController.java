package com.example.biblio1_api.Controller;

import com.example.biblio1_api.Dto.CritereRecherche;
import com.example.biblio1_api.Dto.LivreCreationRequest;
import com.example.biblio1_api.Dto.LivreResponse;
import com.example.biblio1_api.Dto.LivreUpdateRequest;
import com.example.biblio1_api.Entity.Genre;
import com.example.biblio1_api.Service.LivreService;
import com.example.biblio1_api.Service.RechercheLivreService;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/livres")
public class LivreController {
    private final LivreService service;
    private final RechercheLivreService rechercheService;

    public LivreController(LivreService service, RechercheLivreService rechercheService) {
        this.service = service;
        this.rechercheService = rechercheService;
    }
    @GetMapping
    public Page<LivreResponse> lister(
            @PageableDefault(size = 20,sort = "titre",
            direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String titre){
        return (titre==null || titre.isBlank())?
                service.lister(pageable)
                :service.rechercheParTitre(titre,pageable);
    }
    @GetMapping("/{id}")
        public LivreResponse parId(@PathVariable Long id){
        return service.parId(id);
        }

        @GetMapping("/isbn/{isbn}")
    public LivreResponse parIsbn(@PathVariable String isbn){
        return service.parIsbn(isbn);
        }

        @GetMapping("/recherche")
        Page<LivreResponse> recherche(@RequestParam(required = false) String titre,@RequestParam(required = false) Genre genre,
                                      @RequestParam(required = false) Integer anneeMin,@RequestParam(required = false)String nationalite,
                                      @PageableDefault(size = 20) Pageable pageable){
            CritereRecherche critere= new CritereRecherche(titre,genre,anneeMin,nationalite);
            return rechercheService.rechercheLivre(critere,pageable);
        }
        @PostMapping
    public ResponseEntity<LivreResponse> cree(
                @Valid @RequestBody LivreCreationRequest request,
                UriComponentsBuilder uriBuilder){
        LivreResponse cree= service.creer(request);
            URI location= uriBuilder.path("/api/livres/{id}").
                    buildAndExpand(cree.id()).toUri();
        return  ResponseEntity.created(location).body(cree);
        }
        @PatchMapping("/{id}")
    public LivreResponse modifier(
            @PathVariable Long id,
            @Valid @RequestBody LivreUpdateRequest requete){

        return service.modifier(id, requete);
        }
        @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id){
        service.supprimer(id);
        }
}
