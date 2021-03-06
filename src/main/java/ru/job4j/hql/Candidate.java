package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double experience;

    private int salary;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_database_id")
    private JobDatabase jobDatabase;

    public Candidate() {
    }

    public Candidate(String name, double experience, int salary, JobDatabase jobDatabase) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
        this.jobDatabase = jobDatabase;
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

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public JobDatabase getJobDatabase() {
        return jobDatabase;
    }

    public void setJobDatabase(JobDatabase jobDatabase) {
        this.jobDatabase = jobDatabase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return getId() == candidate.getId() && Double.compare(candidate.getExperience(),
                getExperience()) == 0 && getSalary() == candidate.getSalary() && Objects.
                equals(getName(), candidate.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getExperience(), getSalary());
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", experience=" + experience
                + ", salary=" + salary
                + ", jobDatabase=" + jobDatabase
                + '}';
    }
}
