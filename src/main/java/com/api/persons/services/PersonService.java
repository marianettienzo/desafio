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

    // En el servicio PersonService
// En el servicio PersonService
    public Person createPerson(Person person) {
        // Verificar si el país está presente y no es vacío ni nulo
        if (person.getCountry() == null || person.getCountry().trim().isEmpty()) {
            // Lanzar una excepción si el país está vacío o nulo
            throw new RuntimeException("El país es obligatorio.");
        }

        // Verificar si ya existe una persona con la misma combinación de tipo de documento, número de documento y país
        boolean exists = personRepository.existsByDocumentTypeAndDocumentNumberAndCountry(
                person.getDocumentType(), person.getDocumentNumber(), person.getCountry());

        // Verificar si al menos uno de los campos de contacto (email o phoneNumber) no está vacío
        if (person.getEmail() == null && person.getPhoneNumber() == null) {
            // Lanzar una excepción si no hay datos de contacto
            throw new RuntimeException("La persona debe tener al menos un dato de contacto (email o número de teléfono).");
        }

        // Verificar si la persona es menor de 18 años
        LocalDate today = LocalDate.now();
        LocalDate eighteenYearsAgo = today.minusYears(18);
        if (person.getDateOfBirth().isAfter(eighteenYearsAgo)) {
            // Lanzar una excepción si la persona es menor de 18 años
            throw new RuntimeException("No se pueden crear personas menores de 18 años.");
        }

        // Si pasa todas las validaciones, guardar la persona en la base de datos
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
}
