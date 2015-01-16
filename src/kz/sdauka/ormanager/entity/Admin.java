package kz.sdauka.ormanager.entity;

import javax.persistence.*;

/**
 * Created by Dauletkhan on 10.01.2015.
 */

@Entity
public class Admin {
    private int id;
    private String login;
    private String password;

    public Admin() {
    }

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "LOGIN", unique = true, nullable = false, length = 15)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "PASSWORD", unique = true, nullable = false, length = 15)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
