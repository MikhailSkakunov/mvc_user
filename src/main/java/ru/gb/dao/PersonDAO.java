package ru.gb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gb.models.Book;
import ru.gb.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE fullName=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES(id, ?, ?)", person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET fullName=?, yearOfBirth=?, WHERE id=?", updatedPerson.getFullName(),
                updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }


    /*Тестим Batch!!!!!*/

//    public void multipleUpdate() {
//        List<Person> people = createListPeople();
//
//        long before = System.currentTimeMillis();
//
//        for (Person person : people)
//        jdbcTemplate.update("INSERT INTO person VALUES (id, ?, ?, ?, ?)", person.getName(),
//                person.getAge());
//
//        long after = System.currentTimeMillis();
//
//        System.out.println("Time: " + (after - before));
//    }
//
//    public void batchUpdate() {
//        List<Person> people = createListPeople();
//
//        long before = System.currentTimeMillis();
//
//        jdbcTemplate.batchUpdate("INSERT INTO person VALUES (id, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//
//                ps.setString(1, people.get(i).getName());
//                ps.setInt(2, people.get(i).getAge());
//                ps.setString(3, people.get(i).getEmail());
//                ps.setString(4, people.get(i).getAddress());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return people.size();
//            }
//        });
//
//        long after = System.currentTimeMillis();
//
//        System.out.println("Time " + (after - before));
//    }
//
//    public List<Person> createListPeople() {
//        ArrayList<Person> people = new ArrayList<>();
//        for (int i = 0; i < 1000; i++)
//            people.add(new Person(i, "fullName" + i, 1935));
//        return people;
//    }
}