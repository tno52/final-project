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
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> addFlower(@RequestParam("name") String name,
                                       @RequestParam("cost") float cost,
                                       @RequestParam("color") String color,
                                       @RequestParam("occasion") String occasion,
                                       @RequestParam("description") String description,
                                       @RequestParam("imageNumber") int imageNumber) {
        try {
            String imagePath = "flowers/images/" + imageNumber + ".jpeg";

            // Create and save the new Flower object
            Flower flower = new Flower();
            flower.setName(name);
            flower.setCost(cost);
            flower.setColor(color);
            flower.setOccasion(occasion);
            flower.setDescription(description);
            flower.setImagePath(imagePath);
            flowersRepository.save(flower);

            return ResponseEntity.ok().body("Flower added successfully with ID: " + flower.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding flower: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flower> getFlowerById(@PathVariable int id) {
        Optional<Flower> flower = flowersRepository.findById(id);
        if (flower.isPresent()) {
            return ResponseEntity.ok(flower.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Flower>> findAll(@RequestParam(required = false) String color,
                                                @RequestParam(required = false) String occasion,
                                                @RequestParam(required = false) String description) {
        List<Flower> flowers;
        if (color != null && occasion != null && description != null) {
            flowers = flowersRepository.findByColorAndOccasionAndDescriptionIgnoreCase(color, occasion, description);
        } else if (color != null && occasion != null) {
            flowers = flowersRepository.findByColorAndOccasionIgnoreCase(color, occasion);
        } else if (color != null && description != null) {
            flowers = flowersRepository.findByColorAndDescriptionIgnoreCase(color, description);
        } else if (occasion != null && description != null) {
            flowers = flowersRepository.findByOccasionAndDescriptionIgnoreCase(occasion, description);
        } else if (color != null) {
            flowers = flowersRepository.findByColorIgnoreCase(color);
        } else if (occasion != null) {
            flowers = flowersRepository.findByOccasionIgnoreCase(occasion);
        } else if (description != null) {
            flowers = flowersRepository.findByDescriptionIgnoreCase(description);
        } else {
            flowers = (List<Flower>) flowersRepository.findAll();
        }
        return ResponseEntity.ok(flowers);
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
