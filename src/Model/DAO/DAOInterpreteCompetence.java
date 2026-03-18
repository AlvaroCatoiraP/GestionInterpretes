package Model.DAO;

import Model.Bussines.Competence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOInterpreteCompetence {

    private Connection connection;

    public DAOInterpreteCompetence(Connection connection) {
        this.connection = connection;
    }

    public void addCompetenceToInterprete(String idInterprete, int idCompetence) throws Exception {

        String sql = "INSERT INTO Interprete_Competence (id_interprete, id_competence) VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, idInterprete);
        ps.setInt(2, idCompetence);

        ps.executeUpdate();
    }

    public void removeCompetenceFromInterprete(String idInterprete, int idCompetence) throws Exception {

        String sql = "DELETE FROM Interprete_Competence WHERE id_interprete = ? AND id_competence = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, idInterprete);
        ps.setInt(2, idCompetence);

        ps.executeUpdate();
    }

    public List<Competence> findCompetencesByInterprete(String login) throws SQLException {
        String sql = """
            SELECT c.id, c.nom, c.description
            FROM Interprete_Competence ic
            JOIN Competence c ON c.id = ic.id_competence
            WHERE ic.id_interprete = ?
            """;

        List<Competence> competences = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    competences.add(new Competence(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description")
                    ));
                }
            }
        }

        return competences;
    }
}