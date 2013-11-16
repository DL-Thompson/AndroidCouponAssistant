<?php

require_once '../core/init.php';

//Check for correct input
$username = $_POST['username'];
$password = $_POST['password'];
$barcode = $_POST['barcode'];
if (empty($username)) {
    response_error("Username field left blank.");
}
elseif (empty($password)) {
    response_error("Password field left blank.");
}
elseif (empty($barcode)) {
    response_error("Barcode field left blank.");
}

$user_id = login($username, $password);

if ($user_id !== false) {
    //If login was successful, try to search the coupons for the barcode
    //and print the json list if successful
    query_items($barcode);
}
?>