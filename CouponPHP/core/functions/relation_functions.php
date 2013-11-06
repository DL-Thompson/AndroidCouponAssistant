<?php
function submit($post, $user_id, $coupon_id) {
    //Places the user and coupon into the submitted table.
    //Returns a true or false value
    global $db;
    try {
        //Prepare the query
        $query = "INSERT INTO submitted (coupon_id, user_id) VALUES (:coupon_id, :user_id)";
        $query_params = array(
            ':coupon_id' => $coupon_id,
            ':user_id' => $user_id
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);     
    } catch (PDOException $ex) {
        response_error("Inserting coupon failed.");
        return false;
    }
    return true;
    
}

function purchase($post, $user_id, $item_id) {
    //Places the user,item, and coupon into the bought table.
    //Returns a true or false value
    global $db;
    $coupon_id = $post['coupon_barcode'];
    $coupon_id = get_coupon_id($coupon_id);
    if ($coupon_id === false) {
        return false;
    }
    try {
        //Prepare the query
        $query = "INSERT INTO bought (coupon_id, item_id, user_id) VALUES (:coupon_id, :item_id, :user_id)";
        $query_params = array(
            ':coupon_id' => $coupon_id,
            ':item_id' => $item_id,
            ':user_id' => $user_id
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);   
    } catch (PDOException $ex) {
        response_error("Inserting coupon failed.");
        return false;
    }
    return true;
    
}
?>

