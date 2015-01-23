package kz.sdauka.ormanager.dao;


import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dauletkhan on 22.01.2015.
 */
public interface SessionDAO {
    public List getAllSession() throws SQLException;

    public List getSessionByDates(Date first, Date second) throws SQLException;

    public List getSessionByDate(Date date) throws SQLException;

    public List getSessionDetails(int sessionID) throws SQLException;
}
