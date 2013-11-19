<?php

require_once '../core/init.php';

$user_id = login($_POST);

if ($user_id !== false) {
    //If login was successful, try to search the coupons for the barcode
    //and print the json list if successful
    query_items($_POST);
}
?>