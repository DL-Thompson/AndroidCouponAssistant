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

function get_user_coupon_count($user_id) {
    //Get the number of coupons submitted by a user
    global $db;
    try {
        //Prepare the query
        $query = "SELECT COUNT(*) as coupon_count FROM submitted WHERE user_id = :user_id";
        $query_params = array(
            ':user_id' => $user_id
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $stmt->execute($query_params); 
        $result = (int) $stmt->fetch(PDO::FETCH_OBJ)->coupon_count;
    } catch (PDOException $ex) {
        response_error("Getting user submitted coupon count failed.");
        return false;
    }
    return $result;
}

function get_user_coupon_count_by_day($user_id, $num_days) {
    //Query the number of coupons a user has submitted by number of days.
    global $db;
    try {
        //prepare the query
        $query = "SELECT COUNT(*) as coupon_count FROM submitted WHERE date > NOW() - INTERVAL :num_days DAY AND user_id = :user_id";
        $query_params = array(
            ':num_days' => $num_days,
            ':user_id' => $user_id
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $stmt->execute($query_params);
        $result = (int) $stmt->fetch(PDO::FETCH_OBJ)->coupon_count;
    } catch (PDOException $ex) {
        response_error("User coupon count by " . $num_days . " failed.");
    }
    return $result;
    
}

function get_user_coupon_submitted_by_day($user_id, $num_days) {
    //Get a list of coupons the user has submitted in the past number of days
    global $db;
        try {
        //prepare the query
        $query = "SELECT full_code, exp_date, image_blob, date FROM submitted, coupon WHERE date > NOW() - INTERVAL :num_days DAY AND user_id = :user_id AND coupon_id = id";
        $query_params = array(
            ':num_days' => $num_days,
            ':user_id' => $user_id
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("User coupon count by " . $num_days . " failed.");
    }
    $rows = $stmt->fetchAll();
    if ($rows) {
        $coupon_list = array();
        foreach ($rows as $row) {
            $coupon = array();
            $coupon['full_code'] = $row['full_code'];
            $coupon['exp_date'] = $row['exp_date'];
            $coupon['image_blob'] = $row['image_blob'];
            $coupon['date'] = $row['date'];
            array_push($coupon_list, $coupon);
        }
        return $coupon_list;
    }
    return "No coupons founds";
    
}

function get_user_coupon_submitted($user_id) {
    //Get a list of coupons the user has submitted in the past number of days
    global $db;
        try {
        //prepare the query
        $query = "SELECT full_code, exp_date, image_blob, date FROM submitted, coupon WHERE  user_id = :user_id AND coupon_id = id";
        $query_params = array(
            ':user_id' => $user_id
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("User coupon count by " . $num_days . " failed.");
    }
    $rows = $stmt->fetchAll();
    if ($rows) {
        $coupon_list = array();
        foreach ($rows as $row) {
            $coupon = array();
            $coupon['full_code'] = $row['full_code'];
            $coupon['exp_date'] = $row['exp_date'];
            $coupon['image_blob'] = $row['image_blob'];
            $coupon['date'] = $row['date'];
            array_push($coupon_list, $coupon);
        }
        return $coupon_list;
    }
    return "No coupons founds";
    
}
?>

