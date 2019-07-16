<?php
/**
 * Created by PhpStorm.
 * User: ali moghadam
 * Date: 6/8/2019
 * Time: 11:46 AM
 */

$json = file_get_contents('php://input');
$data = json_decode($json);
$category =$data->GROUP_NAME;

include 'DatabaseMan.php';

$databaseMan = new DatabaseMan();


$result = $databaseMan->getCategory($category);

echo json_encode(['result'=>$result]);