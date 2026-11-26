package com.example.biblio1_api.Service;

import com.example.biblio1_api.Entity.Role;
import com.example.biblio1_api.Entity.Utilisateur;
import com.example.biblio1_api.Exception.RessourceIntrouvableException;
import com.example.biblio1_api.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {
    private UtilisateurRepository repository;

    public UserDetailService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional

    public UserDetails loadUserByUsername(String email){
        Utilisateur utilisateur = repository.findByEmail(email)
                .orElseThrow(()->new RessourceIntrouvableException("Utilisateur",email));
        List<SimpleGrantedAuthority> autorites=utilisateur.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).toList();
        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .authorities(autorites)
                .disabled(!utilisateur.isActif())
                .build();
    }
}
