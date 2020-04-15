package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "PHONE")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "PHONE_NUMBER")
    private String number;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "USER_ID")
    private User user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(long id, String number) {
        this.id = id;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
