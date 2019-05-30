package com.asmirnov.quilzistServer.model;

import javax.persistence.*;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="module_id")
    private Module module;

    private String term;
    private String value;

    public Card() {
    }

    public Card(Module module, String term, String value) {
        this.module = module;
        this.term = term;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Card cardObj = (Card) obj;

        return this.id == cardObj.getId()
                && module.getId() == cardObj.getModule().getId()

                && (term == cardObj.getTerm()
                || (term != null && term.equals(cardObj.getTerm())))

                && (value == cardObj.value
                || (value != null && value.equals(cardObj.getValue())));
    }
}
