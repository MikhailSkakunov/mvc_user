package ru.gb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gb.models.Person;
import ru.gb.services.PeopleService;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (!isEmpty(peopleService.show(person.getFullName())))
            errors.rejectValue("fullName", "", "This fullName is already exist");
    }
}
