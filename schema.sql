CREATE DATABASE IF NOT EXISTS totalbuy 
    DEFAULT CHARACTER SET=utf8;
COMMIT;

USE totalbuy;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;

CREATE TABLE  customers (
  id char(10) NOT NULL,
  name varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  gender char(1) NOT NULL DEFAULT 'M',
  email varchar(40) NOT NULL,
  birth_date date DEFAULT NULL,
  address varchar(120) DEFAULT NULL,
  phone varchar(20) DEFAULT NULL,
  married tinyint(1) NOT NULL DEFAULT '0',
  blood_type varchar(2) DEFAULT NULL,
  status int(2) NOT NULL DEFAULT '0',
  discount int(2) NOT NULL DEFAULT '0',
  type varchar(20) NOT NULL DEFAULT 'Customer',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
COMMIT;

INSERT INTO customers
(id, name, password, gender, email) VALUES('A123456789', '張三風', '123456', 'M', 'test@uuu.com.tw');

INSERT INTO customers
(id, name, password, gender, email,birth_date, address, phone,married, blood_type, status, discount, type) 
VALUES('A223456781', '林梅麗', '123456', 'F', 'mary@uuu.com.tw', '1975-4-5', 
    '台北市復興北路99號', '0225149191',false, 'AB', 1, 15, 'VIP');
COMMIT;

SELECT * FROM customers;

CREATE TABLE  products (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  unit_price double NOT NULL DEFAULT '0',
  free tinyint(1) NOT NULL DEFAULT '0',
  stock int(6) unsigned NOT NULL DEFAULT '0',
  description varchar(150) DEFAULT NULL,
  url varchar(100) DEFAULT NULL,
  status int(2) unsigned NOT NULL DEFAULT '1',
  discount int(2) unsigned NOT NULL DEFAULT '0',
  type varchar(15) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
COMMIT;

TRUNCATE TABLE products;

INSERT INTO products (name, unit_price, free, stock)
VALUES('HTC Desire 826', 8300, false, 10);

INSERT INTO products (name, unit_price, free, stock, url, description)
VALUES('HTC One M9+', 17860, false, 10, 'http://cf-attach.i-sogi.com/tw/product/img/hTC_One_M9_0408092708736_360x270.jpg', 
'5.2 吋 2K Quad HD 觸控螢幕、2,560 × 1,440pixels 螢幕解析度, 採用 Android 5.0 Lollipop 作業系統 + HTC Sense 7 操作介面');

INSERT INTO products (name, unit_price, free, stock)
VALUES('Apple iPhone 6 Plus 64GB', 26000, false, 10);

INSERT INTO products (name, unit_price, free, stock, url, description, discount, type)
VALUES('Apple iPhone 5S 64GB', 21000, false, 5, 'http://sogi-image.sogi.com.tw/www/Product/10223/main_image/big/10223.jpg', 
'4 吋（對角線）寬螢幕 Multi-Touch 觸控螢幕、1,136 x 640pixels 螢幕解析度、326ppi',30, 'Outlet');

SELECT * FROM products;
COMMIT;

CREATE TABLE  orders (
  customer_id char(10) NOT NULL,
  order_time datetime NOT NULL,
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  payment_type int(2) unsigned NOT NULL,
  payment_amount double DEFAULT '0',
  payment_note varchar(40) DEFAULT NULL,
  shipping_type int(2) unsigned NOT NULL,
  shipping_amount double DEFAULT '0',
  shipping_note varchar(40) DEFAULT NULL,
  receiver_name varchar(20) NOT NULL,
  receiver_email varchar(40) NOT NULL,
  receiver_phone varchar(20) NOT NULL,
  shipping_address varchar(120) NOT NULL,
  status int(2) unsigned NOT NULL DEFAULT '0',
  bad_status int(2) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  FOREIGN KEY FK_orders_customers (customer_id)
    REFERENCES customers (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
commit;


CREATE TABLE  order_items (
  order_id int(10) unsigned NOT NULL,
  product_id int(10) unsigned NOT NULL,
  quantity int(3) unsigned NOT NULL,
  price double NOT NULL,
  free tinyint(1) DEFAULT '0',
  PRIMARY KEY (order_id,product_id),
  KEY FK_order_items_products (product_id),
  CONSTRAINT FK_order_items_orders FOREIGN KEY (order_id) REFERENCES orders (id),
  CONSTRAINT FK_order_items_products FOREIGN KEY (product_id) REFERENCES products (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
commit;