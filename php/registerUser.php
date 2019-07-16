<?php

$json = file_get_contents('php://input');
$get = json_decode($json);
$user_name = $get->user_name;
$user_pass = $get->user_pass;
$first_name = $get->first_name;
$last_name = $get->last_name;


include 'DatabaseMan.php';
$DatabaseMan = new DatabaseMan();
$regUser = $DatabaseMan->createUser(
    $user_name,
    $user_pass,
    $first_name,
    $last_name);
echo json_encode(['result' => $regUser]);