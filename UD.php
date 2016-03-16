<?php

// --------------- CONFIGURATION

$servername = "localhost";
$username = "root";
$password = "drupalpro";

$db_name = "testsite_dev";
$db_table_ud = "tbl_ud";
$db_table_cb = "tbl_cb";

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
$output = "";
$sql = "";

///-{ Eseguiamo la query.
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
    $sql = "INSERT INTO $db_table_ud (x_key, x_val) VALUES ('$ud_key', '$ud_val')";
    $result = $conn->query($sql);
    if ($result !== TRUE) {
        $output .= "Error: " . $sql . "<br>" . $conn->error;
    } else {
        $output .= $ud_val;
    }

    // Search for known callbacks
    $sql = "SELECT * FROM $db_table_cb WHERE x_key='$ud_key'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $cb_url = $row["x_cal"];
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

///-{ Show the output.
echo $output;

$conn->close();
