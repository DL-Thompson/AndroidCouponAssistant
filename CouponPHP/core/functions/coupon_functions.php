<?php
function post_coupon($post) {
    //Posts a single coupon and returns the id of the posted coupon.
    global $db;
    try {
        //Get variables from the submitted $_POST
        $barcode = $post['barcode'];
        $exp_date = $post['exp_date'];
        $image_blob = $post['image_blob'];
        
        //Get all the coupon variable assignments
        $barcode = split_barcode($barcode);
        $full_code = $barcode['full_code'];
        $prefix = $barcode['prefix'];
        $manuf_code = $barcode['manuf_code'];
        $family_code = $barcode['family_code'];
        $value_code = $barcode['value_code'];
        $check_digit = $barcode['check_digit'];
        
        //Prepare the query
        $query = "INSERT INTO coupon (full_code, prefix, manuf_code, family_code, "
                . "value_code, check_digit, exp_date, image_blob) VALUES (:full_code, "
                . ":prefix, :manuf_code, :family_code, :value_code, :check_digit, "
                . ":exp_date, :image_blob)";
        $query_params = array(
            ':full_code' => $full_code,
            ':prefix' => $prefix,
            ':manuf_code' => $manuf_code,
            ':family_code' => $family_code,
            ':value_code' => $value_code,
            ':check_digit' => $check_digit,
            ':exp_date' => $exp_date,
            ':image_blob' => $image_blob
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
        $coupon_id = $db->lastInsertId();
        if (!empty($coupon_id)) {
            return $coupon_id;
        }
    } catch (PDOException $ex) {
        response_error("Inserting coupon failed.");
        return false;
    }
    return false;
}

function get_coupon_id($barcode) {
    global $db;
    try {
        $query = "SELECT id FROM coupon WHERE full_code = :barcode";
        $query_params = array (
            ':barcode' => $barcode
        );
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
        $rows = $stmt->fetchAll();
        if ($rows) {
            foreach ($rows as $row) {
                $id = $row['id'];
                return $id;
            }
        }
    } catch (PDOException $ex) {
        response_error("Error while searching for coupon.");
    }
    return false;
}

function query_coupons($post) {
    global $db;
    $barcode = $post['barcode'];
    try {
        $query = "SELECT * FROM coupon";
        $query_params = array();
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("Error querying coupons.");
    }
    $rows = $stmt->fetchAll();
    if ($rows) {
        $response["success"] = 1;
        $response["message"] = "Coupons found!";
        $response["coupons"] = array();
        foreach ($rows as $row) {
            $coupon = array();
            $coupon['full_code'] = $row['full_code'];
            $coupon['exp_date'] = $row['exp_date'];
            $coupon['image_blob'] = $row['image_blob'];
            array_push($response['coupons'], $coupon);
        }
        echo json_encode($response);
    }
}
?>

