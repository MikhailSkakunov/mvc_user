package ru.gb.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(max = 100, message = "Название книги не должно превышать 100 символов")
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Size(max = 100, message = "Полное имя автора не должно быть более 100 символов")
    @Column(name = "author")
    private String author;

    @Column(name = "year")
    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    Person owner;

    @Column(name = "taken_is_book")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenIsBook;

    @Transient
    private boolean lateBook;

    public Book() {
    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenIsBook() {
        return takenIsBook;
    }

    public void setTakenIsBook(Date takenIsBook) {
        this.takenIsBook = takenIsBook;
    }

    public void setLateBook(boolean lateBook) {
        this.lateBook = lateBook;
    }

    public boolean isLateBook() {
        return lateBook;
    }
}
