package Model.DAO;

import Model.Bussines.Indisponibilite;
import Model.Bussines.Interprete;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

        PreparedStatement ps = null;
        try {

            ps = connection.prepareStatement(sql);

            ps.setString(1, indisponibilite.getinterprete().getLogin());
            ps.setDate(2, Date.valueOf(indisponibilite.getDate()));
            ps.setTime(3, Time.valueOf(indisponibilite.getHeure_debut()));
            ps.setTime(4, Time.valueOf(indisponibilite.getHeure_fin()));

            return ps.executeUpdate() > 0;
        }finally{
            if(ps != null) ps.close();

        }
    }

    @Override
    public Indisponibilite read(Integer id) throws SQLException {

        String sql = "SELECT * FROM indisponibilite WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {

                String idInterprete = rs.getString("id_interprete");

                DAOInterprete daoInterprete = new DAOInterprete(connection);
                Interprete interprete = daoInterprete.read(idInterprete);

                return new Indisponibilite(
                        rs.getInt("id"),
                        interprete,
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()
                );
            }
        }finally {
            if(ps != null) ps.close();
            rs.close();
        }
        return null;
    }

    @Override
    public void update(Indisponibilite indisponibilite) throws SQLException {

        String sql = "UPDATE indisponibilite SET id_interprete=?, date_indisponibilite=?, heure_debut=?, heure_fin=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);

            ps.setString(1, indisponibilite.getinterprete().getLogin());
            ps.setDate(2, Date.valueOf(indisponibilite.getDate()));
            ps.setTime(3, Time.valueOf(indisponibilite.getHeure_debut()));
            ps.setTime(4, Time.valueOf(indisponibilite.getHeure_fin()));

            ps.executeUpdate();
        }finally {
            if(ps != null) ps.close();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {

        String sql = "DELETE FROM indisponibilite WHERE id=?";
        PreparedStatement ps = null;
        try{
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        }finally{
            if(ps != null) ps.close();
        }

    }

    @Override
    public List<Indisponibilite> findAll() throws SQLException {

        List<Indisponibilite> liste = new ArrayList<>();

        String sql = "SELECT * FROM indisponibilite";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            DAOInterprete daoInterprete = new DAOInterprete(connection);

            while (rs.next()) {

                String idInterprete = rs.getString("id_interprete");

                Interprete interprete = daoInterprete.read(idInterprete);

                liste.add(new Indisponibilite(
                        rs.getInt("id"),
                        interprete,
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()
                ));
            }

            return liste;
        }finally {
            if(ps != null) ps.close();
            rs.close();
        }
    }

    public List<Indisponibilite> findAllbyInterprete(String login) throws SQLException {
        List<Indisponibilite> liste = new ArrayList<>();
        String sql = "SELECT * FROM indisponibilite WHERE id_interprete=?";
        PreparedStatement ps =  null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, login);

            rs = ps.executeQuery();
            Interprete interprete = new Interprete();
            interprete.setLogin(login);

            while (rs.next()) {
                liste.add(new Indisponibilite(
                        rs.getInt("id"),
                        interprete,
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()
                ));
            }
            return liste;
        }finally {
            if(ps != null) ps.close();
            rs.close();
        }
    }

    public List<Indisponibilite> findAllbyInterpreteAndDate(String login, LocalDate date) throws SQLException {

        List<Indisponibilite> liste = new ArrayList<>();

        String sql = "SELECT * FROM indisponibilite WHERE id_interprete=? AND date_indisponibilite=?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setDate(2, Date.valueOf(date));

            rs = ps.executeQuery();

            DAOInterprete daoInterprete = new DAOInterprete(connection);
            Interprete interprete = daoInterprete.read(login);

            while (rs.next()) {

                liste.add(new Indisponibilite(
                        rs.getInt("id"),
                        interprete,
                        rs.getDate("date_indisponibilite").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getTime("heure_fin").toLocalTime()
                ));
            }
        }finally {
            if(ps != null) ps.close();
            rs.close();
        }
        return liste;
    }

    /**
     * recupere la liste des interpretes disponibles pendant une plage horaire
     * @param date_plage date de la plage horaire
     * @param heure_debut_plage heure de debut de la plage horaire
     * @param heure_fin_plage heure de fin de la plage horaire
     * @return la liste des interpretes disponibles
     * @throws SQLException si une erreur
     */
    public List<Interprete> findAllInterpretDisponibles(LocalDate date_plage,
                                                        LocalTime heure_debut_plage,
                                                        LocalTime heure_fin_plage) throws SQLException {

        String query =
                "SELECT DISTINCT i.* " +
                        "FROM interprete i " +
                        "WHERE NOT EXISTS ( " +
                        "    SELECT 1 " +
                        "    FROM indisponibilite ind " +
                        "    WHERE ind.id_interprete = i.login " +
                        "      AND ind.date_indisponibilite = ? " +
                        "      AND ind.heure_debut < ? " +
                        "      AND ind.heure_fin > ? " +
                        ")";

        List<Interprete> interpretes = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(query);

            ps.setDate(1, Date.valueOf(date_plage));
            ps.setTime(2, Time.valueOf(heure_fin_plage));
            ps.setTime(3, Time.valueOf(heure_debut_plage));

            rs = ps.executeQuery();
            while (rs.next()) {
                Interprete i = new Interprete();
                i.setLogin(rs.getString("login"));
                i.setNom(rs.getString("nom"));
                i.setPrenom(rs.getString("prenom"));
                interpretes.add(i);
            }
        }finally {
            if(ps != null) ps.close();
            rs.close();
        }
        return interpretes;
    }
}