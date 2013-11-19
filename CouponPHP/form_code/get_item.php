<?php

require_once '../core/init.php';

<<<<<<< HEAD
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
    //If login was successful, search the items for matching bacodes.
    $items = query_items($barcode);
    if ($items !== false) {
        echo json_encode($items);
    } else {
        response_error("No matching items found.");
    }
=======
$user_id = login($_POST);

if ($user_id !== false) {
    //If login was successful, try to search the coupons for the barcode
    //and print the json list if successful
    query_items($_POST);
>>>>>>> master
}
?>