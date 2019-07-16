<?php

$json = file_get_contents('php://input');
$LocPeoDetail = json_decode($json);
//$user_name = $LocPeoDetail->user_name;


if ($LocPeoDetail->work_experience != null){
    $work_experience = $LocPeoDetail->work_experience;
}else{
    $work_experience = 0;
}

if ($LocPeoDetail->since != null){
    $since = $LocPeoDetail->since;
}else{
    $since = 0;
}


if ($LocPeoDetail->dimensions != null){
    $dimensions = $LocPeoDetail->dimensions;
}else{
    $dimensions = 0;
}

$group_name = $LocPeoDetail->group_name;
$nameLocPeo = $LocPeoDetail->nameLocPeo;
$user_name = $LocPeoDetail->user_name;
$owner_seller = $LocPeoDetail->owner_seller;
$tagLoc = $LocPeoDetail->tagLoc;
$city = $LocPeoDetail->city;
$province = $LocPeoDetail->province;
$phone = $LocPeoDetail->phone;
$address = $LocPeoDetail->address;
/*
$experts = $LocPeoDetail->experts;
$degreeOfEducation = $LocPeoDetail->degreeOfEducation;
$study = $LocPeoDetail->study;
*/
$experts = "";
$degreeOfEducation = "";
$study = "";

$description = $LocPeoDetail->description;
$instagram = $LocPeoDetail->instagram;
$telegram = $LocPeoDetail->telegram;
$site = $LocPeoDetail->site;
$map = $LocPeoDetail->map;
$evidence = $LocPeoDetail->evidence;
$original_pic = $LocPeoDetail->original_pic;
$thumb_pic = $LocPeoDetail->thumb_pic;
//$thumb_pic = 'testNew';


date_default_timezone_set('Asia/Tehran');
include 'Time/converterTime.php';
list($gy,$gm,$gd)=explode('-',date('Y-n-d'));
$j_date_string=gregorian_to_jalali($gy,$gm,$gd,'/'); //خروجی رشته

$time =  $j_date_string."-".date("H:i:s");





include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
$LocPeo = $DatabaseMan->AddLocationOrPeople($group_name, $nameLocPeo, $user_name,$owner_seller, $tagLoc,$city,$province, $since, $work_experience, $dimensions, $phone, $address, $experts,$degreeOfEducation,$study, $description,$instagram,$telegram,$site, $map, $evidence, $original_pic,$thumb_pic,$time);


echo json_encode(['result' => $LocPeo]);