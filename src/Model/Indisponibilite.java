package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Indisponibilite {

    private int id;
    private String id_interprete;
    private LocalDate date;
    private LocalTime heure_debut;
    private LocalTime heure_fin;

    public Indisponibilite(int id, String id_interprete, LocalDate date, LocalTime heure_debut, LocalTime heure_fin) {
        this.id = id;
        this.id_interprete = id_interprete;
        this.date = date;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_interprete() {
        return id_interprete;
    }

    public void setId_interprete(String id_interprete) {
        this.id_interprete = id_interprete;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(LocalTime heure_debut) {
        this.heure_debut = heure_debut;
    }

    public LocalTime getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(LocalTime heure_fin) {
        this.heure_fin = heure_fin;
    }
}
