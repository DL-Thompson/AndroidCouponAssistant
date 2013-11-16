<?php

//Temporarily display errors. Remove later.
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);

//Require all files needed
require_once 'database.php';
include_once 'functions/coupon_functions.php';
include_once 'functions/item_functions.php';
include_once 'functions/other_functions.php';
include_once 'functions/relation_functions.php';
include_once 'functions/user_functions.php';

//URL for the off site upc database
$upc_url = "http://www.upcdatabase.org/api/json/427764840ef7a9e41ca5a09e1dc415e1/";
?>

