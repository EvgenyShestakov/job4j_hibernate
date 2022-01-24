package ru.job4j.integration;

import java.util.Collection;

public interface Store {
    Order save(Order order);

    boolean update(Order order);

    Collection<Order> findAll();

    Collection<Order> findByName(String name);

    Order findById(int id);
}
