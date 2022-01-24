package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() {
        try (InputStream in = OrdersStoreTest.class.
                getClassLoader().getResourceAsStream("db.properties")) {
            Properties config = new Properties();
            config.load(in);
            pool.setDriverClassName(config.getProperty("driver"));
            pool.setUrl(config.getProperty("url"));
            pool.setUsername(config.getProperty("username"));
            pool.setPassword(config.getProperty("password"));
            pool.setMaxTotal(Integer.parseInt(config.getProperty("max_total")));
            StringBuilder builder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(config.getProperty("script"))))
            ) {
                br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
        }  catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @After
    public void wipeTable() {
        try {
            pool.getConnection().prepareStatement("drop table orders").execute();
        } catch  (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSaveOrder() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getId(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
    }

    @Test
    public void whenUpdateOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = Order.of("John", "pizza");
        store.save(order1);
        Order order2 = new Order(1, "Mike", "sushi", new Timestamp(System.currentTimeMillis()));
        Timestamp timestamp = order2.getCreated();
        boolean result = store.update(order2);
        List<Order> all = (List<Order>) store.findAll();
        assertTrue(result);
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getId(), is(1));
        assertThat(all.get(0).getName(), is("Mike"));
        assertThat(all.get(0).getDescription(), is("sushi"));
        assertEquals(all.get(0).getCreated(), timestamp);
    }

    @Test
    public void whenFindOrderByName() {
    OrdersStore store = new OrdersStore(pool);
        Order order1 = Order.of("Alex", "burger");
        Order order2 = Order.of("Alex", "pizza");
        store.save(order1);
        store.save(order2);
        List<Order> all = (List<Order>) store.findByName("Alex");
        assertThat(all.size(), is(2));
        assertThat(all.get(0).getId(), is(1));
        assertThat(all.get(0).getName(), is("Alex"));
        assertThat(all.get(0).getDescription(), is("burger"));
        assertThat(all.get(1).getId(), is(2));
        assertThat(all.get(1).getName(), is("Alex"));
        assertThat(all.get(1).getDescription(), is("pizza"));
    }

    @Test
    public void whenFindOrderById() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = Order.of("Alex", "burger");
        Order order2 = Order.of("John", "pizza");
        store.save(order1);
        store.save(order2);
        Order order = store.findById(2);
        assertThat(order.getId(), is(2));
        assertThat(order.getName(), is("John"));
        assertThat(order.getDescription(), is("pizza"));
    }

    @Test
    public void whenFindAllOrders() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = Order.of("Alex", "burger");
        Order order2 = Order.of("John", "pizza");
        Order order3 = Order.of("Mike", "sushi");
        store.save(order1);
        store.save(order2);
        store.save(order3);
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(3));
        assertThat(all.get(0).getId(), is(1));
        assertThat(all.get(0).getName(), is("Alex"));
        assertThat(all.get(0).getDescription(), is("burger"));
        assertThat(all.get(1).getId(), is(2));
        assertThat(all.get(1).getName(), is("John"));
        assertThat(all.get(1).getDescription(), is("pizza"));
        assertThat(all.get(2).getId(), is(3));
        assertThat(all.get(2).getName(), is("Mike"));
        assertThat(all.get(2).getDescription(), is("sushi"));
    }
}
