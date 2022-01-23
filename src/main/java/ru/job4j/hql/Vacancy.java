package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String employer;

    public Vacancy() {
    }

    public Vacancy(String name, String employer) {
        this.name = name;
        this.employer = employer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy that = (Vacancy) o;
        return getId() == that.getId() && Objects.equals(getName(),
                that.getName()) && Objects.equals(getEmployer(),
                that.getEmployer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmployer());
    }

    @Override
    public String toString() {
        return "JobVacancy{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", employer='" + employer + '\''
                + '}';
    }
}
