<?php
function response_error($message) {
    //kills the php script with the passed error message
    //for testing purposes. Error messages should be limited
    //when the code is finished to limit information to any
    //malicious users
        $response['success'] = 0;
        if (is_string($message)) {
            $response['message'] = $message;
        }
        else {
            $response['message'] = "Uknown Error.";
        }
        die(json_encode($response));
    }
    
    function split_barcode($barcode) {
    //Takes a full barcode and splits it into seperated values
    //that will be stored in the database. Returns an array with
    //each database id equal to the value.
    $full_code = $barcode;
    $prefix = substr($barcode, 0, 1);
    $manuf_code = substr($barcode, 1, 5);
    $family_code = substr($barcode, 6, 3);
    $value_code = substr($barcode, 9, 2);
    $check_digit = substr($barcode, -1);
    $product_code = substr($barcode, 6, 5);

    $barcode = array(
        'full_code' => $full_code,
        'prefix' => $prefix,
        'manuf_code' => $manuf_code,
        'family_code' => $family_code,
        'value_code' => $value_code,
        'check_digit' => $check_digit,
        'product_code' => $product_code
    );
    //check digit needs to be calculated
    return $barcode;
}

function json_post($success, $message) {
    //Echo a success message via JSON for the android device to read
    if (is_int($success) && is_string($message)) {
        $response["success"] = $success;
        $response["message"] = $message;
        echo json_encode($response);
    } else {
        response_error("Invalid json_post variable types.");
    }
}

function get_upc_name($barcode) {
    //Returns the item name.
    //Sometimes the name is in either the name or desc field, so both are checked
    global $upc_url;
    $url = $upc_url . $barcode;
    $upc_json = file_get_contents($url);
    $upc_array = json_decode($upc_json, true);
    if ($upc_array['valid'] === 'false') {
        return "Item not found.";
    }
    $itemname = $upc_array['itemname'];
    $description = $upc_array['description'];
    if (!empty($itemname)) {
        return $itemname;
    }
    elseif (!empty($description)) {
        return $description;
    }
    else {
        return "Unknown";
    }
}
?>

