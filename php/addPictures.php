<?php
/**
 * Created by PhpStorm.
 * User: ali moghadam
 * Date: 5/2/2019
 * Time: 11:08 AM
 */

$json = file_get_contents('php://input');
$LocPeoDetail = json_decode($json);



$Post_ID = $LocPeoDetail->Post_ID;
$is_original = $LocPeoDetail->is_original;
$pic_id = $LocPeoDetail->pic_id;
$pic_address = $LocPeoDetail->pic_address;

$width = $LocPeoDetail->width;
$height = $LocPeoDetail->height;
$thumb_150 = $LocPeoDetail->thumb_150;
$thumb_1000 = $LocPeoDetail->thumb_1000;



include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$pictures = $DatabaseMan->addPictures($Post_ID,$is_original,$pic_id,$pic_address,$width,$height,$thumb_150,$thumb_1000);

echo json_encode(['result' => $pictures]);

