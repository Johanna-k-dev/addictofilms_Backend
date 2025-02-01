package com.visiotech.addictofilms.controllers;

import com.visiotech.addictofilms.daos.ViewsMovieDao;
import com.visiotech.addictofilms.models.ViewsMovie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/views")

public class ViewsMovieController {

    private final ViewsMovieDao viewsMovieDao;

    public ViewsMovieController(ViewsMovieDao viewsMovieDao) {
        this.viewsMovieDao = viewsMovieDao;
    }

    @GetMapping
    public ResponseEntity<List<ViewsMovie>> getAllViews() {
        List<ViewsMovie> views = viewsMovieDao.findAll();
        return ResponseEntity.ok(views);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewsMovie> getViewById(@PathVariable int id) {
        ViewsMovie view = viewsMovieDao.findById(id);
        return ResponseEntity.ok(view);
    }

    @PostMapping
    public ResponseEntity<ViewsMovie> addView(@RequestBody ViewsMovie viewsMovie) {
        ViewsMovie newView = viewsMovieDao.save(viewsMovie);
        return ResponseEntity.ok(newView);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViewsMovie> updateView(@PathVariable int id, @RequestBody ViewsMovie viewsMovie) {
        ViewsMovie updatedView = viewsMovieDao.update(id, viewsMovie);
        return ResponseEntity.ok(updatedView);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteView(@PathVariable int id) {
        boolean deleted = viewsMovieDao.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
