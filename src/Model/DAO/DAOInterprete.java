package Model.DAO;

import Model.Bussines.Interprete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOInterprete extends DAO<Interprete , String> {

    public DAOInterprete() throws SQLException {
        super();
    }

    public DAOInterprete(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Interprete interprete) throws SQLException {
        boolean estInseree = false;
        String query = "INSERT INTO Interprete (login,nom,prenom) VALUES (?,?,?)";
        PreparedStatement prStat = null;
        try {
            prStat = connection.prepareStatement(query);
            prStat.setString(1, interprete.getLogin());
            prStat.setString(2, interprete.getNom());
            prStat.setString(3, interprete.getPrenom());

            int nbrLigneInsert = prStat.executeUpdate();
            if (nbrLigneInsert > 0) {
                estInseree = true;
            }
            return estInseree;
        } finally {
            if(prStat != null) prStat.close();
        }
    }

    @Override
    public Interprete read(String login) throws SQLException {
        String sql = "SELECT login, nom, prenom FROM Interprete WHERE login = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Interprete interprete = new Interprete(
                        rs.getString("login"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                );
                DAOCompetences competences = new DAOCompetences(connection);
                interprete.setCompetances(competences.findAllbyInterprete(login));

                DAOIndisponibilite daoIndisponibilite = new DAOIndisponibilite(connection);
                interprete.setIndisponibilites(daoIndisponibilite.findAllbyInterprete(login));
                return interprete;
            }

        } finally {
            if(stmt != null) stmt.close();
            if(rs != null)rs.close();
        }
        return null;
    }

    @Override
    public void update(Interprete interprete) throws SQLException {
        String sql = "UPDATE Interprete SET nom = ?, prenom = ? WHERE login = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, interprete.getNom());
            stmt.setString(2, interprete.getPrenom());
            stmt.setString(3, interprete.getLogin());
            stmt.executeUpdate();
        }finally {
            if(stmt != null) stmt.close();
        }
    }

    @Override
    public void delete(String login) throws SQLException {
        String sql = "DELETE FROM Interprete WHERE login = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.executeUpdate();
            System.out.println(login + " supprimer");
        }finally{
            if(stmt != null) stmt.close();
        }
    }

    @Override
    public List<Interprete> findAll() throws SQLException {
        List<Interprete> interpretes = new ArrayList<>();
        String sql = "SELECT LOGIN, NOM, PRENOM FROM Interprete ORDER BY NOM";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                interpretes.add(new Interprete(
                        rs.getString("login"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                ));
            }
            fillInterpreteList(interpretes);
        }finally {
            if(stmt != null) stmt.close();
            if(rs != null)rs.close();
        }
        return interpretes;

    }

    /**
     *
     * @param interpretes liste d'interprete sans ses disponibilité
     */
    public void fillInterpreteList(List<Interprete> interpretes) throws SQLException {

        for(Interprete interprete : interpretes){

            DAOCompetences competences = new DAOCompetences(connection);
            interprete.setCompetances(competences.findAllbyInterprete(interprete.getLogin()));

            DAOIndisponibilite daoIndisponibilite = new DAOIndisponibilite(connection);
            interprete.setIndisponibilites(daoIndisponibilite.findAllbyInterprete(interprete.getLogin()));
        }
    }
}
