<?php
/**
 * Created by PhpStorm.
 * User: ali moghadam
 * Date: 5/23/2019
 * Time: 12:56 PM
 */

$json = file_get_contents('php://input');
$detail = json_decode($json);


$LOCATION_ID = $detail->LOCATION_ID;
include 'DatabaseMan.php';

$databaseMan = new DatabaseMan();

$result = $databaseMan->getPersonnel($LOCATION_ID);

echo json_encode(['result'=>$result]);
