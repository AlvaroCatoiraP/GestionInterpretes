package Model.Bussines;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe pour representer un iterprete
 */
public class Interprete {

    private String login;
    private String nom;
    private String prenom;

    private List<Indisponibilite> indisponibilites;
    private List<Competence> competances;

    /**
     *
     * @param login identifiant de l'interprete
     * @param nom
     * @param prenom
     */
    public Interprete(String login, String nom, String prenom) {
        this.login = login;
        this.indisponibilites = new ArrayList<>();
        this.competances = new ArrayList<>();
        this.nom = nom;
        this.prenom = prenom;
    }

    public Interprete() {

    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Indisponibilite> getIndisponibilites() {
        return indisponibilites;
    }

    public void setIndisponibilites(List<Indisponibilite> indisponibilites) {
        this.indisponibilites = indisponibilites;
    }

    public List<Competence> getCompetances() {
        return competances;
    }

    public void setCompetances(List<Competence> competances) {
        this.competances = competances;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
