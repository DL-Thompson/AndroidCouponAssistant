<?php

require_once '../core/init.php';

<<<<<<< HEAD
//Check to be sure all required input is not blank
$username = $_POST['username'];
$password = $_POST['password'];
$barcode = $_POST['barcode'];
$exp_date = $_POST['exp_date'];
$image_blob = $_POST['image_blob'];
if (empty($username)) {
    response_error("Username field left blank.");
} elseif (empty($password)) {
    response_error("Password field left blank.");
} elseif (empty($barcode)) {
    response_error("Barcode field left blank.");
} elseif (empty($exp_date)) {
    response_error("Expiration date field left blank.");
} elseif (empty($image_blob)) {
    response_error("Image blob field left blank.");
} elseif (strlen($barcode) != 12) {
    response_error("Barcode must be 12 digits.");
}

//Check to be sure the user is valid
$user_id = login($username, $password);
if ($user_id !== false) {
    //If login was successful, try to post the coupon.
    $coupon_id = post_coupon($barcode, $exp_date, $image_blob);
    if ($coupon_id !== false) {
        //If coupon was posted successfully, post the coupon submitted by relationship.
        $submitted = submit($user_id, $coupon_id);
        if ($submitted === true) {
            //Submitted by relationship was posted successfully.
            response_success("Coupon submitted!");
        } else {
            //Inserting into the Submitted table failed. Coupon will still be
            //stored in the database for other users to use, so it won't be removed.
=======
$user_id = login($_POST);
if($user_id !== false) {
    //If login was successful, try to post the coupon.
    $coupon_id = post_coupon($_POST);
    //Redo to check if coupon exists.
    if ($coupon_id !== false) {
        //If coupon was posted successfully, post the submitted relationship.
        $submitted = submit($_POST, $user_id, $coupon_id);
        if ($submitted === true) {
            //Submitted by relationship was posted successfully.
            //Return the info via JSON to the android device.
            json_post(1, "Coupon submitted.");
        }
        else {
            //Inserting into the Submitted table failed, the coupon should be
            //removed here if we decide to.
            //Return the failure message via JSON to the android device.
>>>>>>> master
            response_error("Coupon Submitted by failed.");
        }
    }
}
<<<<<<< HEAD
=======


>>>>>>> master
?>

