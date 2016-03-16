UltraDictionary allows you to have your own key/value store server.
Just copy the files on your server and use it.
UltraDictionary allows you to perform some callback call if a key is set.
Useful for implementing Cloud Messaging style applications.

== Tables ==

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

== Queries ==

<pre>
-- Example Insert
INSERT INTO tbl_ud (x_key, x_val) VALUES ('myKey', 'myValue')

-- Callback Insert: if myKey is modified, the website testsite is called.
INSERT INTO tbl_cb (x_key, x_cal) VALUES ('myKey', 'http://testsite.dev/aa_ud/UD.php?COM=SET&KEY=myKey&VAL=myValFromCallback');
</pre>

== URLs ==

<pre>
Url:= url?COM=[command]&KEY=[key]&VAL=[val]
	GET key		Output: value
	SET key=value	Output: value

http://testsite.dev/aa_ud/UD.php?COM=GET&KEY=myKey

http://testsite.dev/aa_ud/UD.php?COM=SET&KEY=myKey&VAL=myValue
</pre>
