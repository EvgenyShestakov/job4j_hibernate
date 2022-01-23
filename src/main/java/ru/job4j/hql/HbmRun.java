package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import javax.persistence.Query;

public class HbmRun {
    public static void main(String[] args) {
        Candidate rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            /*
            JobDatabase jobDatabase1 = new JobDatabase("user1");
            JobDatabase jobDatabase2 = new JobDatabase("user2");
            JobDatabase jobDatabase3 = new JobDatabase("user3");
            Vacancy vacancy1 = new Vacancy("Driver", "Employer1");
            Vacancy vacancy2 = new Vacancy("Street cleaner", "Employer2");
            Vacancy vacancy3 = new Vacancy("Builder", "Employer3");
            Vacancy vacancy4 = new Vacancy("Neurologist", "Employer4");
            Vacancy vacancy5 = new Vacancy("Cardiologist", "Employer5");
            Vacancy vacancy6 = new Vacancy("Surgeon", "Employer6");
            Vacancy vacancy7 = new Vacancy("Programmer", "Employer7");
            Vacancy vacancy8 = new Vacancy("Tester", "Employer8");
            Vacancy vacancy9 = new Vacancy("Manager", "Employer9");
            jobDatabase1.addVacancy(vacancy1);
            jobDatabase1.addVacancy(vacancy2);
            jobDatabase1.addVacancy(vacancy3);
            jobDatabase2.addVacancy(vacancy4);
            jobDatabase2.addVacancy(vacancy5);
            jobDatabase2.addVacancy(vacancy6);
            jobDatabase3.addVacancy(vacancy7);
            jobDatabase3.addVacancy(vacancy8);
            jobDatabase3.addVacancy(vacancy9);
            Candidate one = new Candidate("John", 1.5, 750, jobDatabase1);
            Candidate two = new Candidate("Alex", 2.5, 900, jobDatabase2);
            Candidate three = new Candidate("Mike", 4, 1200, jobDatabase3);
            session.persist(one);
            session.persist(two);
            session.persist(three);
            */
            Query findAll = session.createQuery("from Candidate ");
            for (Object st : findAll.getResultList()) {
                System.out.println(st);
            }
            Candidate candidate = (Candidate) session.
                    createQuery("from Candidate c where c.id = :paramId").
                    setParameter("paramId", 1).uniqueResult();
            System.out.println(candidate);
            Query findByName = session.
                    createQuery("from Candidate s where s.name = :paramName").
                    setParameter("paramName", "Alex");
            for (Object el : findByName.getResultList()) {
                System.out.println(el);
            }
            Query set = session.createQuery("update Candidate c set c."
                    + "name = :paramName, c.experience = :paramExperience,"
                    + " c.salary = :paramSalary where c.id = :paramId"
            );
            set.setParameter("paramName", "Tony");
            set.setParameter("paramExperience", 5.0);
            set.setParameter("paramSalary", 1500);
            set.setParameter("paramId", 3);
            set.executeUpdate();
            session.createQuery("delete from Candidate where id = :paramId")
                    .setParameter("paramId", 3)
                    .executeUpdate();
            rsl = session.createQuery(
                    "select distinct c from Candidate c "
                            + "join fetch c.jobDatabase d "
                            + "join fetch d.vacancies v "
                            + "where c.id = :sId", Candidate.class
            ).setParameter("sId", 1).uniqueResult();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }
}
