<?php

include_once("config.php");
include 'upload/images/create_thumb.php';

$siteAddress = "http://khodemon.ir/upload/images/";
$uploadDir = "upload/images/";
$thumbDir150 = "/thumb_150/";
$thumbDir500 = "/thumb_500/";
$thumbDir1000 = "/thumb_1000/";
$thumbDir1500 = "/thumb_1500/";

$json = json_decode(file_get_contents('php://input'),true);


$id = $json["id"];
$name = $json["name"];
$group = $json["group"];
$pic_id = $json["pic_id"];
$image = $json["image"];

$response = array();

$decodedImage = base64_decode("$image");

if ($group == "LOCATION"){
    // $return = file_put_contents("uploads/locations/".$name.".JPG", $decodedImage);
    $return = file_put_contents("upload/images/LOCATION/".$name, $decodedImage);
}else if ($group == "PEOPLE"){
    $return = file_put_contents("upload/images/PEOPLE/".$name, $decodedImage);
}else if ($group == "MEMBER"){
    $return = file_put_contents("upload/images/MEMBER/".$name, $decodedImage);
}else {
    $return = file_put_contents("upload/images/".$name, $decodedImage);
}



if($return !== false){

    list($width, $height) = getimagesize($siteAddress.$group."/".$name);

    createThumbnail($uploadDir.$group."/".$name, $uploadDir.$group.$thumbDir150.$name, 150);
    createThumbnail($uploadDir.$group."/".$name, $uploadDir.$group.$thumbDir500.$name, 500);
    createThumbnail($uploadDir.$group."/".$name, $uploadDir.$group.$thumbDir1000.$name, 1000);
    createThumbnail($uploadDir.$group."/".$name, $uploadDir.$group.$thumbDir1500.$name, 1500);

    $response['success'] = 1;
    $response['message'] = "Image Uploaded Successfully";
    $response['pic_id'] = $pic_id;
    $response['url'] = $siteAddress.$group."/".$name;
    $response['width'] = $width;
    $response['height'] = $height;
    $response['thumb_150'] = $siteAddress.$group.$thumbDir150.$name;
    $response['thumb_1000'] = $siteAddress.$group.$thumbDir1000.$name;




}else{
    $response['success'] = 0;
    $response['message'] = "Image Uploaded Failed";

}




echo json_encode($response);
