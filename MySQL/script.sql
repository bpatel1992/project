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


INSERT INTO country_m(id,code,ct_desc,name,status
) VALUES (         1,         91,         'india - largest democracy',         'India',         1     );




INSERT INTO authority(authority_id,name
) VALUES (         1,         'ROLE_ADMIN'     );
INSERT INTO authority(authority_id,name
) VALUES (         2,         'ROLE_CUSTOMER'     );




INSERT INTO language_m(id,code,lang_desc,name,status
) VALUES (         1,         'en',         'English',         'English',         1     );

INSERT INTO language_m(id,code,lang_desc,name,status
) VALUES (         2,         'fr',         'French',         'French',         1     );

INSERT INTO user_login_type(id,login_type,status
) VALUES (         1,         'gmail',         1     );

INSERT INTO user_login_type(id,login_type,status
) VALUES (         2,         'facebook',         1     );

INSERT INTO user_login_type(id,login_type,status
) VALUES (         3,         'self',         1     );

INSERT INTO partner_type_m(name,partner_type_desc,status
) VALUES (         'Groomer',         "Pet Groomers",         1     );

INSERT INTO partner_type_m(name,partner_type_desc,status
) VALUES (         'Food',         "All types of food available",         1     );

INSERT INTO partner_type_m(name,partner_type_desc,status
) VALUES (         'Clinic',         "All type of doctors available",         1     );

INSERT INTO title_m(id,title,status)
VALUES (1,'Mr.',1 );

INSERT INTO title_m(id,title,status)
VALUES (2,'Ms.',1 );

INSERT INTO title_m(id,title,status)
VALUES (3,'Mrs.',1 );

INSERT INTO title_m(id,title,status)
VALUES (4,'Dr.',1 );

INSERT INTO profession_m(id,profession)
VALUES (1,'Veterinarian' );

INSERT INTO profession_m(id,profession)
VALUES (2,'Entrepreneur' );

INSERT INTO partner_address(id,address,lattitude,longitude,status,partner_id,latitude,address_type_id,displayOrder,title
) VALUES (1,'Flat 1902, Tower CM2, Supertech Capetown, Sector 74, Noida',NULL,NULL,'1',NULL,NULL,NULL,1,'Head Office'
 );

INSERT INTO partner_addresses_mp(partner_id,address_id
) VALUES (1,1
 );

 INSERT INTO business_timing(id,day_range
) VALUES (1,'Monday - Friday'
 );
INSERT INTO business_timing(id,day_range
) VALUES (2,'Saturday'
 );

 	INSERT INTO business_timing_partner_address_mp(partner_address_id,business_timing_id
	) VALUES (1,2
	 );
	INSERT INTO business_timing_partner_address_mp(partner_address_id,business_timing_id
	) VALUES (1,1
	 );

INSERT INTO time_range(id,name,display_order
) VALUES (1,'10:00 AM - 01:00 PM',10
 );
INSERT INTO time_range(id,name,display_order
) VALUES (2,'06:00 PM - 09:00 PM',20
 );

INSERT INTO business_timing_time_range_mp(business_timing_id,time_range_id
) VALUES (1,1
 );
INSERT INTO business_timing_time_range_mp(business_timing_id,time_range_id
) VALUES (1,2
 );
INSERT INTO business_timing_time_range_mp(business_timing_id,time_range_id
) VALUES (2,1
 );

-- Dump data of "document_type" ----------------------------
INSERT INTO document_type(id,document_type,page_quantity) VALUES
( '1', 'gallery', '40' ),
( '2', 'youtube', '40' );
-- ---------------------------------------------------------

-- Dump data of "partner_document" -------------------------
INSERT INTO partner_document(id,display_order,file_name,file_url,title,document_id,partner_id) VALUES 
( '1', '1', '1595004951_cardioforce.jpg', NULL, 'jai shree ram !', '1', '1' ),
( '2', '2', 'pho-pet-care-what-we-do.png', NULL, 'jai shree ram !', '1', '1' ),
( '3', '1', 'aJEdLCc4HuM', NULL, 'jai shree ram !', '2', '1' );
-- ---------------------------------------------------------

INSERT INTO `weight_unit_m`(
    `id`,
    `w_desc`,
    `name`
) VALUES (
    1,
    'kilograms',
    'Kg'
 );

