<?php

function post_item($barcode) {
    //Post a single item and return the items id.
    global $db;
    try {
        //Query variables
        $description = get_upc_name($barcode);

        //Prepare the query
        $query = "INSERT INTO item (full_code, description ) VALUES (:full_code, :description)";
        $query_params = array(
            ':full_code' => $barcode,
            ':description' => $description
        );

        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
        $item_id = $db->lastInsertId();
        if (!empty($item_id)) {
            //Return the item id number if it is successfully posted
            return $item_id;
        }
    } catch (PDOException $ex) {
        $error = $ex->getCode();
        if ($error == 23000) {
            response_success("Item already exists in database.");
        }
        response_error("Error inserting item into database.");
    }
    return false;
}

function query_items($barcode) {
    //Get the list of matching items from a barcode
    global $db;
    try {
        //Prepare the query
        $query = build_item_query($barcode);
        $query_params = array();

        //Execute the query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("Error querying items.");
    }

    //Prepare and return the results
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
        return $response;
    }
    return false;
}

function build_item_query($barcode) {
    //Builds the query string to find a list of items that match a coupon
    $query = "SELECT * FROM item WHERE full_code REGEXP ";
    $manuf_code = substr($barcode, 1, 5);
    $regexp = "'[0-9]" . $manuf_code;
    if ($barcode[6] === "0") {
        $regexp = $regexp . "[0-9]";
    } else {
        $regexp = $regexp . $barcode[6];
    }
    if ($barcode[7] === "0") {
        $regexp = $regexp . "[0-9]";
    } else {
        $regexp = $regexp . $barcode[7];
    }
    if ($barcode[8] === "0") {
        $regexp = $regexp . "[0-9]";
    } else {
        $regexp = $regexp . $barcode[8];
    }
    $regexp = $regexp . "[0-9]{3}'";
    $query = $query . $regexp;
    return $query;
}

function item_exists($barcode) {
    //Checks to see if an item exists in the database.
    //If it does, it returns the id.
    global $db;
    try {
        //Prepare query
        $query = "SELECT id FROM item WHERE full_code = :full_code";
        $query_params = array(
            ':full_code' => $barcode,
        );

        //Execute query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        response_error("Error finding item.");
    }
    //Return results
    $row = $stmt->fetch();
    if ($row) {
        //Coupon exists, return id
        $id = $row['id'];
        return $id;
    }
    return false;
}

?>
