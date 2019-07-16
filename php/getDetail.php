<?php
/**
 * Created by PhpStorm.
 * User: ali moghadam
 * Date: 5/15/2019
 * Time: 9:27 AM
 */

$json = file_get_contents('php://input');
$jsonDecoded = json_decode($json);

$Post_ID = $jsonDecoded->Post_ID;


include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$result = $DatabaseMan->getDetail($Post_ID);

echo json_encode(['result'=>$result]);