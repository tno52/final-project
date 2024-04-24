package edu.iu.habahram.databsedemo.controllers;

import edu.iu.habahram.databsedemo.model.Flower;
import edu.iu.habahram.databsedemo.repository.FlowersFileRepository;
import edu.iu.habahram.databsedemo.repository.FlowersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/flowers")
public class FlowersController {
    FlowersFileRepository flowersFileRepository;
    FlowersRepository flowersRepository;

    public FlowersController(
            FlowersFileRepository flowersFileRepository,
            FlowersRepository flowersRepository) {
        this.flowersFileRepository = flowersFileRepository;
        this.flowersRepository = flowersRepository;
    }

    @PostMapping
    public int add(@RequestBody Flower flower) {
        Flower saved = flowersRepository.save(flower);
        return saved.getId();
    }

    @GetMapping
    public Iterable<Flower> findAll() {
        Iterable<Flower> flowers = flowersRepository.findAll();
        return flowers;
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        try {
            byte[] image = flowersFileRepository.getImage(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/image")
    public boolean updateImage(@PathVariable int id,
                               @RequestParam MultipartFile file) {
        try {
            return flowersFileRepository.updateImage(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
