<?php
/**
 * Created by ali moghadam.
 * User: Besat
 * Date: 2/28/2019
 * Time: 10:58 AM
 */

$json = file_get_contents('php://input');
$whatYouNeedCode = json_decode($json);
//$code = $whatYouNeedCode->code;
$code = 0;


include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$locPeoData = $DatabaseMan->getHomeItems();


echo json_encode(['result'=>$locPeoData]);