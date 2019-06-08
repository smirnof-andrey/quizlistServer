package com.asmirnov.quilzistServer.model;

import javax.persistence.*;

public class ModuleAdditionalInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="module_id")
    private Module module;

    private Integer itemsCount;
}
