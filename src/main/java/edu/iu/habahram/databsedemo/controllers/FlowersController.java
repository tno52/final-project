package edu.iu.habahram.databsedemo.controllers;

import edu.iu.habahram.databsedemo.repository.FlowersFileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/flowers")
public class FlowersController {
    FlowersFileRepository flowersFileRepository;

    public FlowersController(FlowersFileRepository flowersFileRepository) {
        this.flowersFileRepository = flowersFileRepository;
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
}
