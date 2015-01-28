package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.dao.AdminDAO;
import kz.sdauka.ormanager.entity.Admin;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.swing.*;
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
            JOptionPane.showMessageDialog(null, "Не удалось обновить пароль", "Ошибка обновления данных", JOptionPane.OK_OPTION);
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
            JOptionPane.showMessageDialog(null, "Не удалось загрузить данные админки", "Ошибка загрузки данных ", JOptionPane.OK_OPTION);
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
            JOptionPane.showMessageDialog(null, "Не удалось загрузить данные админки", "Ошибка обновления данных", JOptionPane.OK_OPTION);
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
            JOptionPane.showMessageDialog(null, "Не удалось загрузить данные админки", "Ошибка загрузки данных ", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return check;
    }
}
