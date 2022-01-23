package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import javax.persistence.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            /*
            Candidate one = new Candidate("John", 1.5, 750);
            Candidate two = new Candidate("Alex", 2.5, 900);
            Candidate three = new Candidate("Mike", 4, 1200);
            session.save(one);
            session.save(two);
            session.save(three);
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
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
