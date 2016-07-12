<?php

include_once 'settings.php';

$conn = new mysqli($servername, $username, $password, $db_name);
if ($conn->connect_error) {
    die("Connection to Server failed: " . $conn->connect_error);
}

// ---------------- Main Variables
// filter_input(INPUT_POST, 'var_name') instead of $_POST['var_name']
// $ud_com = $_GET["COM"]; // ... Ma li filtro.
$ud_com = filter_input(INPUT_GET, 'COM');
$ud_key = filter_input(INPUT_GET, 'KEY');
$ud_val = filter_input(INPUT_GET, 'VAL');
$ud_tok = filter_input(INPUT_GET, 'TOK');
$output = "";
$sql = "";

// --------------- Check if Command was given, and Token is correct
if (!isset($ud_com)) {
    echo "ERROR. UltraDictionary, no commands was given.";
    $conn->close();
    exit();
}

if (!empty($api_token) && $ud_tok !== $api_token) {
    echo "ERROR. Please send the correct API Token for this UltraDictionary instance.";
    $conn->close();
    exit();
}


if ($ud_com === 'GET') {
    // Prendiamo l'ultimo salvato se esiste
    $sql = "SELECT * FROM $db_table_ud WHERE x_key='$ud_key' ORDER BY id DESC LIMIT 1";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $output .= $row["x_val"] . "\n";
        }
    }
}

if ($ud_com === 'SET') {
    $ud_val_s = $conn->real_escape_string($ud_val);
    $sql = "INSERT INTO $db_table_ud (x_key, x_val) VALUES ('$ud_key', '$ud_val_s')";
    $result = $conn->query($sql);
    if ($result !== TRUE) {
        $output .= "ERROR. " . $sql . "<br>" . $conn->error;
    } else {
        $output .= $ud_val;
    }

    // Search for known callbacks
    $sql = "SELECT * FROM $db_table_cb WHERE x_key='$ud_key'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $cb_url = $row["x_val"];
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $cb_url);
            //curl_setopt($ch, CURLOPT_POST, 1);// set post data to true
            //curl_setopt($ch, CURLOPT_POSTFIELDS,"data=mydata&foo=bar");// post data
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            $response = curl_exec($ch); // response it ouputed in the response var
            curl_close($ch); // close curl connection
        }
    }
}

if ($ud_com === 'CAL') {
    $sql = "INSERT INTO $db_table_cb (x_key, x_val) VALUES ('$ud_key', '$ud_val')";
    $result = $conn->query($sql);
    if ($result !== TRUE) {
        $output .= "ERROR. " . $sql . "<br>" . $conn->error;
    } else {
        $output .= $ud_val;
    }
}

if ($ud_com === 'INI') {

    $sql = "DROP TABLE IF EXISTS $db_table_ud";
    $conn->query($sql);

    $sql = "CREATE TABLE $db_table_ud (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    x_key VARCHAR(100) NOT NULL DEFAULT '',
    x_val VARCHAR(255) NOT NULL DEFAULT '',
    x_dat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )";

    if ($conn->query($sql) === TRUE) {
        echo "Table $db_table_ud created successfully.<br />";
    } else {
        echo "ERROR. Cannot create table: " . $conn->error;
    }

    $sql = "DROP TABLE IF EXISTS $db_table_cb";
    $conn->query($sql);

    $sql = "CREATE TABLE $db_table_cb (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    x_key VARCHAR(100) NOT NULL DEFAULT '',
    x_val VARCHAR(255) NOT NULL DEFAULT '',
    x_dat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )";

    if ($conn->query($sql) === TRUE) {
        echo "Table $db_table_cb created successfully.<br />";
    } else {
        echo "ERROR. Cannot create table: " . $conn->error;
    }
}

if ($ud_com === 'LIS') {
    $output = "Table UltraDictionary: $db_table_ud <br  />\n";
    $sql = "SELECT * FROM $db_table_ud";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $output .= "[" . $row['x_dat'] . "] " . $row['x_key'] . " := " . $row['x_val'] . "<br />\n";
        }
    }

    $output .= "Table CallBacks: $db_table_cb <br  />\n";
    $sql = "SELECT * FROM $db_table_cb";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $output .= $row["x_val"] . "\n";
        }
    }
}

///-{ Show the output and close the connection.
echo $output;
$conn->close();
