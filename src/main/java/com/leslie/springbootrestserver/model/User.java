package com.leslie.springbootrestserver.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="USER_ID", updatable = false, nullable = false)
    private Long userID;

    @Column(name ="LOGIN", unique = true, insertable = true, updatable = true, nullable = false)
    private String login;

    @Column(name ="USER_PASSWORD", insertable = true, updatable = true, nullable = false)
    private String password;

    @Column(name ="USER_ACTIVE", insertable = true, updatable = true, nullable = false)
    private Integer active;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name="USER_ID"), inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
    private Set<Role> roles = new HashSet<Role>();


    /* Constructors */
    public User(){
        super();
    }

    public User(String login, String password, Integer active) {
        this.login = login;
        this.password = password;
        this.active = active;
    }

    public User(Long userID, String login, String password, Integer active) {
        this.userID = userID;
        this.login = login;
        this.password = password;
        this.active = active;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /* Getters +  Setters */

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /* Override methods */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(active, user.active) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, login, password, active, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
