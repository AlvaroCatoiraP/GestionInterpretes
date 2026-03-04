package DAO;

import Model.Competence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOInterpreteCompetence {

    private Connection connection;

    public DAOInterpreteCompetence(Connection connection) {
        this.connection = connection;
    }

    public void addCompetenceToInterprete(int idInterprete, int idCompetence) throws Exception {

        String sql = "INSERT INTO Interprete_Competence (id_interprete, id_competence) VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idInterprete);
        ps.setInt(2, idCompetence);

        ps.executeUpdate();
    }

    public void removeCompetenceFromInterprete(int idInterprete, int idCompetence) throws Exception {

        String sql = "DELETE FROM Interprete_Competence WHERE id_interprete = ? AND id_competence = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idInterprete);
        ps.setInt(2, idCompetence);

        ps.executeUpdate();
    }

    public List<Competence> findCompetencesByInterprete(String login) throws Exception {

        String sql = "SELECT id_competence FROM Interprete_Competence WHERE id_interprete = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, login);

        ResultSet rs = ps.executeQuery();

        List<Competence> competences = new ArrayList<>();

        while (rs.next()) {
            competences.add(new Competence(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description")
            ));
        }

        return competences;
    }
}