<?php

require_once '../core/init.php';

$user_id = login($_POST);
if($user_id !== false) {
    //If login was successful, try to post the item.
    $item_id = post_item($_POST);
    //Redo to check if item exists.
    if ($item_id !== false) {
        //If item was posted successfully, post the submitted relationship.
        $purchased = purchase($_POST, $user_id, $item_id);
        if ($purchased === true) {
            //Bought relationship was posted successfully.
            //Return the info via JSON to the android device.
            json_post(1, "Item purchased.");
        }
        else {
            //Return the failure message via JSON to the android device.
            json_post(0, "Item purchase failed.");
        }
    }
}


?>

