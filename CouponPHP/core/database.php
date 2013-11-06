<?php

//Login/Connection Variables
$username = "b24_13865725";
$password = "ketchup6";
$host = "sql203.byethost24.com";
$dbname = "b24_13865725_coupondb";

//User UTF-8 Encoding
$options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8');

//Open Connection
try {
    $db = new PDO("mysql:host={$host};dbname={$dbname};charset=utf8", $username, $password, $options);
} catch (PDOException $ex) {
    die("Failed to connect to the database: " . $ex->getMessage());
}

//Throw Exception on Error
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

//Return rows as string arrays
$db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

//Undo magic quotes. Old bugfix
if (function_exists('get_magic_quotes_gpc') && get_magic_quotes_gpc()) {

    function undo_magic_quotes_gpc(&$array) {
        foreach ($array as &$value) {
            if (is_array($value)) {
                undo_magic_quotes_gpc($value);
            } else {
                $value = stripslashes($value);
            }
        }
    }

    undo_magic_quotes_gpc($_POST);
    undo_magic_quotes_gpc($_GET);
    undo_magic_quotes_gpc($_COOKIE);
}

//Tell browser using UTF-8
header('Content-Type: text/html; charset=utf-8');

//Initialize Session
session_start();
?>