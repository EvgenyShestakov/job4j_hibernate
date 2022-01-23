package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job_database")
public class JobDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private boolean active = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public JobDatabase() {
    }

    public JobDatabase(String username) {
        this.username = username;
    }

    public void addVacancy(Vacancy vacancy) {
    vacancies.add(vacancy);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobDatabase that = (JobDatabase) o;
        return getId() == that.getId() && isActive() == that.isActive() && Objects.
                equals(getUsername(), that.getUsername()) && Objects.equals(getVacancies(),
                that.getVacancies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), isActive(), getVacancies());
    }

    @Override
    public String toString() {
        return "JobDatabase{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", active=" + active
                + ", vacancies=" + vacancies
                + '}';
    }
}
