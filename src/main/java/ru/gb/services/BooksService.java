package ru.gb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.models.Book;
import ru.gb.models.Person;
import ru.gb.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
        return booksRepository.findAll(Sort.by("year"));
        else
        return booksRepository.findAll();
    }

    public List<Book> findSorted(Integer page, Integer size, boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();}
        else
            return booksRepository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book updateBook, int id) {
        Book book = booksRepository.findById(id).get();

        updateBook.setId(id);
        updateBook.setOwner(book.getOwner());

        save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public Person getBookOwner(int id) {
         Book book = booksRepository.findById(id).orElse(null);
        assert book != null;
        return book.getOwner();
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setTakenIsBook(null);
            book.setOwner(null);});
    }

    @Transactional
    public void assign(int id, Person person) {
        Optional<Book> book= booksRepository.findById(id);
        if (book.isPresent() && book.get().getOwner() == null) {

            book.get().setTakenIsBook(new Date());

            book.get().setOwner(person);
        }
    }

    public List<Book> show(String title) {
        return booksRepository.findByTitleStartsWith(title);
    }
}
