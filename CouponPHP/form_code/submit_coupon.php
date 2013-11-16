<?php

require_once '../core/init.php';

//Check for correct input
$username = $_POST['username'];
$password = $_POST['password'];
$barcode = $_POST['barcode'];
$exp_date = $_POST['exp_date'];
$image_blob = $_POST['image_blob'];
if (empty($username)) {
    response_error("Username field left blank.");
}
elseif (empty($password)) {
    response_error("Password field left blank.");
}
elseif (empty($barcode)) {
    response_error("Barcode field left blank.");
}
elseif (empty($exp_date)) {
    response_error("Expiration date field left blank.");
}
elseif (empty($image_blob)) {
    response_error("Image blob field left blank.");
}


$user_id = login($username, $password);
if($user_id !== false) {
    //If login was successful, try to post the coupon.
    $coupon_id = post_coupon($barcode, $exp_date, $image_blob);
    //Redo to check if coupon exists.
    if ($coupon_id !== false) {
        //If coupon was posted successfully, post the submitted relationship.
        $submitted = submit($user_id, $coupon_id);
        if ($submitted === true) {
            //Submitted by relationship was posted successfully.
            //Return the info via JSON to the android device.
            response_success("Coupon submitted!");
        }
        else {
            //Inserting into the Submitted table failed, the coupon should be
            //removed here if we decide to.
            //Return the failure message via JSON to the android device.
            response_error("Coupon Submitted by failed.");
        }
    }
}


?>

