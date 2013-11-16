<?php

require_once '../core/init.php';

//Check to be sure all required input is not blank
$username = $_POST['username'];
$password = $_POST['password'];
$barcode = $_POST['barcode'];
if (empty($username)) {
    response_error("Username field left blank.");
} elseif (empty($password)) {
    response_error("Password field left blank.");
} elseif (empty($barcode)) {
    response_error("Barcode field left blank.");
} elseif (strlen($barcode) != 12) {
    response_error("Barcode must be 12 digits.");
}

//Check to be sure the user is valid
$user_id = login($username, $password);
if ($user_id !== false) {
    //If login was successful, search the coupons for matching bacodes.
    //The query function handle the JSON echo.
    $coupons = query_coupons($barcode);
    if ($coupons !== false) {
        echo json_encode($coupons);
    } else {
        response_error("No matching coupons found.");
    }
}
?>