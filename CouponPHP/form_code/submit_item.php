<?php

require_once '../core/init.php';

<<<<<<< HEAD
// to be sure all required input is not blank
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
    //If login was successful, try to post the item.
    $item_id = post_item($barcode);
    if ($item_id !== false) {
        response_success("Item posted!");
    } else {
        response_error("Item posting failed.");
    }
}
=======
$user_id = login($_POST);
if($user_id !== false) {
    //If login was successful, try to post the item.
    $item_id = post_item($_POST);
    //Redo to check if item exists.
    if ($item_id !== false) {
        json_post(1, "Item posted.");
    }
    else {
        json_post(0, "Item posting failed.");
    }
}


>>>>>>> master
?>


