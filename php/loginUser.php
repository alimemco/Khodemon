<?php

$json = file_get_contents('php://input');
$loginDetail = json_decode($json);
$user_name = $loginDetail->user_name;
$user_pass = $loginDetail->user_pass;

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$loginUser = $DatabaseMan->loginUser($user_name,$user_pass);


echo json_encode(['result'=>$loginUser]);