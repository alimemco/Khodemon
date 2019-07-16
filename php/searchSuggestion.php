<?php

header('Content-Type: application/json');

$json = file_get_contents('php://input');
$data = json_decode($json);

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$result = $DatabaseMan->getSearchCategory(null,null,null,null);


echo json_encode(['result' => $result]);