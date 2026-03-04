package DAO;

import Model.Indisponibilite;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DAOIndisponibilite extends DAO<Indisponibilite,Integer> {

    public DAOIndisponibilite() throws SQLException { super(); }
    public DAOIndisponibilite(Connection connection) { super(connection); }



    @Override
    public boolean create(Indisponibilite obj) throws SQLException {
        return false;
    }

    @Override
    public Indisponibilite read(Integer integer) {
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
