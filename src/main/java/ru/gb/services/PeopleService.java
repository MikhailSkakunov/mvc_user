package ru.gb.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.models.Book;
import ru.gb.models.Person;
import ru.gb.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {

        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return  person.orElse(null);
    }

    @Transactional
    public void update(Person updatePerson, int id) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Person show(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    @Transactional
    public List<Book> getBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        List<Book> books = person.get().getBooks();
        if (person.isPresent()) {
            Hibernate.initialize(books);

            for (Book book : books) {
               long mls = Math.abs(book.getTakenIsBook().getTime() - new Date().getTime());
               if (mls > 864000000)
                   book.setLateBook(true);
            }

            return books;
        }
        else
        return Collections.emptyList();
    }
}
