package com.api.persons.services;

import com.api.persons.models.Person;
import com.api.persons.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private IPersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person createPerson(Person person) {
        validatePersonData(person);
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setDocumentType(updatedPerson.getDocumentType());
                    person.setDocumentNumber(updatedPerson.getDocumentNumber());
                    person.setCountry(updatedPerson.getCountry());
                    person.setDateOfBirth(updatedPerson.getDateOfBirth());
                    person.setEmail(updatedPerson.getEmail());
                    person.setPhoneNumber(updatedPerson.getPhoneNumber());
                    return personRepository.save(person);
                })
                .orElse(null); // or throw an exception
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
            throw new RuntimeException("El país es obligatorio.");
        }

        boolean exists = personRepository.existsByDocumentTypeAndDocumentNumberAndCountry(person.getDocumentType(), person.getDocumentNumber(), person.getCountry());

        if (exists) {
            throw new RuntimeException("Ya existe una persona con el mismo tipo de documento, número de documento y país.");
        }

        if (person.getEmail() == null || person.getEmail().trim().isEmpty()) {
            throw new RuntimeException("El mail es obligatorio.");
        }

        if (person.getDocumentNumber() == null || person.getDocumentNumber().trim().isEmpty()) {
            throw new RuntimeException("El documento es obligatorio.");
        }

        if (person.getDateOfBirth() == null) {
            throw new RuntimeException("La fecha de nacimiento es obligatoria.");
        }

        LocalDate today = LocalDate.now();
        LocalDate eighteenYearsAgo = today.minusYears(18);
        if (person.getDateOfBirth().isAfter(eighteenYearsAgo)) {
            throw new RuntimeException("No se pueden crear personas menores de 18 años.");
        }
    }

}
