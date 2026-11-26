package com.example.biblio1_api.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
@EnableWebSecurity
public class ConfigurationSecuriteClassique {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requetes -> requetes

                                // ---- 1. Acces public ----
                                .requestMatchers(HttpMethod.GET, "/", "/index.html", "/favicon.ico").permitAll()
                                .requestMatchers("/actuator/health").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/utilisateurs/inscriptions").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/livres/**", "/api/auteurs/**").permitAll()

                                // ---- 2. Attribution des roles : administrateur seulement ----
                                .requestMatchers("/api/utilisateurs/*/roles/**").hasRole("ADMIN")

                                // ---- 3. Annuaire des comptes : administrateur seulement ----
                                .requestMatchers(HttpMethod.GET, "/api/utilisateurs").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/utilisateurs/par-role").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/utilisateurs/par-email/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/utilisateurs/*").hasRole("ADMIN")

                                // ---- 4. Fiche d'un compte : la propriete est verifiee dans le service ----
                                .requestMatchers(HttpMethod.GET, "/api/utilisateurs/*").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/utilisateurs/*").authenticated()

                        // ---- 5. Membres : bibliothecaire seulement ----
                .requestMatchers("/api/membres/**").hasRole("BIBLIOTHECAIRE")

                // ---- 6. Catalogue en ecriture : les GET sont deja passes en 1 ----
                .requestMatchers("/api/livres/**", "/api/auteurs/**").hasRole("BIBLIOTHECAIRE")

                // ---- 7. Emprunts ----
                .requestMatchers(HttpMethod.POST, "/api/emprunts/*/retour").hasRole("BIBLIOTHECAIRE")
                .requestMatchers("/api/emprunts/**").authenticated()

                .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Profile("demo")
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
       UserDetails lecteur= User.builder()
               .username("lecteur")
               .password(passwordEncoder.encode("lecteur123"))
               .roles("LECTEUR")
               .build();
       UserDetails bibliothecaire= User.builder()
               .username("marie")
               .password(passwordEncoder.encode("marie123"))
               .roles("BIBLIOTHECAIRE","LECTEUR")
               .build();
       UserDetails admin=User.builder()
               .username("admin")
               .password(passwordEncoder.encode("admin123"))
               .roles("ADMIN","BIBLIOTHECAIRE","LECTEUR")
               .build();
       return  new InMemoryUserDetailsManager(lecteur,bibliothecaire,admin);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
