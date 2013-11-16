<?php

require_once '../core/init.php';

//Check to be sure all required input is not blank
$username = $_POST['username'];
$password = $_POST['password'];
$first_name = $_POST['first_name'];
$last_name = $_POST['last_name'];
if (empty($username)) {
    response_error("Username field left blank.");
} elseif (empty($password)) {
    response_error("Password field left blank.");
}
//First and Last name are not required fields.
//Tries to register a new user into the database
$register = register($username, $password, $first_name, $last_name);
if ($register !== false) {
    response_success("User successfully registered.");
} else {
    response_error("Error registering user.");
}
?>

