-- Tables
CREATE TABLE `countries` (
    `country_id` bigint NOT NULL AUTO_INCREMENT,
    `country` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `last_update` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`country_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `vacations` (
    `vacation_id` bigint NOT NULL AUTO_INCREMENT,
    `create_date` datetime(6) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `last_update` datetime(6) DEFAULT NULL,
    `travel_fare_price` decimal(19, 2) DEFAULT NULL,
    `vacation_title` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`vacation_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `divisions` (
    `division` varchar(255) DEFAULT NULL,
    `division_id` bigint NOT NULL AUTO_INCREMENT,
    `create_date` datetime(6) DEFAULT NULL,
    `last_update` datetime(6) DEFAULT NULL,
    `country_id` bigint NOT NULL,
    PRIMARY KEY (`division_id`),
    KEY (`country_id`),
    CONSTRAINT FOREIGN KEY (`country_id`) REFERENCES `countries` (`country_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `excursions` (
    `excursion_id` bigint NOT NULL AUTO_INCREMENT,
    `create_date` datetime(6) DEFAULT NULL,
    `excursion_price` decimal(19, 2) DEFAULT NULL,
    `excursion_title` varchar(255) DEFAULT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `last_update` datetime(6) DEFAULT NULL,
    `vacation_id` bigint NOT NULL,
    PRIMARY KEY (`excursion_id`),
    KEY (`vacation_id`),
    CONSTRAINT FOREIGN KEY (`vacation_id`) REFERENCES `vacations` (`vacation_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `customers` (
    `customer_id` bigint NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `city` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `customer_first_name` varchar(255) DEFAULT NULL,
    `customer_last_name` varchar(255) DEFAULT NULL,
    `last_update` datetime(6) DEFAULT NULL,
    `phone` varchar(255) DEFAULT NULL,
    `postal_code` varchar(255) DEFAULT NULL,
    `division_id` bigint DEFAULT NULL,
    PRIMARY KEY (`customer_id`),
    UNIQUE KEY `uk_customers_email` (`email`),
    KEY (`division_id`),
    CONSTRAINT FOREIGN KEY (`division_id`) REFERENCES `divisions` (`division_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `carts` (
    `cart_id` bigint NOT NULL AUTO_INCREMENT,
    `package_price` decimal(19, 2) DEFAULT NULL,
    `party_size` int DEFAULT NULL,
    `status` ENUM ('pending', 'ordered', 'canceled'),
    `order_tracking_number` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `last_update` datetime(6) DEFAULT NULL,
    `customer_id` bigint NOT NULL,
    PRIMARY KEY (`cart_id`),
    KEY (`customer_id`),
    CONSTRAINT FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `cart_items` (
    `cart_item_id` bigint NOT NULL AUTO_INCREMENT,
    `create_date` datetime(6) DEFAULT NULL,
    `last_update` datetime(6) DEFAULT NULL,
    `cart_id` bigint NOT NULL,
    `vacation_id` bigint NOT NULL,
    PRIMARY KEY (`cart_item_id`),
    KEY (`cart_id`),
    KEY (`vacation_id`),
    CONSTRAINT FOREIGN KEY (`vacation_id`) REFERENCES `vacations` (`vacation_id`),
    CONSTRAINT FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `excursion_cartitem` (
    `cart_item_id` bigint NOT NULL,
    `excursion_id` bigint NOT NULL,
    PRIMARY KEY (`cart_item_id`, `excursion_id`),
    KEY (`excursion_id`),
    CONSTRAINT FOREIGN KEY (`cart_item_id`) REFERENCES `cart_items` (`cart_item_id`),
    CONSTRAINT FOREIGN KEY (`excursion_id`) REFERENCES `excursions` (`excursion_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Seed Data: Countries
INSERT INTO `countries` VALUES 
    (1, 'U.S', NOW(), NOW()),
    (2, 'UK', NOW(), NOW()),
    (3, 'Canada', NOW(), NOW());

-- Seed Data: Divisions (States/Provinces)
INSERT INTO `divisions` VALUES 
    ('Alaska', 54, NOW(), NOW(), 1),
    ('Arizona', 02, NOW(), NOW(), 1),
    ('Arkansas', 03, NOW(), NOW(), 1),
    ('California', 04, NOW(), NOW(), 1),
    ('Colorado', 05, NOW(), NOW(), 1),
    ('Connecticut', 06, NOW(), NOW(), 1),
    ('Delaware', 07, NOW(), NOW(), 1),
    ('District of Columbia', 08, NOW(), NOW(), 1),
    ('Florida', 09, NOW(), NOW(), 1),
    ('Georgia', 10, NOW(), NOW(), 1),
    ('Hawaii', 52, NOW(), NOW(), 1),
    ('Idaho', 11, NOW(), NOW(), 1),
    ('Illinois', 12, NOW(), NOW(), 1),
    ('Indiana', 13, NOW(), NOW(), 1),
    ('Iowa', 14, NOW(), NOW(), 1),
    ('Kansas', 15, NOW(), NOW(), 1),
    ('Kentucky', 16, NOW(), NOW(), 1),
    ('Louisiana', 17, NOW(), NOW(), 1),
    ('Maine', 18, NOW(), NOW(), 1),
    ('Maryland', 19, NOW(), NOW(), 1),
    ('Massachusetts', 20, NOW(), NOW(), 1),
    ('Michigan', 21, NOW(), NOW(), 1),
    ('Minnesota', 22, NOW(), NOW(), 1),
    ('Mississippi', 23, NOW(), NOW(), 1),
    ('Missouri', 24, NOW(), NOW(), 1),
    ('Montana', 25, NOW(), NOW(), 1),
    ('Nebraska', 26, NOW(), NOW(), 1),
    ('Nevada', 27, NOW(), NOW(), 1),
    ('New Hampshire', 28, NOW(), NOW(), 1),
    ('New Jersey', 29, NOW(), NOW(), 1),
    ('New Mexico', 30, NOW(), NOW(), 1),
    ('New York', 31, NOW(), NOW(), 1),
    ('North Carolina', 32, NOW(), NOW(), 1),
    ('North Dakota', 33, NOW(), NOW(), 1),
    ('Ohio', 34, NOW(), NOW(), 1),
    ('Oklahoma', 35, NOW(), NOW(), 1),
    ('Oregon', 36, NOW(), NOW(), 1),
    ('Pennsylvania', 37, NOW(), NOW(), 1),
    ('Rhode Island', 38, NOW(), NOW(), 1),
    ('South Carolina', 39, NOW(), NOW(), 1),
    ('South Dakota', 40, NOW(), NOW(), 1),
    ('Tennessee', 41, NOW(), NOW(), 1),
    ('Texas', 42, NOW(), NOW(), 1),
    ('Utah', 43, NOW(), NOW(), 1),
    ('Vermont', 44, NOW(), NOW(), 1),
    ('Virginia', 45, NOW(), NOW(), 1),
    ('Washington', 46, NOW(), NOW(), 1),
    ('West Virginia', 47, NOW(), NOW(), 1),
    ('Wisconsin', 48, NOW(), NOW(), 1),
    ('Wyoming', 49, NOW(), NOW(), 1),
    ('Alberta', 61, NOW(), NOW(), 3),
    ('British Columbia', 62, NOW(), NOW(), 3),
    ('Manitoba', 63, NOW(), NOW(), 3),
    ('New Brunswick', 64, NOW(), NOW(), 3),
    ('Newfoundland and Labrador', 72, NOW(), NOW(), 3),
    ('Northwest Territories', 60, NOW(), NOW(), 3),
    ('Nova Scotia', 65, NOW(), NOW(), 3),
    ('Nunavut', 70, NOW(), NOW(), 3),
    ('Ontario', 67, NOW(), NOW(), 3),
    ('Prince Edward Island', 66, NOW(), NOW(), 3),
    ('Québec', 68, NOW(), NOW(), 3),
    ('Saskatchewan', 69, NOW(), NOW(), 3),
    ('Yukon', 71, NOW(), NOW(), 3),
    ('England', 101, NOW(), NOW(), 2),
    ('Wales', 102, NOW(), NOW(), 2),
    ('Scotland', 103, NOW(), NOW(), 2),
    ('Northern Ireland', 104, NOW(), NOW(), 2);

-- Seed Data: Vacations
INSERT INTO `vacations` VALUES
    (1, NOW(), 'Visit the beautiful country of Italy', 'https://images.unsplash.com/photo-1515859005217-8a1f08870f59?ixlib=rb-4.0.3&auto=format&fit=crop&w=1110&q=80', NOW(), 1000, 'Italy'),
    (2, NOW(), 'Visit the beautiful country of Greece', 'https://images.unsplash.com/photo-1533105079780-92b9be482077?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 1500, 'Greece'),
    (3, NOW(), 'Visit the beautiful country of France', 'https://images.unsplash.com/photo-1502602898657-3e91760cbb34?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 1500, 'France'),
    (4, NOW(), 'Visit the beautiful country of Belgium', 'https://images.unsplash.com/photo-1491557345352-5929e343eb89?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 1500, 'Belgium'),
    (5, NOW(), 'Visit the beautiful country of Brazil', 'https://images.unsplash.com/photo-1483729558449-99ef09a8c325?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 1500, 'Brazil'),
    (6, NOW(), 'Visit the beautiful state of South Dakota', 'https://images.unsplash.com/photo-1605801495512-f47a64d01f4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1472&q=80', NOW(), 1500, 'South Dakota'),
    (7, NOW(), 'Visit the beautiful city of Nashville', 'https://images.unsplash.com/photo-1545419913-775e3e82c7db?ixlib=rb-4.0.3&auto=format&fit=crop&w=1636&q=80', NOW(), 1500, 'Nashville'),
    (8, NOW(), 'Visit the beautiful state of Wisconsin', 'https://images.unsplash.com/photo-1566419808810-658178380987?ixlib=rb-4.0.3&auto=format&fit=crop&w=1471&q=80', NOW(), 1500, 'Wisconsin');

-- Seed Data: Excursions (8 per vacation)
INSERT INTO `excursions` (`excursion_id`, `create_date`, `excursion_price`, `excursion_title`, `image_url`, `last_update`, `vacation_id`) VALUES
    -- Italy (vacation_id = 1)
    (1, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 1),
    (2, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 1),
    (3, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 1),
    (4, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 1),
    (5, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 1),
    (6, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 1),
    (7, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 1),
    (8, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 1),
    -- Greece (vacation_id = 2)
    (9, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 2),
    (10, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 2),
    (11, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 2),
    (12, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 2),
    (13, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 2),
    (14, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 2),
    (15, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 2),
    (16, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 2),
    -- France (vacation_id = 3)
    (17, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 3),
    (18, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 3),
    (19, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 3),
    (20, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 3),
    (21, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 3),
    (22, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 3),
    (23, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 3),
    (24, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 3),
    -- Belgium (vacation_id = 4)
    (25, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 4),
    (26, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 4),
    (27, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 4),
    (28, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 4),
    (29, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 4),
    (30, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 4),
    (31, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 4),
    (32, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 4),
    -- Brazil (vacation_id = 5)
    (33, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 5),
    (34, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 5),
    (35, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 5),
    (36, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 5),
    (37, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 5),
    (38, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 5),
    (39, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 5),
    (40, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 5),
    -- South Dakota (vacation_id = 6)
    (41, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 6),
    (42, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 6),
    (43, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 6),
    (44, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 6),
    (45, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 6),
    (46, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 6),
    (47, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 6),
    (48, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 6),
    -- Nashville (vacation_id = 7)
    (49, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 7),
    (50, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 7),
    (51, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 7),
    (52, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 7),
    (53, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 7),
    (54, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 7),
    (55, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 7),
    (56, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 7),
    -- Wisconsin (vacation_id = 8)
    (57, NOW(), 100, 'Cheese Tour', 'https://images.unsplash.com/photo-1631379578550-7038263db699?ixlib=rb-4.0.3&auto=format&fit=crop&w=1474&q=80', NOW(), 8),
    (58, NOW(), 75, 'Bicycle Tour', 'https://images.unsplash.com/uploads/14122621859313b34d52b/37e28531?ixlib=rb-4.0.3&auto=format&fit=crop&w=1473&q=80', NOW(), 8),
    (59, NOW(), 250, 'Spa Treatment', 'https://images.unsplash.com/photo-1620733723572-11c53f73a416?ixlib=rb-4.0.3&auto=format&fit=crop&w=687&q=80', NOW(), 8),
    (60, NOW(), 100, 'Historic Tour', 'https://images.unsplash.com/photo-1479142506502-19b3a3b7ff33?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 8),
    (61, NOW(), 25, 'Boat Ride', 'https://images.unsplash.com/photo-1587252337395-d02401a8a814?ixlib=rb-4.0.3&auto=format&fit=crop&w=1026&q=80', NOW(), 8),
    (62, NOW(), 500, 'Horseback Riding Lesson', 'https://images.unsplash.com/photo-1598810577851-34982c359155?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 8),
    (63, NOW(), 120, 'Zip Lining', 'https://images.unsplash.com/photo-1625076307714-a5cd1b2beb4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 8),
    (64, NOW(), 150, 'Dinner and a Show', 'https://plus.unsplash.com/premium_photo-1661774645265-ce387923cb5b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80', NOW(), 8);
