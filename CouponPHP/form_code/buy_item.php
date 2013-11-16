<?php

require_once '../core/init.php';

//Check for correct input
$username = $_POST['username'];
$password = $_POST['password'];
$item_barcode = $_POST['item_barcode'];
$coupon_barcode = $_POST['coupon_barcode'];
$exp_date = $_POST['exp_date'];
if (empty($username)) {
    response_error("Username field left blank.");
}
elseif (empty($password)) {
    response_error("Password field left blank.");
}
elseif (empty($item_barcode)) {
    response_error("Item barcode field left blank.");
}
elseif (empty($coupon_barcode)) {
    response_error("Coupon barcode field left blank.");
}
elseif (empty($exp_date)) {
    response_error("Expiration date field left blank.");
}

$user_id = login($username, $password);
if($user_id !== false) {
    //If login was successful, be sure coupon/item exists
    $item_id = item_exists($item_barcode);
    $coupon_id = coupon_exists($coupon_barcode, $exp_date);
    
    //If coupon/item combo exists, it can be submitted to the purchased table
    if ($item_id !== false && $coupon_id !== false) {
        $purchased = purchase($user_id, $item_id, $coupon_id);
        if ($purchased === true) {
            //Bought relationship was posted successfully.
            response_success("Item purchased successfully.");
        }
        else {
            //Return the failure message via JSON to the android device.
            response_error("Item purchase failed.");
        }
    }
}


?>

