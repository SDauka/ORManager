package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.dao.GamesDAO;
import kz.sdauka.ormanager.entity.Game;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dauletkhan on 14.01.2015.
 */
public class GamesDAOImpl implements GamesDAO {


    @Override
    public List<Game> getAllGames() throws SQLException {
        Session session = null;
        List<Game> games = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            games = session.createCriteria(Game.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Не удалось загрузить данные", "Ошибка загрузки данных'", JOptionPane.OK_OPTION);
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
            JOptionPane.showMessageDialog(null, "Не удалось добавить игру", "Ошибка при вставке", JOptionPane.OK_OPTION);
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
            JOptionPane.showMessageDialog(null, "Не удалось обновить игру", "Ошибка при обновлении", JOptionPane.OK_OPTION);
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
            JOptionPane.showMessageDialog(null, "Не удалось удалить игру", "Ошибка при удалении", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
