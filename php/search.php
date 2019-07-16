<?php

header('Content-Type: application/json');

$json = file_get_contents('php://input');
$data = json_decode($json);

$keyword = $data->keyword;
$category = $data->category;
$phone = $data->phone;
$city = $data->city;
$province = $data->province;

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$result = $DatabaseMan->search($category,$keyword, $city,$province);



echo json_encode(['result' => $result]);