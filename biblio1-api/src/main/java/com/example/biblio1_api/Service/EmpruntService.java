package com.example.biblio1_api.Service;

import com.example.biblio1_api.Config.ProprietesBiblio;
import com.example.biblio1_api.Dto.EmpruntResponse;
import com.example.biblio1_api.Entity.Emprunt;
import com.example.biblio1_api.Entity.Livre;
import com.example.biblio1_api.Entity.Membre;
import com.example.biblio1_api.Entity.StatutEmprunt;
import com.example.biblio1_api.Exception.ConflitMetierException;
import com.example.biblio1_api.Exception.RessourceIntrouvableException;
import com.example.biblio1_api.Repository.EmpruntRepository;
import com.example.biblio1_api.Repository.LivreRepository;
import com.example.biblio1_api.Repository.MembreRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;
    private final ProprietesBiblio proprietes;

    public EmpruntService(EmpruntRepository empruntRepository,
                          LivreRepository livreRepository,
                          MembreRepository membreRepository,
                          ProprietesBiblio proprietes) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
        this.membreRepository = membreRepository;
        this.proprietes = proprietes;
    }

    @Transactional
    public Emprunt emprunter(Long idLivre, Long idMembre) {
        Membre membre = membreRepository.findById(idMembre)
                .orElseThrow(() -> new RessourceIntrouvableException("Membre", idMembre));

        if (!membre.isActif()) {
            throw new ConflitMetierException("Le compte du membre est désactivé");
        }

        long empruntsDuMembre = empruntRepository.countByMembreIdAndStatut(idMembre, StatutEmprunt.EN_COURS);
        if (empruntsDuMembre >= proprietes.emprunt().maxSimultanes()) {
            throw new ConflitMetierException(
                    "Le membre a déjà atteint la limite de " + proprietes.emprunt().maxSimultanes() + " emprunts");
        }

        Livre livre = livreRepository.findById(idLivre)
                .orElseThrow(() -> new RessourceIntrouvableException("Livre", idLivre));

        long exemplairesSortis = empruntRepository.countByLivreIdAndStatut(idLivre, StatutEmprunt.EN_COURS);
        if (!livre.estDisponible(exemplairesSortis)) {
            throw new ConflitMetierException("Aucun exemplaire disponible pour ce livre");
        }

        Emprunt emprunt = new Emprunt(livre, membre, proprietes.emprunt().dureeJours());
        return empruntRepository.save(emprunt);
    }

    @Transactional
    public Emprunt retourner(Long idEmprunt) {
        Emprunt emprunt = empruntRepository.findById(idEmprunt)
                .orElseThrow(() -> new RessourceIntrouvableException("Emprunt", idEmprunt));

        if (emprunt.getStatut() != StatutEmprunt.EN_COURS) {
            throw new ConflitMetierException("Cet emprunt est déjà clôturé");
        }

        emprunt.enregistrerRetour(LocalDate.now());
        return emprunt;
    }
    public List<EmpruntResponse> listerEmpruntsDuMembre(Long membreId)  {

        List<Emprunt> emprunts = empruntRepository.findByMembreId(membreId);
        return emprunts.stream().map(EmpruntResponse::depuis).toList();
    }
}
