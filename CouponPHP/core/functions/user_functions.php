<?php
<<<<<<< HEAD

function user_exists($username) {
    //Returns user id if the user exists in the user table.
    global $db;
    try {
        //Prepare query
        $query = "SELECT id FROM user WHERE login = :username";
        $query_params = array(
            ':username' => $username);

        //Execute query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);

        //Return query results
=======
    function user_exists($username) {
    //Returns user id if the user exists in the user table.
    global $db;
    try {
        $query = "SELECT id FROM user WHERE login = :username";
        $query_params = array(
            ':username' => $username);
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
>>>>>>> master
        $row = $stmt->fetch();
        if ($row) {
            return $row['id'];
        } else {
            return false;
        }
    } catch (PDOException $ex) {
        response_error("Error querying username.");
    }
<<<<<<< HEAD
}

function check_login($username, $password) {
    //Returns true if the user is in the database and the correct password
    //is entered
    global $db;
    try {
        //Prepare query
=======
    return false;
}

function check_login($username, $password) {
    //returns true if the user is in the database and the correct password
    //is entered
    global $db;
    try {
>>>>>>> master
        $query = "SELECT id FROM user WHERE login = :username AND passwd = :password";
        $query_params = array(
            ':username' => $username,
            ':password' => md5($password));
<<<<<<< HEAD

        //Execute query
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);

        //Return query results
=======
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
>>>>>>> master
        $row = $stmt->fetch();
        if ($row) {
            return true;
        } else {
            return false;
        }
    } catch (PDOException $ex) {
        response_error("Error querying username and password.");
    }
}

<<<<<<< HEAD
function login($username, $password) {
    //Checks to see if the user/password is correct.
    //Returns the users id or kills the script
    $id = user_exists($username);
    if ($id === false) {
        response_error("That username does not exist.");
    } else {
        if (check_login($username, $password) == false) {
            response_error("Username password combination is incorrect");
=======
function login($post) {
    //Checks to see if the user/password is correct.
    //Returns the users id or kills the script
    if (empty($post) == false) {
        $username = $post['username'];
        $password = $post['password'];
        if (empty($username) || empty($password)) {
            response_error("You must enter a username and password.");
        }
        $id = user_exists($username);
        if ($id === false) {
            response_error("That username does not exist.");
        } else {
            if (check_login($username, $password) == false) {
                response_error("Username password combination is incorrect");
            }
>>>>>>> master
        }
    }
    return $id;
}

<<<<<<< HEAD
function register($username, $password, $first_name, $last_name) {
    //Registers the user if the login isn't in the database
    global $db;

    //Checks to be sure the username isn't taken.
    //Then, registers the user returning true or false.
    $user_exist = user_exists($username);
    if ($user_exist === false) {
        //The user doesn't exist. Submit registration to db.
        try {
            //Prepare query
=======
function register($post) {
    global $db;
    //Registers the user if the login isn't in the database
    //Returns true/false
    if (empty($post) == false) {
        $username = $post['username'];
        $password = $post['password'];
        if (empty($username) || empty($password)) {
            response_error("You must enter a username and password.");
        }
        $user_exist = user_exists($username);
        if ($user_exist === false) {
            $first_name = $post['first_name'];
            $last_name = $post['last_name'];
            //The user doesn't exist. Submit registration to db.
>>>>>>> master
            $query = "INSERT INTO user ( login, passwd, first_name, last_name  ) "
                    . "VALUES ( :username, :password, :first_name, :last_name )";
            $query_params = array(
                ':username' => $username,
                ':password' => md5($password),
                ':first_name' => $first_name,
                ':last_name' => $last_name
            );
<<<<<<< HEAD

            //Execute query
            $stmt = $db->prepare($query);
            $result = $stmt->execute($query_params);
            return true;
        } catch (PDOException $ex) {
            response_error("Error registering new user.");
        }
    } else {
        //The user exists. Advise to choose a different name.
        response_error("That username already exists. Please choose another.");
    }
    return $false;
}

=======
            try {
                $stmt = $db->prepare($query);
                $result = $stmt->execute($query_params);
                return true;
            } catch (PDOException $ex) {
                response_error("Error registering new user.");
            }
        } else {
            //The user exists. Advise to choose a different name.
            response_error("That username already exists. Please choose another.");
        }
    }
    return $false;
}
>>>>>>> master
?>