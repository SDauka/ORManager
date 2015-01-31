package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.dao.GamesDAO;
import kz.sdauka.ormanager.entity.Game;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dauletkhan on 14.01.2015.
 */
public class GamesDAOImpl implements GamesDAO {
    private static final Logger LOG = Logger.getLogger(GamesDAOImpl.class);

    @Override
    public List<Game> getAllGames() throws SQLException {
        Session session = null;
        List games = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            games = session.createCriteria(Game.class).list();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные " + e);
            Dialogs.create().title("Ошибка загрузки данных").message("Не удалось загрузить данные").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return games;
    }

    @Override
    public void setGame(Game game) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось добавить игру " + e);
            Dialogs.create().title("Ошибка при вставке").message("Не удалось добавить игру").showError();
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    @Override
    public void updateGame(Game game) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось обновить игру " + e);
            Dialogs.create().title("Ошибка при обновлении").message("Не удалось обновить игру").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteGame(Game game) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось удалить игру " + e);
            Dialogs.create().title("Ошибка при удалении").message("Не удалось удалить игру").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
