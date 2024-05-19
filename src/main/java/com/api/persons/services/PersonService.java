package com.api.persons.services;

import com.api.persons.enums.DocumentType;
import com.api.persons.models.Person;
import com.api.persons.repository.IPersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private IPersonRepository personRepository;

    public List<Person> getAllPersons() {
        //TODO: make pagination
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        try {
            return personRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("La persona con el ID " + id + " no fue encontrada."));
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Error al buscar la persona con el ID " + id, ex);
        }
    }

    public Person createPerson(Person person) {
        validatePersonData(person);
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setDocumentType(updatedPerson.getDocumentType());
                    person.setName(updatedPerson.getName());
                    person.setDocumentNumber(updatedPerson.getDocumentNumber());
                    person.setCountry(updatedPerson.getCountry());
                    person.setDateOfBirth(updatedPerson.getDateOfBirth());
                    person.setEmail(updatedPerson.getEmail());
                    person.setPhoneNumber(updatedPerson.getPhoneNumber());
                    return personRepository.save(person);
                })
                .orElse(null);
    }

    public boolean deletePerson(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validatePersonData(Person person) {
        if (person.getCountry() == null || person.getCountry().trim().isEmpty()) {
            throw new IllegalArgumentException("El país es obligatorio.");
        }

        boolean exists = personRepository.existsByDocumentTypeAndDocumentNumberAndCountry(person.getDocumentType(), person.getDocumentNumber(), person.getCountry());

        if (exists) {
            throw new IllegalArgumentException("Ya existe una persona con el mismo tipo de documento, número de documento y país.");
        }

        if (person.getEmail() == null || person.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El mail es obligatorio.");
        }

        if (person.getDocumentType() == null ||
                (person.getDocumentType() != DocumentType.DNI &&
                        person.getDocumentType() != DocumentType.LC &&
                        person.getDocumentType() != DocumentType.DNE &&
                        person.getDocumentType() != DocumentType.LE)) {
            throw new IllegalArgumentException("El tipo de documento es inválido.");
        }

        if (person.getDocumentNumber() == null || person.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio.");
        }

        if (person.getDateOfBirth() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }

        LocalDate today = LocalDate.now();
        LocalDate eighteenYearsAgo = today.minusYears(18);
        if (person.getDateOfBirth().isAfter(eighteenYearsAgo)) {
            throw new IllegalArgumentException("No se pueden crear personas menores de 18 años.");
        }
    }



}
