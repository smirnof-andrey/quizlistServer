package com.asmirnov.quilzistServer.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class ModuleAdditionalInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="module")
    private Module module;

    private Integer itemsCount;

}
