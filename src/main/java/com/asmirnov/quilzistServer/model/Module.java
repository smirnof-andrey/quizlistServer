package com.asmirnov.quilzistServer.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    @JsonView(Views.Id.class)
    private Integer id;

    @JsonView(Views.IdName.class)
    private String name;

    @JsonView(Views.Info.class)
    private String info;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonView(Views.FullData.class)
    private User author;

    public Module() {
    }

    public Module(String name, String info, User user) {
        this.name = name;
        this.info = info;
        this.author = user;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<no author>";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
