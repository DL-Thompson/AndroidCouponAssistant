<?php

require_once '../core/init.php';

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
            response_error("Coupon Submitted by failed.");
        }
    }
}


?>

