<?php

require_once '../core/init.php';

$user_id = login($_POST);

if ($user_id !== false) {
    //If login was successful, get the user statstics.
    
    //Get count of coupons submitted by a user.
    $coupon_post_count = get_user_coupon_count($user_id);
    $coupon_post_count_day = get_user_coupon_count_by_day($user_id, 1);
    $coupon_post_count_week = get_user_coupon_count_by_day($user_id, 7);
    $coupon_post_count_month = get_user_coupon_count_by_day($user_id, 30);
    $coupon_post_count_year = get_user_coupon_count_by_day($user_id, 365);
    
    //Encode statistics into JSON and return
    $response["success"] = 1;
    $response["message"] = "Statistics found!";
    $response["count_total"] = $coupon_post_count;
    $response["count_day"] = $coupon_post_count_day;
    $response["count_week"] = $coupon_post_count_week;
    $response["count_month"] = $coupon_post_count_month;
    $response["count_year"] = $coupon_post_count_year;
    echo json_encode($response);
            
}
?>