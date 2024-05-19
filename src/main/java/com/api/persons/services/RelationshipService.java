package com.api.persons.services;

import com.api.persons.enums.RelationshipType;
import com.api.persons.models.Person;
import com.api.persons.models.Relationship;
import com.api.persons.repository.IRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelationshipService {

    @Autowired
    private IRelationshipRepository relationshipRepository;

    public void createRelationship(Person person1, Person person2, RelationshipType relationshipType) {
        if (isValidRelationshipType(relationshipType)) {
            Relationship relationship = new Relationship();
            relationship.setPerson1(person1);
            relationship.setPerson2(person2);
            relationship.setRelationshipType(relationshipType);
        relationshipRepository.save(relationship);
        } else {
            throw new IllegalArgumentException("El tipo de relación no es válido");
        }
    }

    public RelationshipType getRelationshipType(Person person1, Person person2) {
        Relationship relationship = relationshipRepository.findByPerson1AndPerson2(person1, person2);
        if (relationship != null) {
            return relationship.getRelationshipType();
        }
        return null;
    }

    public void deleteRelationship(Long relationshipId) {
        Optional<Relationship> relationshipOptional = relationshipRepository.findById(relationshipId);

        if (relationshipOptional.isPresent()) {
            relationshipRepository.delete(relationshipOptional.get());
        } else {
            throw new IllegalArgumentException("La relación especificada no existe");
        }
    }

    private boolean isValidRelationshipType(RelationshipType relationshipType) {
        return  relationshipType == RelationshipType.HERMANO ||
                relationshipType == RelationshipType.PRIMO ||
                relationshipType == RelationshipType.PADRE;
    }
}
