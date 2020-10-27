INSERT INTO `day_m`(`id`,`name`,`status`,`short_code`,`code`) VALUES
( '1', 'Sunday', '1', 'S', '1' ),
( '2', 'Monday', '1', 'M', '2' ),
( '3', 'Tuesday', '1', 'T', '3' ),
( '4', 'Wednesday', '1', 'W', '4' ),
( '5', 'Thursday', '1', 'T', '5' ),
( '6', 'Friday', '1', 'F', '6' ),
( '7', 'Saturday', '1', 'S', '7' );


create table if not exists oauth_access_token (
                                                  token_id VARCHAR(255),
                                                  token LONG VARBINARY,
                                                  authentication_id VARCHAR(255) PRIMARY KEY,
                                                  user_name VARCHAR(255),
                                                  client_id VARCHAR(255),
                                                  authentication LONG VARBINARY,
                                                  refresh_token VARCHAR(255)
);
create table if not exists oauth_refresh_token (
                                                   token_id VARCHAR(255),
                                                   token LONG VARBINARY,
                                                   authentication LONG VARBINARY
);

INSERT INTO relation_m(id,relation_desc,relation_name,status
) VALUES (1,'Family','Family',1
 );

INSERT INTO relation_m(id,relation_desc,relation_name,status
) VALUES (2,'Owner','Owner',1
 );


INSERT INTO weight_unit_m(id,w_desc,name
) VALUES (1,'kilograms','kg'
 );
INSERT INTO weight_unit_m(id,w_desc,name
) VALUES (2,'pounds','lbs'
 );

INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (1,9,NULL,NULL,'Uttar Pradesh','1',1
 );

INSERT INTO city_m(id,code,description,image_name,name,status,state_id
) VALUES (1,16,'Noida',NULL,'Noida','1',1
 );

INSERT INTO pin_code_m(id,code,description,status,city_id
) VALUES (1,201306,'Sector 74, 72 and 73','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (2,1,NULL,NULL,'Jammu & Kashmir','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (3,2,NULL,NULL,'Himachal Pradesh','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (4,3,NULL,NULL,'Punjab','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (5,4,NULL,NULL,'Chandigarh','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (6,5,NULL,NULL,'Uttarakhand','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (7,6,NULL,NULL,'Haryana','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (8,7,NULL,NULL,'Delhi','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (8,8,NULL,NULL,'Rajasthan','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (10,10,NULL,NULL,'Bihar','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (11,11,NULL,NULL,'Sikkim','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (12,12,NULL,NULL,'Arunachal Pradesh','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (13,13,NULL,NULL,'Nagaland','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (14,14,NULL,NULL,'Manipur','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (15,15,NULL,NULL,'Mizoram','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (16,16,NULL,NULL,'Tripura','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (17,17,NULL,NULL,'Meghalaya','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (18,18,NULL,NULL,'Assam','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (19,19,NULL,NULL,'West Bengal','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (20,20,NULL,NULL,'Jharkhand','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (21,21,NULL,NULL,'Orissa','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (22,22,NULL,NULL,'Chhattisgarh','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (23,23,NULL,NULL,'Madhya Pradesh','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (24,24,NULL,NULL,'Gujarat','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (25,25,NULL,NULL,'Daman & Diu','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (26,26,NULL,NULL,'Dadra & Nagar Haveli','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (27,27,NULL,NULL,'Maharashtra','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (28,28,NULL,NULL,'Andhra Pradesh','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (29,29,NULL,NULL,'Karnataka','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (30,30,NULL,NULL,'Goa','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (31,31,NULL,NULL,'Lakshadweep','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (32,32,NULL,NULL,'Kerala','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (33,33,NULL,NULL,'Tamil Nadu','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (34,34,NULL,NULL,'Puducherry','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (35,35,NULL,NULL,'Andaman & Nicobar Islands','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (36,36,NULL,NULL,'Telengana','1',1
 );

 INSERT INTO state_m(id,code,description,image_name,name,status,country_id
) VALUES (37,37,NULL,NULL,'Andhra Pradesh','1',1
 );


INSERT INTO `appointment_repeat_m`(
    `id`,
    `appointment_repeat`,
    `active`
) VALUES (
    1,
    ' Does not repeat',
    1
 );

INSERT INTO `appointment_type_m`(`id`,`appointment_type`,`active`) VALUES
( '1', 'Office Visit', '1' ),
( '2', 'Home Vist', '1' ),
( '3', 'Online Consultation', '1' ),
( '4', 'Surgery', '1' ),
( '5', 'Boarding', '1' ),
( '6', 'Grooming', '1' ),
( '7', 'Vaccination', '1' ),
( '8', 'Deworming', '1' );


INSERT INTO `appointment_reason_m`(
    `id`,
    `appointment_reason`,
    `active`
) VALUES (
    1,
    'Tossing Head',
    1
 );
