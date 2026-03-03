package Metier;

import java.time.LocalDate;
import java.time.LocalTime;

public class Indisponibilite {

    private LocalDate date;
    private LocalTime heure_debut;
    private LocalTime heure_fin;

    public Indisponibilite(LocalDate date, LocalTime heure_debut, LocalTime heure_fin) {
        this.date = date;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }
}
