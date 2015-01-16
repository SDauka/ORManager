package kz.sdauka.ormanager.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Dauletkhan on 10.01.2015.
 */
@Entity
@Table(name = "SESSION_DETAILS", schema = "PUBLIC", catalog = "ORMANAGER")
public class SessionDetails {
    private int id;
    private Timestamp startTime;
    private Time operationTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "START_TIME", nullable = false, insertable = true, updatable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "OPERATION_TIME", nullable = false, insertable = true, updatable = true)
    public Time getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Time operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionDetails that = (SessionDetails) o;

        if (id != that.id) return false;
        if (operationTime != null ? !operationTime.equals(that.operationTime) : that.operationTime != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (operationTime != null ? operationTime.hashCode() : 0);
        return result;
    }
}
