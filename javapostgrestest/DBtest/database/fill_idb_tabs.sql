INSERT INTO "size" ("id","name") VALUES
(1,'36'),
(2,'37'),
(3,'38'),
(4,'39'),
(5,'40'),
(6,'41'),
(7,'42'),
(8,'43'),
(9,'44'),
(10,'45'),
(11,'46'),
(12,'47'),
(13,'48'),
(14,'49'),
(15,'50');

SELECT setval('size_id_seq', 15);
---------------------------------------
INSERT INTO "color" ("id","name") VALUES
(1,'white'),
(2,'black' ),
(3,'brown'),
(4,'red'),
(5,'pink'),
(6,'blue'),
(7,'orange'),
(8,'yellow'),
(9,'green'),
(10,'grey'),
(11,'darkblue'),
(12,'silver'),
(13,'gold'),
(14,'purple'),
(15,'multicolor');
SELECT setval('color_id_seq', 15);
--================================================================
INSERT INTO "category" ("id", "name" , "parentid") VALUES
---------------------------------------------------
(1,'Special shoes',1),
(2,'Men shoes',NULL),
(3,'Mens Boots',2),
(4,'Mens Winter Boots',2),
(5,'Mens Chelsea',2),
(6,'Mens Loafers',2),
(7,'Mens Shoes',2),
(8,'Mens Flip Flops',2),
(9,'Mens Sandals',2),
(10,'Mens Slippers',2),
(11,'Mens Sports Shoes',2),
(12,'Women shoes',NULL),
(13,'Womens Boots',12),
(14,'Womens Sandals',12),
(15,'Womens Slippers',12),
(16,'Womens Ankle Boots',12),
(17,'Womens Chelsea Boots',12),
(18,'Womens Sports Shoes',12),
(19,'Womens Loafers',12),
(20,'Womens Flip Flops',12),
(21,'Womens Boat Shoes',12),
(22,'Womens Winter Boots',12),
(23,'Womens High Boots',12),
(24,'Womens Heeled Boots',12),
(25,'Unisex shoes',NULL),
(26,'Unisex Rubber Shoes',25),
(27,'Unisex Slippers',25);
-----------------------------------------------
SELECT setval('category_id_seq',27);
--================================================================
INSERT INTO "classification" ("id", "name"  , "parentid", "categoryid") VALUES
---------------------------------------------------
(1,'Category',NULL,NULL),
(2,'Special shoes',1,1),
(3,'Unisex shoes',1,25),
(4,'Mens Boots',1,3),
(5,'Mens Winter Boots',1,4),
(6,'Mens Chelsea',1,5),
(7,'Mens Loafers',1,6),
(8,'Mens Shoes',1,7),
(9,'Mens Flip Flops',1,8),
(10,'Mens Sandals',1,9),
(11,'Mens Slippers',1,10),
(12,'Mens Sports Shoes',1,11),
(13,'Womens Boots',1,13),
(14,'Womens Sandals',1,14),
(15,'Womens Slippers',1,15),
(16,'Womens Ankle Boots',1,16),
(17,'Womens Chelsea Boots',1,17),
(18,'Womens Sports Shoes',1,18),
(19,'Womens Loafers',1,19),
(20,'Womens Flip Flops',1,20),
(21,'Womens Boat Shoes',1,21),
(22,'Womens Winter Boots',1,22),
(23,'Womens High Boots',1,23),
(24,'Womens Heeled Boots',1,24),
(25,'Unisex Rubber Shoes',1,26),
(26,'Unisex Slippers',1,27),
(27,'Season',NULL,NULL),
(28,'Winter',27,NULL),
(29,'Allseason',27,NULL),
(30,'Insulated',27,NULL),
(31,'Summer',27,NULL),
(32,'Brand',NULL,NULL),
(33,'Euroshoes',32,NULL),
(34,'Puma',32,NULL),
(35,'Belwest',32,NULL),
(36,'Material',NULL,NULL),
(37,'Natural leather',36,NULL),
(38,'Faux leather',36,NULL),
(39,'Nubuck',36,NULL),
(40,'Textile',36,NULL),
(41,'Rubber',36,NULL),
(42,'Suede',36,NULL),
(43,'Color',NULL,NULL),
(44,'white',43,NULL),
(45,'black',43,NULL),
(46,'brown',43,NULL),
(47,'red',43,NULL),
(48,'pink',43,NULL),
(49,'blue',43,NULL),
(50,'orange',43,NULL),
(51,'yellow',43,NULL),
(52,'green',43,NULL),
(53,'grey',43,NULL),
(54,'bordo',43,NULL),
(55,'silver',43,NULL),
(56,'gold',43,NULL),
(57,'purple',43,NULL),
(58,'multicolor',43,NULL),
(59,'Prices',NULL,NULL),
(60,'<50',59,NULL),
(61,'<100',59,NULL),
(62,'>100',59,NULL),
(63,'Discounts',NULL,NULL),
(64,'0',63,NULL),
(65,'<20',63,NULL),
(66,'<40',63,NULL),
(67,'>40',63,NULL),
(68,'In stock',68,NULL);
-----------------------------------------------
SELECT setval('classification_id_seq',68);
--===========================================================================
INSERT INTO "users"
---------------------------------------------------
("id", "name"     , "login", "password"      , "email"                 ,"roleid") VALUES
---------------------------------------------------
(   1, 'Admin'    ,  'Admin',  'Admin'    , 'ruslan-y-a@mail.ru'    , 0      ),
(   2, 'Product'  ,  'Product',  'Product'    , 'ruslan-y-a@mail.ru'    , 2      ),
(   3, 'Manager'  ,  'Manager', 'Manager'    , 'ruslan-y-a@mail.ru'    , 3      ),
(   4, 'Cashier'  ,  'Cashier' ,'Cashier'    , 'ruslan-y-a@mail.ru'    , 4      ),
(   5, 'Client'  ,  'Client', '123456789'    , 'ruslan-y-a@mail.ru'     , 1      ),
(   6, 'Courier'  , 'Courier' , 'Courier'    , 'ruslan-y-a@mail.ru'     , 5      );
-----------------------------------------------
SELECT setval('users_id_seq', 5);
--================================================================

INSERT INTO "tagurl"
---------------------------------------------------
("id", "tagname"                      ) VALUES
---------------------------------------------------
(   1,  'Black Mens shoes'           ),
(   2,  'Mens natural leather boots' ),
(   3,  'Womens Puma sport shoes'    ),
(   4,  'Womens sport shoes'         ),
(   5, 'Mens Puma sport shoes'      ),
(   6,  'Mens sport shoes'           ),
(   7,  'Womens silver summer sandals'),
(   8,  'Womens cheap summer shoes'  ),
(   9,  'Mens Belwest summer shoes'  ),
(  10,  'Mens Belwest cheap sandals' );
-----------------------------------------------
SELECT setval('tagurl_id_seq', 10);
--================================================================
INSERT INTO "tagcloud"  ("id", "classification") VALUES
---------------------------------------------------
(   1, '{10,41}'   ),
(   2, '{20,36}'     ),
(   3, '{14,36}'     ),
(   4, '{16,57}'     ),
(   5, '{33,37}'     );
-----------------------------------------------
SELECT setval('tagcloud_id_seq', 5);
--================================================================
INSERT INTO "items"
("id", "articul", "model" , "category", "baseprice", "discount", "name" ,  "classification", "img" ,  "active", "webpages")
VALUES
(1, '01505_01', '01505_01',7,60,0,'Mens shoes 01505_01','{2,10,31,39,47}',NULL,true,NULL),
(2,'37284903','37284903',11,110,0,'Womens sneakers 37284903','{3,20,31,36,42}',NULL,true,NULL),
(3,'37112005','37112005',18,70,25,'Mens sneakers 37112005','{2,14,31,36,42}',NULL,true,NULL);
-----------------------------------------------
SELECT setval('items_id_seq', 3);
--================================================================
INSERT INTO "itemtagurl" 
("id","items","tagurl") VALUES
(1,1,1),
(2,2,2),
(3,3,3);
SELECT setval('itemtagurl_id_seq', 3);
--================================================================
INSERT INTO "itemcatgory" 
("id","items","classification") VALUES
(1,1,2),
(2,1,10),
(3,1,31),
(4,1,37),
(5,1,39),
(6,1,47),
(7,1,63),
(8,1,66),
(9,1,68),
(10,2,3),
(11,2,20),
(12,2,31),
(13,2,36),
(14,2,42),
(15,2,60),
(16,2,64),
(17,2,66),
(18,2,68),
(19,3,2),
(20,3,14),
(21,3,31),
(22,3,36),
(23,3,42),
(24,3,47),
(25,3,63),
(26,3,66),
(27,3,67);
SELECT setval('itemcatgory_id_seq', 27);
--------------------------------------------
INSERT INTO "currency"
---------------------------------------------------
("id", "name"     , "rate" ) VALUES
---------------------------------------------------
(   1,   'USD'    ,    1    ),
(   2,   'EUR'    ,    1.1  ),
(   3,   'GBP'    ,    1.9  );
-----------------------------------------------
SELECT setval('currency_id_seq',3 );
--================================================================
INSERT INTO "country"
---------------------------------------------------
("id", "name"     , "currency" ) VALUES
---------------------------------------------------
(   1, 'USA'      , 1      ),
(   2, 'Europe'   , 2      );
-----------------------------------------------
SELECT setval('country_id_seq',2 );
--================================================================
INSERT INTO "baseitem"
---------------------------------------------------
("id", "itemid" , "color", "size" , "quantity"   ) VALUES
---------------------------------------------------
(   1, 1          ,  7, 2 ,  10       ),
(   2, 1          ,  8, 2 ,  10       ),
(   3, 1          ,  9, 2 ,  10       ),
(   4, 2          ,  2, 15 ,  10       ),
(   5, 2          ,  3, 15 ,  10       ),
(   6, 2          ,  4, 15 ,  10       ),
(   7, 2          ,  7, 5  ,  10       ),
(   8, 2          ,  8, 2  ,  10       ),
(   9, 3          ,  2,12  ,  10       ),
(  10, 3          ,  3, 12 ,  10       ),
(  11, 3          ,  7, 3  ,  10       ),
(  12, 3          ,  8, 3  ,  10       ),
(  13, 3          ,  9, 3 ,  10       ),
(  14, 1          ,  7, 3 ,  10       ),
(  15, 1          ,  8, 3  ,  10       ),
(  16, 1          ,  9, 3  ,  10       );
-----------------------------------------------
SELECT setval('baseitem_id_seq',16 );
--================================================================
INSERT INTO "client"
---------------------------------------------------
("id","countryid",  "address"  ,"creationdate","userid","bonuspoints","phoneno") VALUES
---------------------------------------------------
(1 , 1           ,'Address1'   , '2018-12-07' , 1      , 0           ,'+375(29) 123-45-67' ),
(2 , 2           ,'Address2'   , '2018-12-07' , 2      , 0           ,'+375 (33) 987-65-43' );
-----------------------------------------------
SELECT setval('client_id_seq',2 );
--================================================================
INSERT INTO "orders"
---------------------------------------------------
("id","number","datetime"        ,"dateexpired","baseitem","client","quantity","sum",
 "currency","delivery" ,"active"  ,"status") VALUES
---------------------------------------------------
(1 ,1  ,  '2019-12-07 14:30:00'  ,'2019-12-12' ,   1         , 1         , 1        , 60  ,
 1           , 1          ,    FALSE           ,   2     ),
(2 ,1   ,  '2019-12-07 14:30:00'  ,'2019-12-12' ,   4         , 1         , 1        , 110 ,
 1           , 1          ,    FALSE           ,   2     ),
(3 ,1   ,  '2019-12-07 14:30:00'  ,'2019-12-12' ,   7         , 1         , 1        , 70  ,
 1           , 1          ,    TRUE           ,  1     ),
(4 ,2   ,  '2019-12-13 14:30:00'  ,'2019-12-18' ,    9        , 2         , 1        , 35  ,
 2           , 1          ,    FALSE           ,  1     ),
(5 ,2   ,  '2019-12-13 14:30:00'  ,'2019-12-18' ,    11       , 2         , 1        , 35  ,
 2           , 1          ,    FALSE           ,   2     ),
(6 ,2  ,  '2019-12-13 14:30:00'  ,'2019-12-18' ,    14       , 2         , 1        , 49  ,
 2           , 1          ,    FALSE           ,   2     );
-----------------------------------------------
SELECT setval('orders_id_seq',6 );
--================================================================
INSERT INTO "webpages"
("id", "url", "title", "description", "keywords", "h1", "text", "robots")
VALUES
(1,'black-men-shoes-01505-01', 
  'Modern allseason black men shoes of natural leather 01505_01',
  'Modern allseason black men shoes of natural leather 01505_01',
  'Mens shoes, Mens black shoes',
  'Men shoes 01505_01',
  'Modern allseason black men shoes of natural leather 01505_01.',
  'index,follow'),
(2,'modern-women-sneakers-37284903',
   'Modern women sneakers 37284903. Puma sport shoes',
   'Modern women sneakers 37284903. Puma sport shoes',
   'Women sneakers, women sport shoes, Women Puma sneakers',
   'Mens shoes 01505_01',
   'Modern women sneakers 37284903. Puma sport shoes',
   'index,follow'),
(3,'black-men-sneakers-37112005',
   'Modern black mens sneakers 37112005. Puma sport shoes',   
   'Modern black mens sneakers 37112005. Puma sport shoes',
   'Mens sneakers, Mens sport shoes , Mens Puma sneakers',
   'Mens sneakers 37112005',
   'Modern black mens sneakers 37112005. Puma sport shoes',   
   'index,follow');
		
-----------------------------------------------
SELECT setval('webpages_id_seq',3 );
--================================================================
INSERT INTO "sale"
---------------------------------------------------
("id","datetime"                 ,"order","returned","currency") VALUES
---------------------------------------------------
(1   , '2018-12-07 14:30:00' , 1       , FALSE  ,1           ),
(2   , '2018-12-07 14:30:00' , 2       , FALSE  ,1           ),
(3   , '2018-12-08 16:30:00' , 4       , FALSE  ,1           ),
(4   , '2018-12-08 16:30:00' , 5       , FALSE  ,1           );
--------------------------------------------------------------
SELECT setval('sale_id_seq',4 );
--================================================================