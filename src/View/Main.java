package View;

import Model.Bussines.Interprete;
import Model.Bussines.Competence;
import Model.DAO.DAOInterprete;
import Model.DAO.DAOCompetences;

import java.util.List;

public class Main {
    // uniquement pour tester des connexions
    public static void main(String[] args) throws Exception {

        DAOInterprete daoInterprete = new DAOInterprete();

        daoInterprete.delete("i14521");

        Interprete i = new Interprete("i14521", "Catoira", "Alvaro");
        daoInterprete.create(i);

        Interprete interpreteBd = daoInterprete.read("i14521");
        System.out.println(interpreteBd.getLogin() + " " + interpreteBd.getNom() + " " + interpreteBd.getPrenom());

        List<Interprete> interpretes = daoInterprete.findAll();
        for (Interprete inter : interpretes) {
            System.out.println(inter.getLogin());
        }

        DAOCompetences daoCompetences = new DAOCompetences(daoInterprete.getConnection());

        Competence c = new Competence(0, "LSF", "Langue des signes");
        daoCompetences.create(c);

        List<Competence> competences = daoCompetences.findAll();
        for (Competence comp : competences) {
            System.out.println(comp.getNom());
        }

        daoInterprete.closeConnection();
    }
}