<?php
/**
 * Created by PhpStorm.
 * User: Besat
 * Date: 6/1/2019
 * Time: 11:57 AM
 */

$json = file_get_contents('php://input');
$jsonDecoded = json_decode($json);

$Post_ID = $jsonDecoded->Post_ID;
$GROUP_NAME = $jsonDecoded->GROUP_NAME;


include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$result = $DatabaseMan->getInfo($GROUP_NAME,$Post_ID);

echo json_encode(['result'=>$result]);