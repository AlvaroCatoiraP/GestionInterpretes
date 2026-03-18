package Model.Bussines;

import java.util.Objects;

public class Competence {
    private int id;
    private String nom;
    private String description;

    public Competence(int id,String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;

    }

    public String getNom() {
        return nom;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Competence that = (Competence) o;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(description, that.description);
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
