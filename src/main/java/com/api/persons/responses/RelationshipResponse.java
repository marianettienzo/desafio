package com.api.persons.responses;

import com.api.persons.enums.RelationshipType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationshipResponse {
    private String relationshipMessage;
    public RelationshipResponse(String relationshipMessage) {
        this.relationshipMessage = relationshipMessage;
    }

}
