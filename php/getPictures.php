<?php
/**
 * Created by PhpStorm.
 * User: Besat
 * Date: 5/2/2019
 * Time: 2:58 PM
 */

$json = file_get_contents('php://input');
$jsonDecoded = json_decode($json);

$Post_ID = $jsonDecoded->Post_ID;

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$PicData = $DatabaseMan->getPictures($Post_ID);

echo json_encode(['result'=>$PicData]);