package kz.sdauka.ormanager.dao;

import kz.sdauka.ormanager.entity.Admin;

import java.sql.SQLException;

/**
 * Created by Dauletkhan on 10.01.2015.
 */
public interface AdminDAO {
    public Admin getAdmin() throws SQLException;

    public void insertAdmin(Admin admin) throws SQLException;

    public boolean checkAdmin() throws SQLException;
    public void updatePassword(Admin admin) throws SQLException;
}
