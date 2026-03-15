package Controler;

import Model.DAO.DAOInterprete;
import Model.Exceptions.ConnexionException;
import Model.Bussines.Indisponibilite;
import Model.Bussines.Interprete;
import Model.DAO.DAOIndisponibilite;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

public class GestionInterpretesControleur implements GestionInterpretes {

    @Override
    public boolean verifierDisponibilite(String login,
                                         LocalDate date_plage,
                                         LocalTime heure_debut_plage,
                                         LocalTime heure_fin_plage)
            throws NoSuchElementException, ConnexionException {

        if (heure_debut_plage == null || heure_fin_plage == null || date_plage == null) {
            throw new IllegalArgumentException("Date et heures ne peuvent pas être null.");
        }
        if (!heure_debut_plage.isBefore(heure_fin_plage)) {
            throw new IllegalArgumentException("L'heure de début doit être avant l'heure de fin.");
        }

        List<Indisponibilite> indispos;
        try {
            DAOIndisponibilite daoIndisponibilite = new DAOIndisponibilite();
            indispos = daoIndisponibilite.findAllbyInterpreteAndDate(login,date_plage);
        } catch (Exception e) {
            throw new ConnexionException("Erreur lors de la récupération des indisponibilités : " + e.getMessage());
        }

        if (indispos == null || indispos.isEmpty()) {
            return true;
        }

        for (Indisponibilite ind : indispos) {
            LocalTime debutIndispo = ind.getHeure_debut();
            LocalTime finIndispo = ind.getHeure_fin();

            if (debutIndispo.isBefore(heure_fin_plage) && finIndispo.isAfter(heure_debut_plage)){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Interprete> obtenirListeInterpretesDisponibles(LocalDate date_plage, LocalTime heure_debut_plage, LocalTime heure_fin_plage) throws ConnexionException, SQLException {

        DAOIndisponibilite daoIndisponibilite = new DAOIndisponibilite();
        List<Interprete> interpretes = daoIndisponibilite.findAllInterpretDisponibles(date_plage, heure_debut_plage, heure_fin_plage);
        return interpretes;
    }

    @Override
    public List<Interprete> obtenirListeInterpretesDisponibles(LocalDate date_plage, LocalTime heure_debut_plage, LocalTime heure_fin_plage, String competence) throws ConnexionException, SQLException {

        DAOInterprete daoInterprete = new DAOInterprete();
        List<Interprete> interpretes = daoInterprete.findAll();

        List<Interprete> interpretesDisponibles = interpretes.stream()
                .filter(i -> i.getCompetances().contains(competence))
                .filter(i -> i.getIndisponibilites().stream()
                        .noneMatch(ind ->
                                ind.getHeure_debut().isBefore(heure_fin_plage)
                                        && ind.getHeure_fin().isAfter(heure_debut_plage)
                        ))
                .toList();
        return interpretesDisponibles;
    }
}
