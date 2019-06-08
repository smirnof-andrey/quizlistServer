package com.asmirnov.quilzistServer.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude={"author"})
public class Module {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    @JsonView(Views.Id.class)
    private Integer id;

    @NonNull
    @JsonView(Views.IdName.class)
    private String name;

    @NonNull
    @JsonView(Views.Info.class)
    private String info;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonView(Views.FullData.class)
    private User author;
}
