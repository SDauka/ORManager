package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.dao.AdminDAO;
import kz.sdauka.ormanager.entity.Admin;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.SQLException;

/**
 * Created by Dauletkhan on 10.01.2015.
 */
public class AdminDAOImpl implements AdminDAO {
    private static final Logger LOG = Logger.getLogger(AdminDAOImpl.class);
    @Override
    public void updatePassword(Admin admin) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(admin);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось обновить пароль", e);
            Dialogs.create().title("Ошибка обновления данных").message("Не удалось обновить пароль").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Admin getAdmin() throws SQLException {
        Session session = null;
        Admin admin = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Admin admin order by admin.password DESC");
            query.setMaxResults(1);
            admin = (Admin) query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные админки", e);
            Dialogs.create().title("Ошибка загрузки данных").message("Не удалось загрузить данные админки").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return admin;
    }

    @Override
    public void insertAdmin(Admin admin) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(admin);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные админки", e);
            Dialogs.create().title("Ошибка обновления данных").message("Не удалось загрузить данные админки").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean checkAdmin() throws SQLException {
        Session session = null;
        Admin admin = null;
        boolean check = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Admin admin order by admin.password DESC");
            query.setMaxResults(1);
            check = query.uniqueResult() != null;
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Не удалось загрузить данные админки", e);
            Dialogs.create().title("Ошибка загрузки данных").message("Не удалось загрузить данные админки").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return check;
    }
}
