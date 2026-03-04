package DAO;

import Model.Indisponibilite;
import Model.Interprete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOIndisponibilite extends DAO<Indisponibilite, Integer> {

    public DAOIndisponibilite() throws SQLException {
        super();
    }

    public DAOIndisponibilite(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Indisponibilite indisponibilite) throws SQLException {

        String sql = "INSERT INTO indisponibilite (id_interprete, date_indisponibilite, heure_debut, heure_fin) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, indisponibilite.getId_interprete());
        ps.setDate(2, Date.valueOf(indisponibilite.getDate()));
        ps.setTime(3, Time.valueOf(indisponibilite.getHeure_debut()));
        ps.setTime(4, Time.valueOf(indisponibilite.getHeure_fin()));

        return ps.executeUpdate() > 0;
    }

    @Override
    public Indisponibilite read(Integer id) {

        String sql = "SELECT * FROM indisponibilite WHERE id = ?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String idInterprete = rs.getString("id_interprete");

                DAOInterprete daoInterprete = new DAOInterprete(connection);
                Interprete interprete = daoInterprete.read(idInterprete);

                return new Indisponibilite(
                        rs.getInt("id"),
                        rs.getString("id_interprete"),
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()
                );
            }

        } catch (Exception e) {
            System.err.println("Erreur read indisponibilite : " + e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Indisponibilite indisponibilite) {

        String sql = "UPDATE indisponibilite SET id_interprete=?, date_indisponibilite=?, heure_debut=?, heure_fin=? WHERE id=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, indisponibilite.getId_interprete());
            ps.setDate(2, Date.valueOf(indisponibilite.getDate()));
            ps.setTime(3, Time.valueOf(indisponibilite.getHeure_debut()));
            ps.setTime(4, Time.valueOf(indisponibilite.getHeure_fin()));

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Erreur update indisponibilite : " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {

        String sql = "DELETE FROM indisponibilite WHERE id=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Erreur delete indisponibilite : " + e.getMessage());
        }
    }

    @Override
    public List<Indisponibilite> findAll() {

        List<Indisponibilite> liste = new ArrayList<>();

        String sql = "SELECT * FROM indisponibilite";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DAOInterprete daoInterprete = new DAOInterprete(connection);

            while (rs.next()) {

                String idInterprete = rs.getString("id_interprete");

                Interprete interprete = daoInterprete.read(idInterprete);

                liste.add(new Indisponibilite(
                        rs.getInt("id"),
                        rs.getString("id_interprete"),
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()
                ));
            }

        } catch (Exception e) {
            System.err.println("Erreur findAll indisponibilite : " + e.getMessage());
        }

        return liste;
    }

    public List<Indisponibilite> findAllbyInterprete(String login) {

        List<Indisponibilite> liste = new ArrayList<>();

        String sql = "SELECT * FROM indisponibilite WHERE id_interprete=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            DAOInterprete daoInterprete = new DAOInterprete(connection);
            Interprete interprete = daoInterprete.read(login);

            while (rs.next()) {

                liste.add(new Indisponibilite(
                        rs.getInt("id"),
                        rs.getString("id_interprete"),
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()
                ));
            }

        } catch (Exception e) {
            System.err.println("Erreur findAllByInterprete : " + e.getMessage());
        }

        return liste;
    }
}