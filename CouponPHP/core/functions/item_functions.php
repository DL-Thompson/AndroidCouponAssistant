<?php
function post_item($post) {
    //Post a single item and return the items id.
    global $db;
    try {
        //Get variables from the submitted $_POST
        $barcode = $post['barcode'];
        $description = get_upc_name($barcode);

        //Get all the coupon variable assignments
        $barcode = split_barcode($barcode);
        $full_code = $barcode['full_code'];
        $manuf_code = $barcode['manuf_code'];
        $product_code = $barcode['product_code'];
        $check_digit = $barcode['check_digit'];
        
        //Prepare the query
        $query = "INSERT INTO item (full_code, manuf_code, product_code, check_digit, "
                . "description ) VALUES (:full_code, :manuf_code, :product_code, :check_digit, "
                . ":description)";
        $query_params = array(
            ':full_code' => $full_code,
            ':manuf_code' => $manuf_code,
            ':product_code' => $product_code,
            ':check_digit' => $check_digit,
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
        response_error("Error inserting item into database. Error: " . $error);
        return false;
    }
    return false;
}

function query_items($post) {
    //Get the list of matching items from a barcode
    //Temporarily returns all items.
    global $db;
    $barcode = $post['barcode'];
    try {
        $query = "SELECT * FROM item";
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
?>
