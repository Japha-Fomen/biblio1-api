package com.example.biblio1_api.Exception;

public class RessourceIntrouvableException extends RuntimeException{
    public RessourceIntrouvableException(String ressource,Long id){
    super(ressource + " avec id " + id + " introuvable");
}

public RessourceIntrouvableException(String message) {
    super(message);
}
}
