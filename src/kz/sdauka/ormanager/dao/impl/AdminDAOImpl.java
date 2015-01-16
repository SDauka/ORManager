package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.Main;
import kz.sdauka.ormanager.dao.AdminDAO;
import kz.sdauka.ormanager.entity.Admin;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by Dauletkhan on 10.01.2015.
 */
public class AdminDAOImpl implements AdminDAO {
    private Main main;

    @Override
    public void updatePassword(Admin admin) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(admin);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Не удалось обновить пароль", "Ошибка обновления данных", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Admin getAdmin(int id) throws SQLException {
        Session session = null;
        Admin admin = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            admin = (Admin) session.get(Admin.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Не удалось загрузить данные админки", "Ошибка загрузки данных ", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return admin;
    }
}
