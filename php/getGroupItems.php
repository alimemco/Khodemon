<?php
/**
 * Created by ali moghadam.
 * User: Besat
 * Date: 3/4/2019
 * Time: 10:42 AM
 */

$json = file_get_contents('php://input');
$whatYouNeedCode = json_decode($json);
$code = $whatYouNeedCode->code;
$group = $whatYouNeedCode->group;

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$locPeoData = $DatabaseMan->getGroupItems($group);


echo json_encode(['result'=>$locPeoData]);