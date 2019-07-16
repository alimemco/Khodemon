<?php
/**
 * Created by PhpStorm.
 * User: ali
 * Date: 6/2/2019
 * Time: 1:11 PM
 */

$json = file_get_contents('php://input');
$data = json_decode($json);
$category =$data->GROUP_NAME;

include 'DatabaseMan.php';

$databaseMan = new DatabaseMan();


$result = $databaseMan->getCategory($category);

echo json_encode(['result'=>$result]);
