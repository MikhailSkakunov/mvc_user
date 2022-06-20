package ru.gb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gb.dao.BookDao;
import ru.gb.dao.PersonDAO;
import ru.gb.models.Book;
import ru.gb.models.Person;
import ru.gb.util.BookValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

private final BookDao bookDao;
private final PersonDAO personDAO;
private final BookValidator bookValidator;


    @Autowired
    public BooksController(BookDao bookDao, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDao = bookDao;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        model.addAttribute("book", bookDao.show(id));

        Optional<Person> optional = bookDao.getBookOwner(id);
        if (optional.isPresent())
        model.addAttribute("owner", optional.get());
        else
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book) {
        return ("books/new");
    }

    @PostMapping
    public String crateBook(@ModelAttribute("book") @Valid Book book,
                            BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/new";

        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";
        bookDao.update(book, id);
        return "redirect:/books";
    }

    @PostMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDao.assign(id, person);
        return "redirect:/books/" + id;
    }

}
