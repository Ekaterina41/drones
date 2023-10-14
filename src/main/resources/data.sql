-- Init 10 Drones in the database

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN001', 'LIGHTWEIGHT', 90, 94, 'IDLE');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN002', 'MIDDLEWEIGHT', 200, 92, 'IDLE');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN003', 'CRUISEWEIGHT', 270, 25, 'LOADING');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN004', 'HEAVYWEIGHT', 480, 33, 'LOADED');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN005', 'LIGHTWEIGHT', 80, 60, 'DELIVERING');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN006', 'MIDDLEWEIGHT', 195, 83, 'DELIVERED');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN007', 'CRUISEWEIGHT', 370, 22, 'RETURNING');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN008', 'HEAVYWEIGHT', 500, 57, 'RETURNING');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN009', 'LIGHTWEIGHT', 120, 4, 'IDLE');

INSERT INTO drone (serial_number, model, weight_limit, battery_level, state)
VALUES ('DRN010', 'MIDDLEWEIGHT', 210, 68, 'IDLE');

-- Init loaded Medications in the database

INSERT INTO medication (name, weight, code, image_url, drone_id)
VALUES ('Medication 1', 125, 'M123', 'medication_1.jpg', 4);

INSERT INTO medication (name, weight, code, image_url, drone_id)
VALUES ('Medication 2', 250, 'M456', 'medication_2.jpg', 4);

INSERT INTO medication (name, weight, code, image_url, drone_id)
VALUES ('Medication 3', 50, 'M352', 'medication_3.jpg', 5);