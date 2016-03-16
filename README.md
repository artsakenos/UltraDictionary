# UltraDictionary

UltraDictionary allows you to have your own key/value store server and 
to perform some callback call if a key is set, 
useful for implementing Cloud Messaging style applications. 
Php + Mysqli is supported.

Just copy the files on your server and follow the instructions.

# Installation

Copy the file on your server, e.g., in http://testsite.dev/aa_ud/*

## Create Tables on your db

Configure the headings variables of UD.php, and call the INIT command:

http://testsite.dev/aa_ud/COM=INIT


## Perform Key Value Queries by http

<pre>
The query syntax is:
Url:= url?COM=[command]&KEY=[key]&VAL=[val]
Get a Value	GET key		Output: val
Set a Value	SET key=val	Output: val
Init DB         INIT            Output: successful state.
Set a Callback  CAL key=val     Output: successful state.

Examples:

-- Set a myKey = myValue
http://testsite.dev/aa_ud/UD.php?COM=SET&KEY=myKey&VAL=myValue

-- Get the value of myKey 
http://testsite.dev/aa_ud/UD.php?COM=GET&KEY=myKey

</pre>

# To Do

* Avoid loop with callbacks
* Avoid code injection
* Parametrical Callback

## Example queries from mysql

<pre>
-- Example Insert
INSERT INTO tbl_ud (x_key, x_val) VALUES ('myKey', 'myValue')

-- Callback Insert: if myKey is modified, the website testsite is called.
INSERT INTO tbl_cb (x_key, x_cal) VALUES ('myKey', 'http://testsite.dev/aa_ud/UD.php?COM=SET&KEY=myKey&VAL=myValFromCallback');
</pre>

