package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        List<Brand> list = new ArrayList<>();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Brand bmw = Brand.of("BMW");
            Model model1 = Model.of("X3", bmw);
            Model model2 = Model.of("X5", bmw);
            Model model3 = Model.of("X7", bmw);
            bmw.addModel(model1);
            bmw.addModel(model2);
            bmw.addModel(model3);
            session.save(bmw);
           list =  session.createQuery("from Brand").list();
            for (Brand brand : list) {
                for (Model model : brand.getModels()) {
                    System.out.println(model);
                }
            }
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
