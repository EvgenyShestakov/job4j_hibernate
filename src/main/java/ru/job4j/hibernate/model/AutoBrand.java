package ru.job4j.hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brand")
public class AutoBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AutoModel> autoModelList = new ArrayList<>();

    public static AutoBrand of(String name) {
        AutoBrand brand = new AutoBrand();
        brand.name = name;
        return brand;
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

    public void addAutoModel(AutoModel autoModel) {
        this.autoModelList.add(autoModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutoBrand autoBrand = (AutoBrand) o;
        return id == autoBrand.id && Objects.equals(name, autoBrand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AutoBrand{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
