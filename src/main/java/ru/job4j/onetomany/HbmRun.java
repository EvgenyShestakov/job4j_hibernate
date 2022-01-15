package ru.job4j.onetomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.onetomany.AutoBrand;
import ru.job4j.onetomany.AutoModel;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            AutoModel one = AutoModel.of("Edge");
            AutoModel two = AutoModel.of("Escape");
            AutoModel three = AutoModel.of("Mondeo");
            AutoModel four = AutoModel.of("Mustang");
            AutoModel fife = AutoModel.of("Taurus");
            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(fife);
            AutoBrand brand = AutoBrand.of("Ford");
            brand.addAutoModel(session.load(AutoModel.class, 1));
            brand.addAutoModel(session.load(AutoModel.class, 2));
            brand.addAutoModel(session.load(AutoModel.class, 3));
            brand.addAutoModel(session.load(AutoModel.class, 4));
            brand.addAutoModel(session.load(AutoModel.class, 5));
            session.save(brand);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
