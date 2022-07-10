package ru.gb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gb.models.Book;
import ru.gb.models.Person;
import ru.gb.services.BooksService;
import ru.gb.services.PeopleService;
import ru.gb.util.BookValidator;

import javax.validation.Valid;

import static org.springframework.util.ObjectUtils.isEmpty;

@Controller
@RequestMapping("/books")
public class BooksController {

private final BooksService booksService;
private final PeopleService peopleService;
private final BookValidator bookValidator;


    @Autowired
    public BooksController(BooksService booksService,
                           PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "pageable", required = false) Integer pageable,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "year", required = false) boolean sortByYear) {

        if (pageable == null && booksPerPage == null)
        model.addAttribute("books", booksService.findAll(sortByYear));
        if (pageable != null || booksPerPage != null)
            model.addAttribute("books", booksService.findSorted(pageable, booksPerPage, sortByYear));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        model.addAttribute("book", booksService.findOne(id));

        Person owner = booksService.getBookOwner(id);
        if (!isEmpty(owner))
        model.addAttribute("owner", booksService.getBookOwner(id));
        else
        model.addAttribute("people", peopleService.findAll());
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

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(book, id);
        return "redirect:/books";
    }

    @PostMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.assign(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }


    @PostMapping("/search")
    public String searchBook(Model model, @RequestParam("query") String query) {

        model.addAttribute("books", booksService.show(query));
        return "books/search";
    }
}
