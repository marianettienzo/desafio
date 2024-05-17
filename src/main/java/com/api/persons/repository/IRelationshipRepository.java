package com.api.persons.repository;

import com.api.persons.models.Person;
import com.api.persons.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRelationshipRepository extends JpaRepository<Relationship, Long> {
    Relationship findByPerson1AndPerson2(Person person1, Person person2);
}
