package DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Classe abstraite générique implémentant le pattern DAO.
 * Fournit une base commune pour tous les objets d'accès aux données
 * avec une connexion automatique à la base de données Oracle.
 *
 * @param <T> Le type d'entité géré
 * @param <ID> Le type d'id géré
 */
public abstract class DAO<T, ID> {

    protected Connection connection;

    /**
     * Constructeur par défaut : initialise automatiquement la connexion Oracle. identifiants à demander
     */
    public DAO() throws SQLException {
        try {


            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            this.connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new SQLException(" Driver Oracle introuvable !", e);
        } catch (SQLException e) {
            throw new SQLException(" Échec de la connexion à la base de données !", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructeur avec connexion partagée.
     * @param connection La connexion à utiliser
     */
    public DAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retourne la connexion actuelle.
     * @return La connexion à la base de données
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Ferme la connexion à la BD.
     */
    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Connexion fermée avec succès.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }

    /**
     * Crée une nouvelle entité en BDD.
     *
     * @param obj L'objet à créer
     * @return
     */
    public abstract boolean create(T obj) throws SQLException;

    /**
     * Récupère une entité dans la BDD via une clé primaire .
     * @param id Identifiant unique
     * @return Entité trouvée ou null
     */
    public abstract T read(ID id) throws SQLException;

    /**
     * Met à jour une entité existante dans la BDD.
     * @param obj L'objet contenant les nouvelles données
     */
    public abstract void update(T obj);

    /**
     * Supprime une entité de la BDD par son identifiant.
     * @param id Identifiant unique
     */
    public abstract void delete(ID id);

    /**
     * Récupère toutes les entités de la BDD.
     * @return Liste de toutes les entités
     */
    public abstract List<T> findAll();
}