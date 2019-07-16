<?php
/**
 * Created by PhpStorm.
 * User: Besat
 * Date: 3/8/2019
 * Time: 10:50 AM
 */

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$locPeoData = $DatabaseMan->getHomeItemsList();

echo json_encode(['result'=>$locPeoData]);
