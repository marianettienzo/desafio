package com.api.persons.repository;

import com.api.persons.enums.DocumentType;
import com.api.persons.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    boolean existsByDocumentTypeAndDocumentNumberAndCountry(DocumentType documentType, String documentNumber, String country);
}
