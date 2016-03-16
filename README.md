# UltraDictionary

UltraDictionary allows you to have your own key/value store server and to perform some callback call if a key is set, useful for implementing Cloud Messaging style applications. Php + Mysqli is supported.

Just copy the files on your server and follow the instructions.

# Installation

Copy the file on your server, e.g., in http://testsite.dev/aa_ud/*

## Create Tables on your db

<pre>
-- Table UltraDictionary
CREATE TABLE tbl_ud (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  x_key VARCHAR(100) NOT NULL DEFAULT '',
  x_val VARCHAR(255) NOT NULL DEFAULT '',
  x_dat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)

-- Table CallBack
CREATE TABLE tbl_cb (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  x_key VARCHAR(100) NOT NULL DEFAULT '',
  x_cal VARCHAR(255) NOT NULL DEFAULT '',
  x_dat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
</pre>

## Perform some queries from mysql

<pre>
-- Example Insert
INSERT INTO tbl_ud (x_key, x_val) VALUES ('myKey', 'myValue')

-- Callback Insert: if myKey is modified, the website testsite is called.
INSERT INTO tbl_cb (x_key, x_cal) VALUES ('myKey', 'http://testsite.dev/aa_ud/UD.php?COM=SET&KEY=myKey&VAL=myValFromCallback');
</pre>

## Perform Key Value Queries by http

<pre>
The query syntax is:
Url:= url?COM=[command]&KEY=[key]&VAL=[val]
	GET key		Output: value
	SET key=value	Output: value

Examples:

-- Set a myKey = myValue
http://testsite.dev/aa_ud/UD.php?COM=SET&KEY=myKey&VAL=myValue

-- Get the value of myKey 
http://testsite.dev/aa_ud/UD.php?COM=GET&KEY=myKey

</pre>

# To Do

* Avoid loop with callbacks
* Avoid code injection

