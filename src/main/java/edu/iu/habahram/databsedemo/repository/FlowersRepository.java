package edu.iu.habahram.databsedemo.repository;

import edu.iu.habahram.databsedemo.model.Flower;
import edu.iu.habahram.databsedemo.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowersRepository extends CrudRepository<Flower, Integer> {
    List<Flower> findByColorIgnoreCase(String color);
    List<Flower> findByOccasionIgnoreCase(String occasion);
    List<Flower> findByDescriptionIgnoreCase(String description);

    List<Flower> findByColorAndOccasionIgnoreCase(String color, String occasion);
    List<Flower> findByColorAndDescriptionIgnoreCase(String color, String description);
    List<Flower> findByOccasionAndDescriptionIgnoreCase(String occasion, String description);

    List<Flower> findByColorAndOccasionAndDescriptionIgnoreCase(String color, String occasion, String description);
}

