package ru.job4j.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        Session session = null;
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            session = sf.openSession();
            session.beginTransaction();
            Author author1 = Author.of("Автор1");
            Author author2 = Author.of("Автор2");
            Author author3 = Author.of("Автор3");
            Author author4 = Author.of("Автор4");
            Author author5 = Author.of("Автор5");
            Author author6 = Author.of("Автор6");
            Book book1 = Book.of("Книга1");
            Book book2 = Book.of("Книга2");
            Book book3 = Book.of("Книга3");
            Book book4 = Book.of("Книга4");
            Book book5 = Book.of("Книга5");
            Book book6 = Book.of("Книга6");

            author1.addBooks(book1);
            author1.addBooks(book2);
            author1.addBooks(book3);
            author2.addBooks(book4);
            author3.addBooks(book5);
            author4.addBooks(book6);
            author5.addBooks(book1);
            author5.addBooks(book2);
            author5.addBooks(book3);
            author6.addBooks(book1);
            author6.addBooks(book2);
            author6.addBooks(book3);
            session.persist(author1);
            session.persist(author2);
            session.persist(author3);
            session.persist(author4);
            session.persist(author5);
            session.persist(author6);
            Author authorOne = session.get(Author.class, 1);
            Author authorSix = session.get(Author.class, 6);
            session.remove(authorOne);
            session.remove(authorSix);

            session.getTransaction().commit();

        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
