<?php

require_once '../core/init.php';

$user_id = login($_POST);
if($user_id !== false) {
    //If login was successful, try to post the item.
    $item_id = post_item($_POST);
    //Redo to check if item exists.
    if ($item_id !== false) {
        json_post(1, "Item posted.");
    }
    else {
        json_post(0, "Item posting failed.");
    }
}


?>


