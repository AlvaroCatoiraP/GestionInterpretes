import Controler.GestionInterpretesControleur;
import Model.Bussines.Indisponibilite;
import Model.Bussines.Interprete;
import Model.Bussines.Competence;
import Model.DAO.DAOIndisponibilite;
import Model.DAO.DAOInterprete;
import Model.DAO.DAOCompetences;
import Model.Exceptions.ConnexionException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        DAOInterprete daoInterprete = new DAOInterprete();
        DAOCompetences daoCompetences = new DAOCompetences(daoInterprete.getConnection());
        DAOIndisponibilite daoIndisponibilite = new DAOIndisponibilite(daoInterprete.getConnection());

        try {

            daoInterprete.delete("i14521");

            Interprete i = new Interprete("i14521", "Catoira", "Alvaro");
            daoInterprete.create(i);
            Interprete i2 = new Interprete("i14522", "Pereyra", "Carlos");
            daoInterprete.create(i2);

            List<Interprete> interpretes = daoInterprete.findAll();
            System.out.println("Tous les interpretes :");
            for (Interprete inter : interpretes) {
                System.out.println(inter);
            }

            Competence c1 = new Competence("LSF", "Langue des signes");
            daoCompetences.create(c1);
            Competence c2 = new Competence("math", "math");
            daoCompetences.create(c2);
            Competence c3 = new Competence("EN", "Anglais");
            daoCompetences.create(c3);

            List<Competence> competences = daoCompetences.findAll();
            for (Competence comp : competences) {
                System.out.println(comp.getNom());
            }
            daoCompetences.addCompenteceToInterprete(competences.get(0),i);

            LocalDate date = LocalDate.of(2026, 3, 19);
            LocalTime heure1 = LocalTime.of(13, 00);
            LocalTime heure_fin1 = LocalTime.of(14, 00);
            Indisponibilite indisp1 = new Indisponibilite(i, date, heure1, heure_fin1);

            LocalTime heure2 = LocalTime.of(6, 00);
            LocalTime heure_fin2 = LocalTime.of(9, 00);
            Indisponibilite indisp2 = new Indisponibilite(i, date, heure2, heure_fin2);

            daoIndisponibilite.create(indisp1);
            daoIndisponibilite.create(indisp2);
            List<Indisponibilite> indisps = daoIndisponibilite.findAll();
            for (Indisponibilite indisp : indisps) {
                daoIndisponibilite.addIndisponibiliteToInterprete(indisp, i);

            }

            Interprete interpreteBd = daoInterprete.read("i14521");

            GestionInterpretesControleur controleur = new GestionInterpretesControleur();
            // pas disponible
            boolean estDispo = controleur.verifierDisponibilite(interpreteBd.getLogin(),date,heure1,heure_fin1);
            if(!estDispo)  System.out.println(interpreteBd.getPrenom() + " N'est pas disponible");

            //disponible
            estDispo = controleur.verifierDisponibilite(interpreteBd.getLogin(),date,LocalTime.of(10, 00),LocalTime.of(12, 00));
            if(estDispo) System.out.println(interpreteBd.getPrenom() + " est disponible");

            List<Interprete> interpretesBd = controleur.obtenirListeInterpretesDisponibles(date,LocalTime.of(10, 00),LocalTime.of(12, 00));

            System.out.println("---------- Interpretes disponibles ----------");
            System.out.println("Le :" + date + " " +  LocalTime.of(10, 00) + " " + LocalTime.of(12, 00));
            for (Interprete interprete : interpretesBd) {
                System.out.println(interprete);
            }

            List<Interprete> interpretesBdComp = controleur.obtenirListeInterpretesDisponibles(date,LocalTime.of(10, 00),LocalTime.of(12, 00),c1.getNom());

            System.out.println("---------- Interpretes disponibles ----------");
            System.out.println("Le :" + date + " " +  LocalTime.of(10, 00) + " " + LocalTime.of(12, 00));
            for (Interprete interprete : interpretesBd) {
                System.out.println(interprete);
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }catch (ConnexionException ex) {
            System.out.println(ex.getMessage());
        }finally {
            daoInterprete.closeConnection();
        }
    }
}