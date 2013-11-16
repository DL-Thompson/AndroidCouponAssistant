<?php
function post_item($barcode) {
    //Post a single item and return the items id.
    global $db;
    try {
        //Get variables from the submitted $_POST
        //$barcode = $post['barcode'];
        $description = get_upc_name($barcode);
        if (empty($barcode)) {
            response_error("Barcode was empty.");
        }

        //Get all the coupon variable assignments
        $barcode = split_barcode($barcode);
        $full_code = $barcode['full_code'];
        $manuf_code = $barcode['manuf_code'];
        $product_code = $barcode['product_code'];
        $check_digit = $barcode['check_digit'];
        
        //Prepare the query
        $query = "INSERT INTO item (full_code, description ) VALUES (:full_code, :description)";
        $query_params = array(
            ':full_code' => $full_code,
            ':description' => $description
        );
        
        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
        $item_id = $db->lastInsertId();
        if (!empty($item_id)) {
            return $item_id;
        }
    } catch (PDOException $ex) {
        $error = $ex->getCode();
        if ($error = 23000) {
            response_error("Item already exists in database.");
        }
        response_error("Error inserting item into database.");
    }
    return false;
}

function query_items($barcode) {
    //Get the list of matching items from a barcode
    //Temporarily returns all items.
    global $db;
    //$barcode = $post['barcode'];
    try {
        $query = build_item_query($barcode);
        $query_params = array();
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("Error querying items.");
    }
    $rows = $stmt->fetchAll();
    if ($rows) {
        $response["success"] = 1;
        $response["message"] = "Items found!";
        $response["items"] = array();
        foreach ($rows as $row) {
            $item = array();
            $item['full_code'] = $row['full_code'];
            $item['description'] = $row['description'];
            array_push($response['items'], $item);
        }
        echo json_encode($response);
    }
}

function build_item_query($barcode) {
    //Builds the query string to find a list of items that match a coupon
    $query = "SELECT * FROM item WHERE full_code REGEXP ";
    $manuf_code = substr($barcode, 1, 5);
    //. "'[0-9]170001[0-9]{5}'";
    $regexp = "'[0-9]" . $manuf_code;
    if ($barcode[6] === "0") {
        $regexp = $regexp . "[0-9]";
    }
    else {
        $regexp = $regexp . $barcode[6];
    }
    if ($barcode[7] === "0") {
        $regexp = $regexp . "[0-9]";
    }
    else {
        $regexp = $regexp . $barcode[7];
    }
    if ($barcode[8] === "0") {
        $regexp = $regexp . "[0-9]";
    }
    else {
        $regexp = $regexp . $barcode[8];
    }
    $regexp = $regexp . "[0-9]{3}'";
    $query = $query . $regexp;
    return $query;
}

function item_exists($barcode) {
    global $db;
    try {
        $query = "SELECT id FROM item WHERE full_code = :full_code";
        $query_params = array(
            ':full_code' => $barcode,
        );
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("Error finding item.");
    }
    $rows = $stmt->fetchAll();
    if ($rows) {
        //coupon exists
        foreach ($rows as $row) {
            $id = $row['id'];
            return $id;
        }
    }
    return false;
}
?>
