<?php

function response_error($message) {
    //kills the php script with the passed error message
    $response['success'] = 0;
    if (is_string($message)) {
        $response['message'] = $message;
    } else {
        $response['message'] = "Uknown Error.";
    }
    die(json_encode($response));
}

function response_success($message) {
    //JSON success response with a message
    $response['success'] = 1;
    if (is_string($message)) {
        $response['message'] = $message;
    } else {
        $response['message'] = "Uknown message.";
    }
    die(json_encode($response));
}

function get_upc_name($barcode) {
    //RQueries an outside UPC code database to get the name of a scanned item
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
    } elseif (!empty($description)) {
        return $description;
    } else {
        return "Unknown";
    }
}
?>

