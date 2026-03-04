package Controler;

import Exceptions.ConnexionException;
import Model.Interprete;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

public class GestionInterpretesControleur implements GestionInterpretes {
    @Override
    public boolean verifierDisponibilite(String login, LocalDate date_plage, LocalTime heure_debut_plage, LocalTime heure_fin_plage) throws NoSuchElementException, ConnexionException {
        return false;
    }

    @Override
    public List<Interprete> obtenirListeInterpretesDisponibles(LocalDate date_plage, LocalTime heure_debut_plage, LocalTime heure_fin_plage) throws ConnexionException {
        return List.of();
    }

    @Override
    public List<Interprete> obtenirListeInterpretesDisponibles(LocalDate date_plage, LocalTime heure_debut_plage, LocalTime heure_fin_plage, String competence) throws ConnexionException {
        return List.of();
    }
}
