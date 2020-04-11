DROP TABLE IF EXISTS "variant";
CREATE TABLE "variant" (
	"id" SERIAL PRIMARY KEY,
--	"longname" VARCHAR(100) NOT NULL UNIQUE,
	"name" VARCHAR(50) NOT NULL UNIQUE,	
	"parentid" INTEGER NULL REFERENCES "variant" ON DELETE RESTRICT ON UPDATE CASCADE
);
-------------------------------------------------------------------------
DROP TABLE IF EXISTS "category";
CREATE TABLE "category" (
	"id" SERIAL PRIMARY KEY,
	"parentid" INTEGER NULL REFERENCES "category" ON DELETE RESTRICT ON UPDATE CASCADE, 
--	"longname" VARCHAR(100) NOT NULL,
	"name" VARCHAR(50) NOT NULL	
);
-------------------------------------------------------------------------
DROP TABLE IF EXISTS "classification";
CREATE TABLE "classification" (
	"id" SERIAL PRIMARY KEY,
	"name" VARCHAR(50) NOT NULL UNIQUE,	
	"parentid" INTEGER NULL REFERENCES "classification" ON DELETE RESTRICT ON UPDATE CASCADE,
	"categoryid" INTEGER NULL REFERENCES "category" ON DELETE RESTRICT ON UPDATE CASCADE
);
--------------------------------------------------
DROP TABLE IF EXISTS "items";
CREATE TABLE "items" (
	"id" SERIAL PRIMARY KEY,
	"articul" VARCHAR(20) NOT NULL, 
	"model" VARCHAR(20) NOT NULL,
	"categoryid" INTEGER[] NOT NULL,
	"baseprice" MONEY NOT NULL,
	"discount" INTEGER NULL,
	"title" VARCHAR(150) NULL,
	"text" TEXT NULL,
	"name" VARCHAR(100) NULL,
	"description" VARCHAR(250) NULL,
	"classifications" INTEGER[] NOT NULL,
    "tagcloud" INTEGER[] NULL,
	"keywords" VARCHAR(50)[] NULL,
	"url" VARCHAR(200) NULL UNIQUE
);
------------------------------------
DROP TABLE IF EXISTS "baseitem";
CREATE TABLE "baseitem" (
	"id" SERIAL PRIMARY KEY,
	"itemid" INTEGER NOT NULL REFERENCES "items" ON DELETE CASCADE ON UPDATE	CASCADE,
	"variantid" INTEGER[] NOT NULL,
	"name" VARCHAR(20) NULL UNIQUE,
	UNIQUE ("itemid", "variantid")                                                                               
);
-------------------------------------------------------------------------
--CREATE SEQUENCE "country_id_sequence" START 100;
-- 0 USD -- 1 EUR
DROP TABLE IF EXISTS "country";
CREATE TABLE "country" (
	"id" SERIAL PRIMARY KEY,	
	"name"  VARCHAR(20) NOT NULL, 	 
    "currency" VARCHAR(3) NOT NULL,
	"rate" REAL NOT NULL
);
--ALTER SEQUENCE "country_id_sequence" OWNED BY "country"."id";
------------------------------------
DROP TABLE IF EXISTS "stocks";
CREATE TABLE "stocks" (
	"id" SERIAL PRIMARY KEY,
	"baseitemid" INTEGER NOT NULL REFERENCES "baseitem" ON DELETE CASCADE ON UPDATE	CASCADE,
	"quantity" INTEGER NOT NULL,
	"unitname" VARCHAR(20) NULL UNIQUE,
	"baseprice" MONEY NULL,
     "currency" INTEGER NOT NULL DEFAULT 1 REFERENCES "country" ON DELETE CASCADE ON UPDATE	CASCADE                                                                        
);
-------------------------------------------------------------------------
DROP TABLE IF EXISTS "users";
CREATE TABLE "users" (
	"id" SERIAL PRIMARY KEY,
	"name" VARCHAR(100) NULL, 	
	"password" CHAR(128) NOT NULL,
    "email" VARCHAR(40) NOT NULL,
	"roleid" SMALLINT NOT NULL CHECK ("roleid" IN (0, 1, 2, 3, 4))	
	/*
	 * 0 - ADMIN
	 * 1 - CLIENT
	 * 2 - PRODUCT MANAGER
	 * 3 - MANAGER
	 * 4 - CASHIER
	 */
);

-------------------------------------------------------------------------
DROP TABLE IF EXISTS "client";
CREATE TABLE "client" (
	"id" SERIAL PRIMARY KEY,
--	"name" VARCHAR(100) NULL, 
	"countryid" INTEGER NOT NULL REFERENCES "country" ON DELETE RESTRICT ON UPDATE RESTRICT,
	"address" VARCHAR(150) NULL,
	"creationdate" DATE NOT NULL DEFAULT CURRENT_DATE,	
	"userid" INTEGER NOT NULL REFERENCES "users" ON DELETE CASCADE ON UPDATE CASCADE,
	"bonuspoints" INTEGER NULL,
	"phoneno" VARCHAR(20) NULL,
	"basket" INTEGER[] NULL,
	"orders" INTEGER[] NULL,
	"sales" INTEGER[] NULL,	
	"recentitems" INTEGER[] NULL	
);
-------------------------------------------------------------------------
--CREATE TYPE ordertype AS ENUM ('1-delivery to home', '2 - pickup', '3- delivery by post');
DROP TABLE IF EXISTS "orders";
CREATE TABLE "orders" (
	"id" SERIAL PRIMARY KEY,
	"number" INTEGER NOT NULL,
	"date" TIMESTAMP NOT NULL,
	"dateexpired" DATE NOT NULL,
	"baseitemid" INTEGER NOT NULL REFERENCES "baseitem" ON DELETE RESTRICT ON UPDATE RESTRICT,
	"customerid" INTEGER NOT NULL REFERENCES "client" ON DELETE RESTRICT ON UPDATE CASCADE,
	"quantity" INTEGER NOT NULL,
	"sum" MONEY NOT NULL,
	"currencyid" INTEGER NOT NULL REFERENCES "country" ON DELETE RESTRICT ON UPDATE RESTRICT,
	"ordertype" INTEGER NOT NULL DEFAULT 2,
    "rejectionreason" VARCHAR(50) NULL, 
	"statussold" BOOLEAN NOT NULL DEFAULT FALSE
	CHECK ("dateexpired" > "date")
);
-------------------------------------------------------------------------
DROP TABLE IF EXISTS "tagcloud";
CREATE TABLE "tagcloud" (
	"id" SERIAL PRIMARY KEY,
	"classification" INTEGER[] NULL,
	"categories" INTEGER NULL REFERENCES "category" ON UPDATE CASCADE,	
    "tagname" VARCHAR(100),    
	"url" VARCHAR(200) NULL UNIQUE
);
------------------------------------
CREATE TYPE robotstype AS ENUM ('index,follow', 'noindex,nofollow', 'noindex,follow');
DROP TABLE IF EXISTS "webpages";
CREATE TABLE "webpages" (
	"id" SERIAL PRIMARY KEY,
	"url" VARCHAR(200) NULL UNIQUE, 
	"title" VARCHAR(150) NULL UNIQUE,
	"description" VARCHAR(250) NULL,
	"keywords" VARCHAR(50)[] NULL,
	"h1" VARCHAR(100) NULL UNIQUE,
	"text" TEXT NULL,
	"categoryid" INTEGER NULL REFERENCES "category" ON UPDATE CASCADE,
	"tagsource" INTEGER NULL REFERENCES "tagcloud" ON UPDATE CASCADE,
	"tagcloud" INTEGER[] NULL,
	"topmenu" INTEGER[] NOT NULL,
    "sidemenu" INTEGER[] NULL,
	"robots" robotstype NOT NULL DEFAULT 'index,follow'
);
--------------------------------------------------------------------
DROP TABLE IF EXISTS "sale";
CREATE TABLE "sale" (
	"id" SERIAL PRIMARY KEY,
	"date" TIMESTAMP NOT NULL,
	"orderid" INTEGER NOT NULL REFERENCES "orders" ON DELETE RESTRICT ON UPDATE CASCADE,	
    "return" BOOLEAN NOT NULL DEFAULT FALSE,
    "currencyid" INTEGER NOT NULL REFERENCES "country" ON DELETE RESTRICT ON UPDATE RESTRICT
);
-------------------------------------------------------------------------
--INSERT INTO "country" ("id", "name", "currency","rate") VALUES (1, 'USA', 'usd',1)