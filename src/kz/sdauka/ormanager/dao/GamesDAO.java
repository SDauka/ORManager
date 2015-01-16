package kz.sdauka.ormanager.dao;

import kz.sdauka.ormanager.entity.Game;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dauletkhan on 14.01.2015.
 */
public interface GamesDAO {
    public List<Game> getAllGames() throws SQLException;

    public void setGame(Game game) throws SQLException;

    public void updateGame(Game game) throws SQLException;

    public void deleteGame(Game game) throws SQLException;
}
