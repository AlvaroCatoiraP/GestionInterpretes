package DAO;

import Model.Interprete;

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
        try {
            PreparedStatement prStat = connection.prepareStatement(query);
            prStat.setString(1, interprete.getLogin());
            prStat.setString(2, interprete.getNom());
            prStat.setString(3, interprete.getPrenom());

            int nbrLigneInsert = prStat.executeUpdate();
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
    public Interprete read(String login) {
        String sql = "SELECT login, nom, prenom FROM Interprete WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
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


        } catch (SQLException ex) {
            System.err.println(" Erreur read Interprete: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Interprete interprete) {
        String sql = "UPDATE Interprete SET nom = ?, prenom = ? WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, interprete.getNom());
            stmt.setString(2, interprete.getPrenom());
            stmt.setString(3, interprete.getLogin());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(" Erreur update Interprete: " + ex.getMessage());
        }
    }

    @Override
    public void delete(String login) {
        String sql = "DELETE FROM Interprete WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
            // mettre en place le delete en cascade pour les competences et les indisponibilité coté BD
        } catch (SQLException ex) {
            System.err.println(" Erreur update Interprete: " + ex.getMessage());
        }
    }

    @Override
    public List<Interprete> findAll() {
        List<Interprete> interpretes = new ArrayList<>();
        String sql = "SELECT login, nom, prenom FROM Interprete ORDER BY nom";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                interpretes.add(new Interprete(
                        rs.getString("login"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                ));
            }
            fillInterpreteList(interpretes);
        } catch (SQLException ex) {
            System.err.println(" Erreur findAll interpretes: " + ex.getMessage());
        }
        return interpretes;

    }

    /**
     *
     * @param interpretes liste d'interprete sans ses disponibilité
     */
    public void fillInterpreteList(List<Interprete> interpretes){

        for(Interprete interprete : interpretes){

            DAOCompetences competences = new DAOCompetences(connection);
            interprete.setCompetances(competences.findAllbyInterprete(interprete.getLogin()));

            DAOIndisponibilite daoIndisponibilite = new DAOIndisponibilite(connection);
            interprete.setIndisponibilites(daoIndisponibilite.findAllbyInterprete(interprete.getLogin()));
        }
    }
}
