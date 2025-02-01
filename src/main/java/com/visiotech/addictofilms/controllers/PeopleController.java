package com.visiotech.addictofilms.controllers;

import com.visiotech.addictofilms.daos.PeopleDao;
import com.visiotech.addictofilms.models.People;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDao peopleDao;


    public PeopleController(PeopleDao peopleDao) {
        this.peopleDao = peopleDao;
    }


    @GetMapping
    public List<People> getAllPeople() {
        return peopleDao.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<People> getPeopleById(@PathVariable int id) {
        try {
            People people = peopleDao.findById(id);
            return ResponseEntity.ok(people);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<People> addPeople(@RequestBody People people) {
        People savedPeople = peopleDao.save(people);
        return ResponseEntity.ok(savedPeople);
    }


    @PutMapping("/{id}")
    public ResponseEntity<People> updatePeople(@PathVariable int id, @RequestBody People people) {
        try {
            People updatedPeople = peopleDao.update(id, people);
            return ResponseEntity.ok(updatedPeople);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Supprimer une personne
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePeople(@PathVariable int id) {
        boolean deleted = peopleDao.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Personne supprimée avec succès");
        } else {
            return ResponseEntity.status(400).body("Échec de la suppression de la personne");
        }
    }
}
