# UltraDictionary

UltraDictionary allows you to have your own key/value store server and 
to perform some callback call when a key is set, 
useful for implementing Cloud Messaging style applications. 
Php + Mysqli is supported.

## Installation
Download and copy the files e.g., in `http://drupatest.dev/aa_ud/*` or

`git clone https://github.com/artsakenos/UltraDictionary.git aa_ud`

Configure the file _settings.php_ of _UltraDictionary_, and you're done.

## Query Syntax

The query is a Command with some optional parameters:
<pre>
Url:= url?COM=[GET|SET|INI|CAL|LIS]&KEY=[key]&VAL=[value]&TOK=[token]
</pre>

The query parameters are:
<pre>
COM     The command, mandatory.
    GET     GET a value given the KEY.
            Output := the value.
    SET     SET a value given the KEY and the VAL
            Output := the value set.
    INI     Setup the DBs
            Output := the result of the operation.
    CAL     SET a Callback for the KEY with VAL (the callback url)
            Output := the result of the operation.
    LIS     List all the variables and callbacks
            Output := the list of all the variables.
KEY     The Key
VAL     The Value (or the callback in the callback command)
TOK     The Token, mandatory if not left empty on the settings
</pre>

## Setup and Use Ultradictionary

After the setup of all the variables in `settings.php`, call the _INIT_ command:
<pre>
http://drupatest.dev/aa_ud/?COM=INIT
</pre>

## Perform Command Queries via http

Some Examples:
<pre>
-- Set a myKey = myValue
http://drupatest.dev/aa_ud/?COM=SET&KEY=myKey&VAL=myValue

-- Get the value of myKey 
http://drupatest.dev/aa_ud/?COM=GET&KEY=myKey
</pre>

## To Do

* Avoid loop with callbacks
* Avoid code injection
* Parametrical Callback

## Example queries from mysql

<pre>
-- Example Insert
INSERT INTO tbl_ud (x_key, x_val) VALUES ('myKey', 'myValue')

-- Callback Insert: if myKey is modified, the website drupatest is called.
INSERT INTO tbl_cb (x_key, x_cal) VALUES ('myKey', 'http://drupatest.dev/aa_ud/?COM=SET&KEY=myKey&VAL=myValFromCallback');
</pre>

## Java Client
The repository contains a Netbeans project for a Java client with an callback server.
