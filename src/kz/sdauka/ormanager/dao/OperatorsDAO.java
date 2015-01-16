package kz.sdauka.ormanager.dao;

import kz.sdauka.ormanager.entity.Operator;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dauletkhan on 12.01.2015.
 */
public interface OperatorsDAO {
    public List<Operator> getAllOperators() throws SQLException;

    public void setOperator(Operator operator) throws SQLException;

    public void updateOperator(Operator operator) throws SQLException;

    public void deleteOperator(Operator operator) throws SQLException;
}
