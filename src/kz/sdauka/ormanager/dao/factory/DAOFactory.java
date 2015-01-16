package kz.sdauka.ormanager.dao.factory;

import kz.sdauka.ormanager.dao.AdminDAO;
import kz.sdauka.ormanager.dao.GamesDAO;
import kz.sdauka.ormanager.dao.OperatorsDAO;
import kz.sdauka.ormanager.dao.impl.AdminDAOImpl;
import kz.sdauka.ormanager.dao.impl.GamesDAOImpl;
import kz.sdauka.ormanager.dao.impl.OperatorsDAOImpl;

/**
 * Created by Dauletkhan on 10.01.2015.
 */
public class DAOFactory {

    private static AdminDAO adminDAO = null;
    private static OperatorsDAO operatorsDAO = null;
    private static GamesDAO gamesDAO = null;
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public GamesDAO getGamesDAO() {
        if (gamesDAO == null) {
            gamesDAO = new GamesDAOImpl();
        }
        return gamesDAO;
    }

    public OperatorsDAO getOperatorsDAO() {
        if (operatorsDAO == null) {
            operatorsDAO = new OperatorsDAOImpl();
        }
        return operatorsDAO;
    }


    public AdminDAO getAdminDAO() {
        if (adminDAO == null) {
            adminDAO = new AdminDAOImpl();
        }
        return adminDAO;
    }

}
