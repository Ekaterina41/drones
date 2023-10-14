package com.task.drones.repository;

import com.task.drones.entity.BatteryLevelLogRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Drone battery audit records access.
 */
@Repository
public interface BatteryLevelLogRepository
        extends CrudRepository<BatteryLevelLogRecord, Integer> {
}
