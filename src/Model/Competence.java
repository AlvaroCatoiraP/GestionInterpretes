package Model;

public class Competence {
    private int id;
    private String nom;
    private String description;

    public Competence(int id,String nom, String description) {
        this.nom = nom;
        this.description = description;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {}
}
