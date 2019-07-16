<?php
$host="localhost";
$user="khodemon_user";
$password="Ali0631Papital";
$db = "khodemon_db";

$con = mysqli_connect($host,$user,$password,$db);

// Check connection
if (mysqli_connect_errno())
{
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}


function OpenConnection()
{
    $dbHost = "localhost";
    $dbUser = "khodemon_user";
    $dbPass = "Ali0631Papital";
    $db = "khodemon_db";
    $conn = new mysqli( $dbHost, $dbUser, $dbPass,$db) or die("Connect failed: %s\n". $conn -> error);
    return $conn;
}

function CloseConnection($conn)
{
    $conn -> close();
}