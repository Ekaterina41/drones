package com.task.drones.audit;

import com.task.drones.entity.BatteryLevelLogRecord;
import com.task.drones.repository.BatteryLevelLogRepository;
import com.task.drones.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A periodic task that saves drone battery level change history.
 */
@Component
@RequiredArgsConstructor
public class BatteryLevelLogTask {

    private final DroneRepository droneRepository;
    private final BatteryLevelLogRepository batteryAuditRepository;

    /**
     * Executes battery level check and stores results in the database.
     */
    @Scheduled(fixedDelayString = "${batteryCheck.interval:60000}")
    public void execute() {

        List<BatteryLevelLogRecord> auditRecords = droneRepository.findAll()
                .stream()
                .map(drone -> new BatteryLevelLogRecord()
                        .setCheckTime(LocalDateTime.now())
                        .setDroneId(drone.getId())
                        .setBatteryLevel(drone.getBatteryLevel()))
                .toList();

        batteryAuditRepository.saveAll(auditRecords);
    }
}
