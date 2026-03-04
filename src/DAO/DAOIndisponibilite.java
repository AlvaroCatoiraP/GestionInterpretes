package DAO;

import Model.Indisponibilite;
import Model.Interprete;

import java.sql.*;
import java.util.List;

public class DAOIndisponibilite extends DAO<Indisponibilite,Integer> {

    public DAOIndisponibilite() throws SQLException { super(); }
    public DAOIndisponibilite(Connection connection) { super(connection); }

    @Override
    public boolean create(Indisponibilite indisponibilite) throws SQLException {
        Boolean estInseree = false;
        String query = "INSERT INTO indisponibilite (id_interprete,heure_debut,heure_fin) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, indisponibilite.getId_interprete());
            ps.setTime(1, Time.valueOf(indisponibilite.getHeure_debut()));
            ps.setTime(2, Time.valueOf(indisponibilite.getHeure_fin()));


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
    public Indisponibilite read(Integer id) {
        String sql = "SELECT id_interprete, heure_debut, heure_fin FROM indisponibilite WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Indisponibilite indisponibilite = new Indisponibilite(
                        rs.getInt("id"),
                        rs.getString("id_interprete"),
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()

                );
                return indisponibilite;
            }

        } catch (Exception ex) {
            System.err.println(" Erreur read Interprete: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Indisponibilite obj) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Indisponibilite> findAll() {
        return List.of();
    }

    public List<Indisponibilite> findAllbyInterprete(String login) {
        return List.of();
    }
}
