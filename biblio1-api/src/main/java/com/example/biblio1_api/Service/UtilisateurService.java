package com.example.biblio1_api.Service;

import com.example.biblio1_api.Config.ConfigurationSecuriteClassique;
import com.example.biblio1_api.Dto.UtilisateurCreationRequest;
import com.example.biblio1_api.Dto.UtilisateurResponse;
import com.example.biblio1_api.Dto.UtilisateurUpdateRequest;
import com.example.biblio1_api.Entity.Role;
import com.example.biblio1_api.Entity.Utilisateur;
import com.example.biblio1_api.Exception.ConflitMetierException;
import com.example.biblio1_api.Exception.RequeteInvalideException;
import com.example.biblio1_api.Exception.RessourceIntrouvableException;
import com.example.biblio1_api.Mapper.UtilisateurMapperGenere;
import com.example.biblio1_api.Repository.UtilisateurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
@Transactional(readOnly=true)
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapperGenere utilisateurMapper;
    private final PasswordEncoder encoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, UtilisateurMapperGenere utilisateurMapper,PasswordEncoder encoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.encoder = encoder;
    }

    //recheche
    public UtilisateurResponse getById(Long id) {
        Utilisateur utilisateur = utilisateurRepository
                .findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", id));
        return utilisateurMapper.versResponse(utilisateur);
    }

    public UtilisateurResponse getByEmail(String email) {
        return utilisateurRepository.findByEmail(email).map(utilisateurMapper::versResponse)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", email));
    }

    public Page<UtilisateurResponse> listerAll(Pageable pageable) {
        return utilisateurRepository.findAll(pageable).map(utilisateurMapper::versResponse);
    }

    public Page<UtilisateurResponse> findByRole(Role role, Pageable pageable) {
        return utilisateurRepository.findByRolesContains(role, pageable).map(utilisateurMapper::versResponse);
    }

    @Transactional
    public Utilisateur create(UtilisateurCreationRequest requete, Set<Role> role) {
        if (utilisateurRepository.existsByEmail(requete.email())) {
            throw new ConflitMetierException("Un utilisateur avec l'email  " + requete.email() + " existe déjà");
        }

        Utilisateur utilisateur = utilisateurMapper.versEntity(requete);
        utilisateur.setRoles(role);
        utilisateur.setPassword(encoder.encode(requete.password()));
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    @Transactional
    public UtilisateurResponse update(UtilisateurUpdateRequest requete, Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", id));
        if (requete.nom() != null) {
            utilisateur.setNom(requete.nom());
        }
        if (requete.password() != null) {
            if (requete.OldPassword()==null){
                throw new RequeteInvalideException("veuillez saisir l'ancien mot de passe si vous voulez changer de mot de passe");
            }
            if(!encoder.matches(
                    requete.OldPassword(),
                    utilisateur.getPassword())) {

                throw new ConflitMetierException(
                        "L'ancien mot de passe est incorrect");
            }
            if (requete.password().equals(requete.OldPassword())) {
                throw new ConflitMetierException("Le nouveau mot de passe est identique à l'ancien");
            }

            utilisateur.setPassword(
                    encoder.encode(requete.password())
            );
        }
        if (requete.prenom() != null) {
            utilisateur.setPrenom(requete.prenom());
        }
        if (requete.email() != null &&
                !requete.email().equals(utilisateur.getEmail())) {

            if (utilisateurRepository.existsByEmail(requete.email())) {
                throw new ConflitMetierException(
                        "Email déjà utilisé");
            }

            utilisateur.setEmail(requete.email());
        }
        return utilisateurMapper.versResponse(utilisateur);

    }

    @Transactional
    public void ajouterRole(Long id, Role role) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", id));
        if (utilisateur.getRoles().contains(role)) {
            throw new ConflitMetierException("l'utilisateur possede deja le role: " + role);
        }
        utilisateur.getRoles().add(role);
    }

    @Transactional
    public void  retirerRole(Long id, Role role) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", id));
        if (!utilisateur.getRoles().contains(role)) {
            throw new ConflitMetierException(
                    "L'utilisateur ne possède pas le rôle " + role);
        }
        utilisateur.getRoles().remove(role);
    }
    @Transactional
    public void delete(Long id) {
        if (!utilisateurRepository.existsById(id)){
            throw new RessourceIntrouvableException("Utilisateur",id);
        }
        utilisateurRepository.deleteById(id);
    }
}
