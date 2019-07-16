<?php
/**
 * Created by PhpStorm.
 * User: Besat
 * Date: 5/26/2019
 * Time: 5:32 PM
 */

include 'DatabaseMan.php';

$databaseMan = new DatabaseMan();

$result = $databaseMan->getPersonList();

echo json_encode(['result'=>$result]);
