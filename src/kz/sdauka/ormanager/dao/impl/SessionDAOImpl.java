package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.dao.SessionDAO;
import kz.sdauka.ormanager.entity.Session;
import kz.sdauka.ormanager.entity.SessionDetails;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dauletkhan on 23.01.2015.
 */
public class SessionDAOImpl implements SessionDAO {
    private static final Logger LOG = Logger.getLogger(SessionDAOImpl.class);

    @Override
    public List getSessionByDates(Date first, Date second) throws SQLException {
        org.hibernate.Session session = null;
        List sessions = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Session session where session.day between :first and :second").setDate("first", first).setDate("second", second);
            sessions = (List<Session>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные " + e);
            Dialogs.create().title("Ошибка загрузки данных").message("Не удалось загрузить данные").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sessions;
    }

    @Override
    public List getSessionByDate(Date date) throws SQLException {
        org.hibernate.Session session = null;
        List sessions = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Session session where session.day = :date").setDate("date", date);
            sessions = (List<Session>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные " + e);
            Dialogs.create().title("Ошибка загрузки данных").message("Не удалось загрузить данные").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sessions;
    }

    @Override
    public List getAllSession() throws SQLException {
        org.hibernate.Session session = null;
        List sessions = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sessions = session.createCriteria(Session.class).list();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные " + e);
            Dialogs.create().title("Ошибка загрузки данных").message("Не удалось загрузить данные").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sessions;
    }

    @Override
    public List getSessionDetails(int sessionID) throws SQLException {
        org.hibernate.Session session = null;
        List sessionDetails = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from SessionDetails sessionDetails where sessionDetails.sessionBySessionId = :session_id").setInteger("session_id", sessionID);
            sessionDetails = (List<SessionDetails>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные " + e);
            Dialogs.create().title("Ошибка загрузки данных").message("Не удалось загрузить данные").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sessionDetails;
    }
}
