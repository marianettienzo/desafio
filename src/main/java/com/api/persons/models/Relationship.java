package com.api.persons.models;

import com.api.persons.enums.RelationshipType;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;


@Getter
@Setter
@Entity
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person1_id", referencedColumnName = "id")
    private Person person1;

    @ManyToOne
    @JoinColumn(name = "person2_id", referencedColumnName = "id")
    private Person person2;

    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;
}
