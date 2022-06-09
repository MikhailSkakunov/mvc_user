package ru.gb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.dao.PersonDAO;

@Controller
@RequestMapping("batch-update")
public class BatchController {
    private final PersonDAO personDAO;

    @Autowired
    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        personDAO.multipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String withBatch() {
        personDAO.batchUpdate();
        return "redirect:/people";
    }


}
