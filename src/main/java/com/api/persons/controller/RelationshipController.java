package com.api.persons.controller;

import com.api.persons.enums.RelationshipType;
import com.api.persons.models.Person;
import com.api.persons.services.PersonService;
import com.api.persons.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private PersonService personService;

    @PostMapping("/personas/{id1}/padre/{id2}")
    public void createParentRelationship(@PathVariable Long id1, @PathVariable Long id2) {
        Person person1 = personService.getPersonById(id1);
        Person person2 = personService.getPersonById(id2);
        relationshipService.createRelationship(person1, person2, RelationshipType.PADRE);
    }

    @GetMapping("/relaciones/{id1}/{id2}")
    public RelationshipType getRelationship(@PathVariable Long id1, @PathVariable Long id2) {
        Person person1 = personService.getPersonById(id1);
        Person person2 = personService.getPersonById(id2);
        return relationshipService.getRelationshipType(person1, person2);
    }
}
