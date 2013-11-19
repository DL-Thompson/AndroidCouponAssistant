<?php

require_once '../core/init.php';

<<<<<<< HEAD
//Check to be sure all required input is not blank
$username = $_POST['username'];
$password = $_POST['password'];
$item_barcode = $_POST['item_barcode'];
$coupon_barcode = $_POST['coupon_barcode'];
$exp_date = $_POST['exp_date'];
if (empty($username)) {
    response_error("Username field left blank.");
} elseif (empty($password)) {
    response_error("Password field left blank.");
} elseif (empty($item_barcode)) {
    response_error("Item barcode field left blank.");
} elseif (empty($coupon_barcode)) {
    response_error("Coupon barcode field left blank.");
} elseif (empty($exp_date)) {
    response_error("Expiration date field left blank.");
} elseif (strlen($item_barcode) != 12) {
    response_error("Item barcode must be 12 digits.");
} elseif (strlen($coupon_barcode) != 12) {
    response_error("Coupon barcode must be 12 digits.");
}

//Check to be sure the user is valid
$user_id = login($username, $password);
if ($user_id !== false) {
    //If login was successful, be sure coupon/item exists
    $item_id = item_exists($item_barcode);
    $coupon_id = coupon_exists($coupon_barcode, $exp_date);

    //If coupon/item combo exists, it can be submitted to the purchased table
    if ($item_id !== false && $coupon_id !== false) {
        $purchased = purchase($user_id, $item_id, $coupon_id);
        if ($purchased === true) {
            //Bought relationship was posted successfully.
            response_success("Item purchased successfully.");
        } else {
            //Return the failure message via JSON to the android device.
            response_error("Item purchase failed.");
        }
    } else {
        if ($item_id === false) {
            response_error("Item not in database.");
        } elseif ($coupon_id === false) {
            response_error("Coupon not in database.");
        }
    }
}
=======
$user_id = login($_POST);
if($user_id !== false) {
    //If login was successful, try to post the item.
    $item_id = post_item($_POST);
    //Redo to check if item exists.
    if ($item_id !== false) {
        //If item was posted successfully, post the submitted relationship.
        $purchased = purchase($_POST, $user_id, $item_id);
        if ($purchased === true) {
            //Bought relationship was posted successfully.
            //Return the info via JSON to the android device.
            json_post(1, "Item purchased.");
        }
        else {
            //Return the failure message via JSON to the android device.
            json_post(0, "Item purchase failed.");
        }
    }
}


>>>>>>> master
?>

