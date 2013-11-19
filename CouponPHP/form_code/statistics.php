<?php

require_once '../core/init.php';

//Check to be sure all required input is not blank
$username = $_POST['username'];
$password = $_POST['password'];
if (empty($username)) {
    response_error("Username field left blank.");
} elseif (empty($password)) {
    response_error("Password field left blank.");
}

//Check to be sure the user is valid
$user_id = login($username, $password);
if ($user_id !== false) {
    //If login was successful, get the user statstics.
    //Get count of coupons submitted by a user.
    $coupon_post_count = get_user_coupon_count_by_day($user_id, 0);
    $coupon_post_count_day = get_user_coupon_count_by_day($user_id, 1);
    $coupon_post_count_week = get_user_coupon_count_by_day($user_id, 7);
    $coupon_post_count_month = get_user_coupon_count_by_day($user_id, 30);
    $coupon_post_count_year = get_user_coupon_count_by_day($user_id, 365);

    //Get list of coupons submitted since days listed
    //Not completed on front end yet
    //$coupon_list_all = get_user_coupon_submitted($user_id);
    //$coupon_list_day = get_user_coupon_submitted_by_day($user_id, 1);
    //$coupon_list_week = get_user_coupon_submitted_by_day($user_id, 7);
    //$coupon_list_month = get_user_coupon_submitted_by_day($user_id, 30);
    //$coupon_list_year = get_user_coupon_submitted_by_day($user_id, 365);
    //Get count of items bought and coupons used
    $item_bought_count = get_items_bought_count($user_id);

    //Encode statistics into JSON
    $response["success"] = 1;
    $response["message"] = "Statistics found!";
    $response["count_bought"] = $item_bought_count;
    $response["count_total"] = $coupon_post_count;
    $response["count_day"] = $coupon_post_count_day;
    $response["count_week"] = $coupon_post_count_week;
    $response["count_month"] = $coupon_post_count_month;
    $response["count_year"] = $coupon_post_count_year;

    //Encode coupon lists into JSON
    //If no coupons are found, a String is returned with a fail message.
    //Not completed on front end yet.
    //$response["coupon_total"] = $coupon_list_all;
    //$response["coupon_day"] = $coupon_list_day;
    //$response["coupon_week"] = $coupon_list_week;
    //$reponse["coupon_month"] = $coupon_list_month;
    //$response["coupon_year"] = $coupon_list_year;

    echo json_encode($response);
}
?>