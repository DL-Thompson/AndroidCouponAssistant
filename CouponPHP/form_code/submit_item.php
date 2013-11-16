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

if($user_id !== false) {
    //If login was successful, try to post the item.
    $item_id = post_item($barcode);
    //Redo to check if item exists.
    if ($item_id !== false) {
        response_success("Item posted!");
    }
    else {
        response_error("Item posting failed.");
    }
}


?>


