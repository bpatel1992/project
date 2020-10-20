INSERT INTO status_type_m(id,status_type_code,status_type_desc,status_type_name) VALUES 
( '1', '34', 'EEE', 'Sent' ),
( '2', '35', NULL, 'In Progress' );


INSERT INTO `status_type_m`(
    `id`,
    `status_type_code`,
    `status_type_desc`,
    `status_type_name`)
    VALUES
    (3,'36',NULL,'Success'),
    ( 4,'37', NULL,'Failed');

    INSERT INTO `services_m`(
        `id`,
        `service_desc`,
        `service_name`,
        `status`,
        `parent_service_id`,
        `service_type_id`
    ) VALUES (
        1,
        'Appointment',
        'Appointment',
        1,
        NULL,
        NULL
     );



