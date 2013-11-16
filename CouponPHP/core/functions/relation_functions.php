<?php

function submit($user_id, $coupon_id) {
    //Places the user and coupon into the submitted table.
    //Returns a true or false value for success
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
        $error = $ex->getCode();
        if ($error == 23000) {
            response_success("Item already exists in database.");
        }
        response_error("Inserting coupon failed.");
    }
    return true;
}

function purchase($user_id, $item_id, $coupon_id) {
    //Places the user,item, and coupon into the bought table.
    //Returns a true or false value for success
    global $db;
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
        $error = $ex->getCode();
        if ($error == 23000) {
            response_error("Purchase already exists. Needs to be fixed.");
        }
        response_error("Purchase insert failed.");
    }
    return true;
}

function get_user_coupon_count_by_day($user_id, $num_days) {
    //Query the number of coupons a user has submitted by number of days.
    global $db;
    try {
        //If the days are greater than 0, return the count for that range.
        //If the days are 0 or below, return total time since creation.
        if ($num_days > 0) {
            //Prepare the query
            $query = "SELECT COUNT(*) as coupon_count FROM submitted WHERE date > NOW() - INTERVAL :num_days DAY AND user_id = :user_id";
            $query_params = array(
                ':num_days' => $num_days,
                ':user_id' => $user_id
            );
        } else {
            //Prepare the query
            $query = "SELECT COUNT(*) as coupon_count FROM submitted WHERE user_id = :user_id";
            $query_params = array(
                ':user_id' => $user_id
            );
        }
        //Execute the query
        $stmt = $db->prepare($query);
        $stmt->execute($query_params);

        //Return query result
        $result = (int) $stmt->fetch(PDO::FETCH_OBJ)->coupon_count;
        return $result;
    } catch (PDOException $ex) {
        response_error("User coupon count by " . $num_days . " failed.");
    }
    return 0;
}

function get_user_coupon_submitted_by_day($user_id, $num_days) {
    //Get a list of coupons the user has submitted in the past number of days
    global $db;
    try {
        //If the number of days are greater than 0, return that range.
        //If the number of days are 0 or less, return the range for time since creation.
        if ($num_days > 0) {
            //Prepare the query
            $query = "SELECT full_code, exp_date, image_blob, date FROM submitted, coupon WHERE date > NOW() - INTERVAL :num_days DAY AND user_id = :user_id AND coupon_id = id";
            $query_params = array(
                ':num_days' => $num_days,
                ':user_id' => $user_id
            );
        } else {
            //Prepare the query
            $query = "SELECT COUNT(*) as coupon_count FROM submitted WHERE date > NOW() - INTERVAL :num_days DAY AND user_id = :user_id";
            $query_params = array(
                ':num_days' => $num_days,
                ':user_id' => $user_id
            );
        }

        //Execute the query
        $stmt = $db->prepare($query);
        $stmt->execute($query_params);

        //Return query results
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
    } catch (PDOException $ex) {
        response_error("User coupon count by " . $num_days . " failed.");
    }
    return "No coupons found";
}

function get_items_bought_count($user_id) {
    //Gets the count of items a user has bought
    global $db;
    try {
        //Prepare the query
        $query = "SELECT COUNT(*) AS bought_count FROM bought WHERE user_id = :user_id";
        $query_params = array(
            ':user_id' => $user_id
        );

        //Execute the query
        $stmt = $db->prepare($query);
        $stmt->execute($query_params);

        //Return query result
        $result = (int) $stmt->fetch(PDO::FETCH_OBJ)->bought_count;
        return $result;
    } catch (PDOException $ex) {
        response_error("Bought count failed.");
    }
    return 0;
}
?>

