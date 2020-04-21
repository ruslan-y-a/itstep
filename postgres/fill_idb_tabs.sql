INSERT INTO "variant"
---------------------------------------------------
("id", "name"                 , "parentid") VALUES
---------------------------------------------------
(   1, 'Size'                 , NULL       ),
(   2,     '36'               , 1          ),
(   3,     '37'               , 1          ),
(   4,     '38'               , 1          ),
(   5,     '39'               , 1          ),
(   6,     '40'               , 1          ),
(   7,     '41'               , 1          ),
(   8,     '42'               , 1          ),
(   9,     '43'               , 1          ),
(  10,     '44'               , 1          ),
(  11,     '45'               , 1          ),
(  12,     '46'               , 1          ),
(  13,     '47'               , 1          ),
(  14,     '48'               , 1          ),
(  15, 'Color'                , NULL       ),
(  16,     'white'            , 15         ),
(  17,     'black'            , 15         ),
(  18,     'brown'            , 15         ),
(  19,     'red'              , 15         ),
(  20,     'pink'             , 15         ),
(  21,     'blue'             , 15         ),
(  22,     'orange'           , 15         ),
(  23,     'yellow'           , 15         ),
(  24,     'green'            , 15         ),
(  25,     'grey'             , 15         ),
(  26,     'darkblue'         , 15         ),
(  27,     'silver'           , 15         ),
(  28,     'gold'             , 15         ),
(  29,     'purple'           , 15         ),
(  30,     'multicolor'       , 15         );
-----------------------------------------------
SELECT setval('variant_id_seq', 30);
--================================================================
INSERT INTO "category"
---------------------------------------------------
("id", "name"                           , "parentid") VALUES
---------------------------------------------------
(   1, 'Special shoes'                  , 1          ),
(   2, 'Men shoes'                   , NULL       ),
(   3,         'Boots'                  , 2          ),
(   4,         'Winter Boots'           , 2          ),
(   5,         'Chelsea Boots'          , 2          ),
(   6,         'Loafers'                , 2          ),
(   7,         'Mens Shoes'             , 2          ),
(   8,         'Flip Flops'             , 2          ),
(   9,         'Sandals'                , 2          ),
(  10,         'Slippers'               , 2          ),
(  11,         'Sports Shoes'           , 2          ),
(  12,  'Women shoes'                , NULL       ),
(  13,         'Boots'                  , 12         ),
(  14,         'Sandals'                , 12         ),
(  15,         'Slippers'               , 12         ),
(  16,         'Ankle Boots'            , 12         ),
(  17,         'Chelsea Boots'          , 12         ),
(  18,         'Womens Boat Shoes'      , 12         ),
(  19,         'Loafers'                , 12         ),
(  20,         'Flip Flops'             , 12         ),
(  21,         'Boat Shoes'             , 12         ),
(  22,         'Winter Boots'           , 12         ),
(  23,         'High Boots'             , 12         ),
(  24,  'Unisex shoes'                  , NULL       ),
(  25,         'Rubber Shoes'           , 24         ),
(  26,         'Rubber Boots'           , 24         ),
(  27,         'Slippers'               , 24         ),
(  28, 'Season'                         , NULL       ),
(  29,     'Winter'                     , 28         ),
(  30,     'Allseason'                  , 28         ),
(  31,     'Insulated'                  , 28         ),
(  32,     'Summer'                     , 28         ),
(  33, 'Brand'                          , NULL       ),
(  34,     'Euroshoes'                  , 33         ),
(  35,     'Puma'                       , 33         ),
(  36,     'Belwest'                    , 33         ),
(  37, 'Sale'                           , 37       ),
(  38, 'Sport'                          , 38       ),
(  39, 'Print'                          , 39       ),
(  40, 'Wedding'                        , 40       );
-----------------------------------------------
SELECT setval('category_id_seq',40);
--================================================================
INSERT INTO "classification"
---------------------------------------------------
("id", "name"                           , "parentid", "categoryid") VALUES
---------------------------------------------------
(   1, 'Special shoes'                       , 1    , 1           ),
(   2, 'Mens shoes'                        , 2    , 2           ),
(   3, 'Womens shoes'                      , 3    , 12          ),
(   4, 'Unisex shoes'                        , 4    , 24          ),
(   5, 'Type'                                , NULL , NULL        ),
(   6,         'Mens Boots'                  , 5    , 3           ),
(   7,         'Mens Winter Boots'           , 5    , 4           ),
(   8,         'Mens Chelsea'                , 5    , 5           ),
(   9,         'Mens Loafers'                , 5    , 6           ),
(  10,         'Mens Shoes'                  , 5    , 7           ),
(  11,         'Mens Flip Flops'             , 5    , 8           ),
(  12,         'Mens Sandals'                , 5    , 9           ),
(  13,         'Mens Slippers'               , 5    , 10          ),
(  14,         'Mens Sports Shoes'           , 5    , 11          ),
(  15,         'Womens Boots'                , 5    , 13          ),
(  16,         'Womens Sandals'              , 5    , 14          ),
(  17,         'Womens Slippers'             , 5    , 15          ),
(  18,         'Womens Ankle Boots'          , 5    , 16          ),
(  19,         'Womens Chelsea Boots'        , 5    , 17          ),
(  20,         'Womens Sports Shoes'         , 5    , 18          ),
(  21,         'Womens Loafers'              , 5    , 19          ),
(  22,         'Womens Flip Flops'           , 5    , 20          ),
(  23,         'Womens Boat Shoes'           , 5    , 21          ),
(  24,         'Womens Winter Boots'         , 5    , 22          ),
(  25,         'Womens High Boots'           , 5    , 23          ),
(  26,         'Unisex Rubber Shoes'           , 5    , 25          ),
(  27,         'Unisex Rubber Boots'           , 5    , 26          ),
(  28,         'Unisex Slippers'               , 5    , 27          ),
(  29, 'Season'                                , NULL , 28        ),
(  30,     'Winter'                            , 29   , 29          ),
(  31,     'Allseason'                         , 29   , 30          ),
(  32,     'Insulated'                         , 29   , 31          ),
(  33,     'Summer'                            , 29   , 32          ),
(  34, 'Brand'                                 , NULL , NULL        ),
(  35,     'Euroshoes'                         , 34   , 34          ),
(  36,     'Puma'                              , 34   , 35          ),
(  37,     'Belwest'                           , 34   , 36          ),
(  38, 'Material'                              , NULL , NULL        ),
(  39,     'Natural leather'                   , 38   , NULL        ),      
(  40,     'Faux leather'                      , 38   , NULL        ),    
(  41,     'Nubuck'                            , 38   , NULL        ),    
(  42,     'Textile'                           , 38   , NULL        ),    
(  43,     'Rubber'                            , 38   , NULL        ),    
(  44,     'Suede'                             , 38   , NULL        ),    
(  45, 'Color'                                 , NULL , NULL        ),
(  46,     'white'                             , 45   , NULL        ),
(  47,     'black'                             , 45   , NULL        ),
(  48,     'brown'                             , 45   , NULL        ),
(  49,     'red'                               , 45   , NULL        ),
(  50,     'pink'                              , 45   , NULL        ),
(  51,     'blue'                              , 45   , NULL        ),
(  52,     'orange'                            , 45   , NULL        ),
(  53,     'yellow'                            , 45   , NULL        ),
(  54,     'green'                             , 45   , NULL        ),
(  55,     'grey'                              , 45   , NULL        ),
(  56,     'bordo'                             , 45   , NULL        ),
(  57,     'silver'                            , 45   , NULL        ),
(  58,     'gold'                              , 45   , NULL        ),
(  59,     'purple'                            , 45   , NULL        ),
(  60,     'multicolor'                        , 45   , NULL        ),
(  61, 'Prices'                                , NULL , NULL        ),
(  62,     '<50'                               , 61   , NULL        ),
(  63,     '<100'                               , 61   , NULL        ),
(  64,     '>100'                              , 61   , NULL        ),
(  65, 'Discounts'                             , NULL , NULL        ),
(  66,     '0'                                 , 65   , NULL        ),
(  67,     '<20'                               , 65   , NULL        ),
(  68,     '<40'                                , 65   , NULL        ),
(  69,     '>40'                               , 65   , NULL        ),
(  70, 'In stock'                              , 70   , NULL        );
-----------------------------------------------
SELECT setval('classification_id_seq',70);
--===========================================================================
INSERT INTO "users"
---------------------------------------------------
("id", "name"     , "password"      , "email"                 ,"roleid") VALUES
---------------------------------------------------
(   1, 'Admin'    ,  '5105565Aa'    , 'ruslan-y-a@mail.ru'    , 0      ),
(   2, 'Product'  ,  '5105565Pp'    , 'ruslan-y-a@mail.ru'    , 2      ),
(   3, 'Manager'  ,  '5105565Mm'    , 'ruslan-y-a@mail.ru'    , 3      ),
(   4, 'Cashier'  ,  '5105565Cc'    , 'ruslan-y-a@mail.ru'    , 4      ),
(   5, 'Client'  ,  '123456789'    , 'ruslan-y-a@mail.ru'     , 1      ),
(   6, 'Client2'  ,  '123456789'    , 'ruslan-y-a@mail.ru'    , 1      );
-----------------------------------------------
SELECT setval('users_id_seq', 6);
--================================================================
INSERT INTO "tagcloud"
---------------------------------------------------
("id", "classification" , "categories"  , "tagname"                      ) VALUES
---------------------------------------------------
(   1, '{10,47}'          ,  NULL         , 'Black Mens shoes'           ),
(   2, '{10,39}'          ,  NULL         , 'Mens natural leather boots' ),
(   3, '{20,36}'          ,  NULL         , 'Womens Puma sport shoes'    ),
(   4, NULL             ,  18           , 'Womens sport shoes'         ),
(   5, '{14,36}'          ,  NULL         , 'Mens Puma sport shoes'      ),
(   6, NULL             ,  11           , 'Mens sport shoes'           ),
(   7, '{16,57}'          ,  NULL         , 'Womens silver summer sandals'),
(   8, '{33,62}'          ,  NULL         , 'Womens cheap summer shoes'  ),
(   9, '{33,37}'          ,  NULL         , 'Mens Belwest summer shoes'  ),
(  10, '{12,37,62}'       ,  NULL         , 'Mens Belwest cheap sandals' );
-----------------------------------------------
SELECT setval('tagcloud_id_seq', 10);
--================================================================
INSERT INTO "items"
---------------------------------------------------
("id", "articul", "model" , "categoryid", "baseprice", "discount", "title",      
 "text", "name"      , "description", "classifications", "tagcloud","keywords") VALUES
---------------------------------------------------
  (1, '01505_01' ,  '01505'    ,'{7,30,36}'       , 60, 0, 'Mens shoes of natural leather 01505_01',
  'Modern allseason black mens shoes of natural leather 01505_01.', 'Mens shoes 01505_01',
  'Modern allseason black mens shoes of natural leather 01505_01', '{2,10,31,37,39,47,63,66,71}',
   '{1,2}','{"Mens shoes","Mens black shoes"}'),
   (2, '37284903' ,  '37284903' ,'{18,30,35,38}'   , 110, 0, 'Womens sneakers 37284903',
  'Modern womens sneakers 37284903. Puma sport shoes', 'Womens sneakers 37284903',
  'Modern womens sneakers 37284903. Puma sport shoes', '{3,20,31,36,42,60,64,66,71}',
   '{3,4}','{"Womens sneakers","womens sport shoes","Womens Puma sneakers"}'),
   (3, '37112005' , '37112005' ,'{11,30,35,37,38}' , 70, 25, 'Mens sneakers 37112005',
  'Modern black mens sneakers 37112005. Puma sport shoes', 'Mens sneakers 37112005',
  'Modern black mens sneakers 37112005. Puma sport shoes', '{2,14,31,36,42,47,63,66,68,71}',
   '{5,6}','{"Mens sneakers","Mens sport shoes","Mens Puma sneakers"}'),
   (4,'210348_350','210348_350','{14,32,34}' , 49, 10, 'Womens summer sandals 210348_350',
  'Modern silver womens summer sandals of natural leather 210348_350.', 'Womens sandals 210348_350',
  'Modern silver womens summer sandals of natural leather 210348_350.', '{3,16,33,35,39,57,62,67,71}',
   '{7,8}','{"Womens summer shoes","Womens sandals"}'),
   (5, '8918016'    ,  '8918016'  ,'{9,32,36}' , 35, 25, 'Mens summer sandals 8918016',
  'Modern brown mens summer sandals of natural leather 8918016.', 'Mens sandals 8918016',
  'Modern brown mens summer sandals of natural leather 8918016.', '{2,12,33,37,39,48,63,68}',
   '{9,10}','{"Mens summer shoes","Mens sandals"}'),
   (6, '8918016_black '    ,  '8918016_black '  ,'{9,32,36}' , 35, 25, 'Mens summer sandals 8918016_black ',
  'Modern black mens summer sandals of natural leather 8918016_black .', 'Mens sandals 8918016',
  'Modern black mens summer sandals of natural leather 8918016_black .', '{2,12,33,37,39,47,63,68,71}',
   '{9,10}','{"Mens summer shoes","Mens sandals"}');
-----------------------------------------------
SELECT setval('items_id_seq', 6);
--================================================================
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
("id", "itemid" , "variantid" , "quantity"   ) VALUES
---------------------------------------------------
(   1, 1          ,  '{7,17}' ,  10       ),
(   2, 1          ,  '{8,17}' ,  10       ),
(   3, 1          ,  '{9,17}' ,  10       ),
(   4, 2          ,  '{2,30}' ,  10       ),
(   5, 2          ,  '{3,30}' ,  10       ),
(   6, 2          ,  '{4,30}' ,  10       ),
(   7, 3          ,  '{7,17}' ,  10       ),
(   8, 3          ,  '{8,17}' ,  10       ),
(   9, 4          ,  '{2,27}' ,  10       ),
(  10, 4          ,  '{3,27}' ,  10       ),
(  11, 5          ,  '{7,18}' ,  10       ),
(  12, 5          ,  '{8,18}' ,  10       ),
(  13, 5          ,  '{9,18}' ,  10       ),
(  14, 6          ,  '{7,17}' ,  10       ),
(  15, 6          ,  '{8,17}' ,  10       ),
(  16, 6          ,  '{9,17}' ,  10       );
-----------------------------------------------
SELECT setval('baseitem_id_seq',16 );
--================================================================
INSERT INTO "client"
---------------------------------------------------
("id","countryid",  "address"  ,"creationdate","userid","bonuspoints","phoneno",
 "basket"    ,"orders" ,"sales"  ,"recentitems") VALUES
---------------------------------------------------
(1 , 1           ,'Address1'   , '2018-12-07' , 1      , 0           ,'+375(29) 123-45-67' ,
 '{1,2,3,4}'   ,'{1,2,3}'  ,'{1,2}'    ,'{1,2,3,4,5}' ),
(2 , 2           ,'Address2'   , '2018-12-07' , 2      , 0           ,'+375 (33) 987-65-43',
 '{6,5,4,3}'   ,'{6,5,4}'  ,'{5,6}'    ,'{6,2,3,4,5}' );
-----------------------------------------------
SELECT setval('client_id_seq',2 );
--================================================================
INSERT INTO "orders"
---------------------------------------------------
("id","number","datetime"        ,"dateexpired","baseitemid","customerid","quantity","sum",
 "currencyid","ordertype" ,"active"  ,"statussold") VALUES
---------------------------------------------------
(1 ,1  ,  '2019-12-07 14:30:00'  ,'2019-12-12' ,   1         , 1         , 1        , 60  ,
 1           , 1          ,    FALSE           ,   TRUE     ),
(2 ,1   ,  '2019-12-07 14:30:00'  ,'2019-12-12' ,   4         , 1         , 1        , 110 ,
 1           , 1          ,    FALSE           ,   TRUE     ),
(3 ,1   ,  '2019-12-07 14:30:00'  ,'2019-12-12' ,   7         , 1         , 1        , 70  ,
 1           , 1          ,    TRUE           ,  FALSE     ),
(4 ,2   ,  '2019-12-13 14:30:00'  ,'2019-12-18' ,    9        , 2         , 1        , 35  ,
 2           , 1          ,    FALSE           ,  FALSE     ),
(5 ,2   ,  '2019-12-13 14:30:00'  ,'2019-12-18' ,    11       , 2         , 1        , 35  ,
 2           , 1          ,    FALSE           ,   TRUE     ),
(6 ,2  ,  '2019-12-13 14:30:00'  ,'2019-12-18' ,    14       , 2         , 1        , 49  ,
 2           , 1          ,    FALSE           ,   TRUE     );
-----------------------------------------------
SELECT setval('orders_id_seq',6 );
--================================================================
INSERT INTO "webpages"
---------------------------------------------------
("id","title"           ,"description"                          ,"keywords"                             ,"h1"          ,
"text"                                        ,"categoryid","tagsource","tagcloud" ,"topmenu"  ,
"sidemenu","url") VALUES
---------------------------------------------------
(1   ,  'IShop'  ,'IShop - internet shopping testing' ,'{"internet shopping testing"}'                    ,'IShop'        ,
 'IShop - internet shopping testing'           , NULL      , NULL      ,NULL       , '{1,2,3}'   ,
    NULL  ,  ''  ),
(2   , 'Mens shoes'   ,'Modern mens shoes of famous brands'   ,'{"Mens shoes","Modern shoes for men"}','Mens shoes',
 'Modern mens shoes of famous brands'       , 2          , NULL      ,'{1,2,5,6,9}', '{1,2,3}'   ,
    NULL  ,   'mens shoes'  ),	
(3   , 'Womens shoes' ,'Modern womens shoes of famous brands' ,'{"Womens shoes","Modern shoes for women"}','Womens shoes',
 'Modern womens shoes of famous brands'     , 12         , NULL      ,'{3,4,7,8}'  , '{1,2,3}'   ,
    NULL  ,   'womens shoes'  );		
-----------------------------------------------
SELECT setval('webpages_id_seq',3 );
--================================================================
INSERT INTO "sale"
---------------------------------------------------
("id","datetime"                 ,"orderid","return","currencyid") VALUES
---------------------------------------------------
(1   , '2018-12-07 14:30:00' , 1       , FALSE  ,1           ),
(2   , '2018-12-07 14:30:00' , 2       , FALSE  ,1           ),
(3   , '2018-12-08 16:30:00' , 4       , FALSE  ,1           ),
(4   , '2018-12-08 16:30:00' , 5       , FALSE  ,1           );
--------------------------------------------------------------
SELECT setval('sale_id_seq',4 );
--================================================================