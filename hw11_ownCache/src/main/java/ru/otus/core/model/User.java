package ru.otus.core.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "USER_NAME")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = PhoneDataSet.class,
            mappedBy = "user", orphanRemoval = true)
    private List<PhoneDataSet> phones;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_ADDRESS")
    private AddressDataSet address;

    public User() {
    }

    public User(long id, String name, List<PhoneDataSet> phones, AddressDataSet address) {
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.address = address;
        if (this.phones != null) {
            this.phones.stream().forEach(phone -> phone.setUser(this));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phones=" + phones +
                ", address=" + address +
                '}';
    }
}
