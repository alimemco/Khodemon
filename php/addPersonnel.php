<?php
/**
 * Created by PhpStorm.
 * User: ali moghadam
 * Date: 5/23/2019
 * Time: 11:25 AM
 */

$json = file_get_contents('php://input');
$detail = json_decode($json);

//$LOCATION_ID = 43;
//$PEOPLE_ID = 48;
$withEvidence = "false";
$evidence = "dens";

$LOCATION_ID = $detail->LOCATION_ID;
$PEOPLE_ID = $detail->PEOPLE_ID;
/*
$withEvidence = $detail->withEvidence;
$evidence = $detail->evidence;*/

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$result = $DatabaseMan->addPersonnel($LOCATION_ID,$PEOPLE_ID,$withEvidence,$evidence);

echo json_encode(['result' => $result]);

