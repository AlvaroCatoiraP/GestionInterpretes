package DAO;

import Metier.Competence;
import Metier.Interprete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCompetences extends DAO<Competence, Integer>{

    public DAOCompetences() throws SQLException { super(); }
    public DAOCompetences(Connection connection) { super(connection); }

    @Override
    public boolean create(Competence competence) throws SQLException {
        boolean estInseree = false;
        String query = "INSERT INTO Competence (nom,descriptio) VALUES (?,?)";
        try
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,competence.getNom());
            ps.setString(2, competence.getDescription());
            int nbrLigneInsert = ps.executeUpdate();
            if (nbrLigneInsert > 0) {
                estInseree = true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion");
        } finally {
            if (connection != null) connection.close();
        }
        return estInseree;

    }

    @Override
    public Competence read(Integer id) throws SQLException {
        String query = "Select * from Competence where id = ?";
        try
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Competence competence = new Competence(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                );
                return competence;
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion");
        } finally {
            if (connection != null) connection.close();
        }
        return null;
    }

    @Override
    public void update(Competence competence) {
        String sql = "UPDATE Competence SET nom = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, competence.getNom());
            stmt.setString(2, competence.getDescription());
            stmt.setInt(3, competence.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(" Erreur update competence: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Competence WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(" Erreur update Competence: " + ex.getMessage());
        }
    }

    @Override
    public List<Competence> findAll() {
        List<Competence> competences = new ArrayList<>();
        String sql = "SELECT id, nom, description FROM Competence ORDER BY nom";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                competences.add(new Competence(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  competences;
    }

    public List<Competence> findAllbyInterprete(String login) {
        return List.of();
    }
}
