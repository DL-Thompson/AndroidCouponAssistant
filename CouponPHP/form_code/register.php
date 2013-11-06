<?php

require_once '../core/init.php';

$register = register($_POST);
if ($register !== false) {
    json_post(1, "User successfully registered.");
}
else {
    json_post(0, "Error registering user.");
}


?>

