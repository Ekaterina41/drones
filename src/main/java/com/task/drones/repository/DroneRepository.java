package com.task.drones.repository;

import com.task.drones.entity.Drone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for Drone entities.
 */
@Repository
public interface DroneRepository extends CrudRepository<Drone, Integer> {

    List<Drone> findByStateIn(Collection<Drone.State> states);
    List<Drone> findAll();
}
