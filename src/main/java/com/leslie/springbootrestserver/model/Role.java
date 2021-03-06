package com.leslie.springbootrestserver.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name ="ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 2284252532274015507L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", updatable = false, nullable = false)
    private int id;

    @Column(name ="ROLE_NAME", updatable = true, nullable = false)
    private String roleName;

    /* Constructor */
    public Role(){
        super();
    }

    public Role(String roleName){
        super();
        this.roleName = roleName;
    }

    /* Getters + Setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }

    public int compareTo(Role role){
        return this.roleName.compareTo(role.getRoleName());

    }
}
