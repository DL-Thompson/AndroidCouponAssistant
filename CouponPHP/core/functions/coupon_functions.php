<?php

function post_coupon($barcode, $exp_date, $image_blob) {
    //Posts a single coupon and returns the id of the posted coupon.
    global $db;
    try {
        //A coupon can have the same barcode, but it should not have the
        //same barcode and expiration date. If it has both, it is a duplicate
        //and the coupon isn't submitted
        if (coupon_exists($barcode, $exp_date) !== false) {
            response_success("Coupon already exists.");
        }

        //Prepare the query
        $query = "INSERT INTO coupon (full_code, exp_date, image_blob) VALUES (:full_code, "
                . ":exp_date, :image_blob)";
        $query_params = array(
            ':full_code' => $barcode,
            ':exp_date' => $exp_date,
            ':image_blob' => $image_blob
        );

        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);

        //Return query results
        $coupon_id = $db->lastInsertId();
        if (!empty($coupon_id)) {
            return $coupon_id;
        }
    } catch (PDOException $ex) {
        response_error("Inserting coupon failed.");
    }
    return false;
}

function query_coupons($barcode) {
    //Returns a list of coupons that match the barcode.
    global $db;
    try {
        //Prepare the query
        $query = coupon_barcode_query_string($barcode);
        $query_params = array();

        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("Error querying coupons.");
    }

    //Return query results
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
        return $response;
    } else {
        return false;
    }
}

function coupon_exists($barcode, $exp_date) {
    //Checks to be sure a coupon exists and returns the id
    global $db;
    try {
        //Prepares the query
        $query = "SELECT * FROM coupon WHERE full_code = :full_code AND exp_date = :exp_date";
        $query_params = array(
            ':full_code' => $barcode,
            ':exp_date' => $exp_date
        );

        //Executes the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("Error querying coupons.");
    }

    //Return results
    $row = $stmt->fetch();
    if ($row) {
        //coupon exists
        $id = $row['id'];
        return $id;
    }
    return false;
}

function coupon_barcode_query_string($barcode) {
    //Prepare the query for barcode wildcards needed for coupon matching
    //Prepare initial barcode
    $barcode = substr($barcode, 0, 10);
    $barcode[0] = "_";
    $barcode[9] = "_";
    $barcode[10] = "%";

    //Begin preparing each family code with the appropriate wildcards
    //Family Code ###
    $q1 = $barcode;
    //Partial barcode matches
    //Family Code ##0
    $q2 = $barcode;
    $q2[8] = 0;
    //Family Code #00
    $q3 = $barcode;
    $q3[7] = 0;
    $q3[8] = 0;
    //Family Code 000
    $q4 = $barcode;
    $q4[6] = 0;
    $q4[7] = 0;
    $q4[8] = 0;
    //Family Code #0#
    $q5 = $barcode;
    $q5[7] = 0;
    //Family Code 00#
    $q6 = $barcode;
    $q6[6] = 0;
    $q6[7] = 0;
    //Family Code 0#0
    $q7 = $barcode;
    $q7[6] = 0;
    $q7[8] = 0;
    //Family Code 0##
    $q8 = $barcode;
    $q8[6] = 0;
    //Family Code 992
    $q9 = $barcode;
    $q9[6] = 9;
    $q9[7] = 9;
    $q9[8] = 2;
    $query_string = "SELECT * FROM coupon WHERE full_code " .
            "LIKE '" . $q1 . "' OR full_code " .
            "LIKE '" . $q2 . "' OR full_code " .
            "LIKE '" . $q3 . "' OR full_code " .
            "LIKE '" . $q4 . "' OR full_code " .
            "LIKE '" . $q5 . "' OR full_code " .
            "LIKE '" . $q6 . "' OR full_code " .
            "LIKE '" . $q7 . "' OR full_code " .
            "LIKE '" . $q8 . "' OR full_code " .
            "LIKE '" . $q9 . "';";
    return $query_string;
}
?>

