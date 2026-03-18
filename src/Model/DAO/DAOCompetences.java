package Model.DAO;

import Model.Bussines.Competence;
import Model.Bussines.Interprete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCompetences extends DAO<Competence, Integer>{

    public DAOCompetences() throws SQLException { super(); }
    public DAOCompetences(Connection connection) { super(connection); }

    @Override
    public boolean create(Competence competence) throws SQLException {
        boolean estInseree = false;
        String query = "INSERT INTO Competence (nom,description) VALUES (?,?)";

        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(query);

        ps.setString(1,competence.getNom());
        ps.setString(2, competence.getDescription());
        int nbrLigneInsert = ps.executeUpdate();
        if (nbrLigneInsert > 0) {
            estInseree = true;
        }
        return estInseree;
        }finally{
            if(ps != null) ps.close();
        }

    }

    @Override
    public Competence read(Integer id) throws SQLException {
        String query = "Select * from Competence where id = ?";

            PreparedStatement ps =null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Competence competence = new Competence(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description")
                    );
                    return competence;
                }
            }finally {
                if(ps != null) ps.close();
                if(rs != null)rs.close();
            }
        return null;
    }

    @Override
    public void update(Competence competence) throws SQLException {
        String sql = "UPDATE Competence SET nom = ?, description = ? WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, competence.getNom());
            stmt.setString(2, competence.getDescription());
            stmt.setInt(3, competence.getId());
            stmt.executeUpdate();
        }finally {
            if(stmt != null) stmt.close();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Competence WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        }finally {

        }
    }

    @Override
    public List<Competence> findAll() throws SQLException{
        List<Competence> competences = new ArrayList<>();
        String sql = "SELECT id, nom, description FROM Competence ORDER BY nom";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                competences.add(new Competence(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                ));
            }
        } finally {
            if(ps != null) ps.close();
            if(rs != null)rs.close();
        }
        return  competences;
    }

    public List<Competence> findAllbyInterprete(String login) throws SQLException {
       List<Competence> competences = findCompetencesByInterprete(login);
       return competences;
    }

    public List<Competence> findCompetencesByInterprete(String login) throws SQLException {
        String sql = """
            SELECT c.id, c.nom, c.description
            FROM Interprete_Competence ic
            JOIN Competence c ON c.id = ic.id_competence
            WHERE ic.LOGIN_INTERPRETE = ?
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

    public boolean addCompenteceToInterprete(Competence competence, Interprete interprete) throws SQLException {
        String query = "INSERT INTO Interprete_Competence (LOGIN_INTERPRETE,id_competence) VALUES (?,?)";
        PreparedStatement ps = null;
        boolean result = false;
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, interprete.getLogin());
            ps.setInt(2, competence.getId());

            int nbInserts = ps.executeUpdate();
            if(nbInserts > 0) {
                result = true;
            }
        }finally{
            if(ps != null) ps.close();
        }
        return result;
    }
}
