package kz.sdauka.ormanager.dao.impl;

import kz.sdauka.ormanager.dao.OperatorsDAO;
import kz.sdauka.ormanager.entity.Operator;
import kz.sdauka.ormanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.Session;

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
            Dialogs.create().title("Ошибка загрузки данных").message("Ошибка загрузки данных операторов").showError();
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
            Dialogs.create().title("Ошибка при вставке").message("Ошибка добавления оператора").showError();
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
            Dialogs.create().title("Ошибка при вставке").message("Ошибка обновления данных оператора").showError();
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
            Dialogs.create().title("Ошибка при удалении").message("Ошбика удаления оператора").showError();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
