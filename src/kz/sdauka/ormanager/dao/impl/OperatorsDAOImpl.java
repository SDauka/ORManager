package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.dao.OperatorsDAO;
import kz.sdauka.ormanager.entity.Operator;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dauletkhan on 12.01.2015.
 */
public class OperatorsDAOImpl implements OperatorsDAO {
    private static final Logger LOG = Logger.getLogger(OperatorsDAOImpl.class);
    @Override
    public List<Operator> getAllOperators() throws SQLException {
        Session session = null;
        List<Operator> operators = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            operators = session.createCriteria(Operator.class).list();
        } catch (Exception e) {
            LOG.error(e);
            JOptionPane.showMessageDialog(null, "Ошибка загрузки данных операторов", "Ошибка загрузки данных", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return operators;
    }

    @Override
    public void setOperator(Operator operator) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(operator);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error(e);
            JOptionPane.showMessageDialog(null, "Ошибка добавления оператора", "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    @Override
    public void updateOperator(Operator operator) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(operator);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error(e);
            JOptionPane.showMessageDialog(null, "Ошибка обновления данных оператора", "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteOperator(Operator operator) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(operator);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error(e);
            JOptionPane.showMessageDialog(null, "Ошбика удаления оператора", "Ошибка при удалении", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
