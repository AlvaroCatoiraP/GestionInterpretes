package Controleur;

import Exceptions.ConnexionException;
import Metier.Interprete;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * controleur qui gère le filtre des interpretes disponibles
 */
public interface GestionInterpretes {

    /**
     * verifier la disponibilite d'un interprete pendant une plage horaire donnee
     * @param login identifiant de l'interprete
     * @param date_plage date de la plage horaire
     * @param heure_debut_plage heure de debut de la plage horaire
     * @param heure_fin_plage heure de fin de la plage horaire
     * @return true si l'interpete identifiee par login est disponible  durant la plage horaire date_plage, entre les heures heure_debut_plage et heure_fin_plage
     * @throws NoSuchElementException si il n'existe pas d'interprete identifiee par login
     * @throws ConnexionException si probleme quelconque de connexion
     */
    boolean verifierDisponibilite(String login, LocalDate date_plage, LocalTime heure_debut_plage, LocalTime heure_fin_plage)
            throws NoSuchElementException, ConnexionException;

    /**
     * recuperer la liste des interpretes disponibles pendant une plage horaire donnee
     * @param date_plage date de la plage horaire
     * @param heure_debut_plage heure de debut de la plage horaire
     * @param heure_fin_plage heure de fin de la plage horaire
     * @return la liste des interpretes disponibles durant la plage horaire date_plage, entre les heures heure_debut_plage et heure_fin_plage
     * @throws ConnexionException si probleme quelconque de connexion
     */
    List<Interprete> obtenirListeInterpretesDisponibles(LocalDate date_plage , LocalTime heure_debut_plage, LocalTime heure_fin_plage)
            throws ConnexionException;

    /**
     * recuperer la liste des interpretes ayant la compétence exigee, disponibles pendant une plage horaire donnee
     * @param date_plage date de la plage horaire
     * @param heure_debut_plage heure de debut de la plage horaire
     * @param heure_fin_plage heure de fin de la plage horaire
     * @param competence competence exigee
     * @return la liste des interpretes disponibles durant la plage horaire date_plage, entre les heures heure_debut_plage et heure_fin_plage
     * @throws ConnexionException si probleme quelconque de connexion
     * @throws NoSuchElementException si competence n'existe pas
     */
    List<Interprete> obtenirListeInterpretesDisponibles(LocalDate date_plage , LocalTime heure_debut_plage, LocalTime heure_fin_plage, String competence)
            throws ConnexionException;



}
