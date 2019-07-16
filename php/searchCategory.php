<?php
/**
 * Created by PhpStorm.
 * User: Ali
 * Date: 7/15/2019
 * Time: 12:29 PM
 */

header('Content-Type: application/json');

$json = file_get_contents('php://input');
$data = json_decode($json);

$keyword = $data->keyword;
$category = $data->category;
$phone = $data->phone;
$city = $data->city;
$province = $data->province;

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();
echo $keyword;
echo "<br>";
echo $category;
echo "<br>";
echo $phone;
echo "<br>";
echo $city;
echo "<br>";
echo $province;
echo "<br>";
$result = $DatabaseMan->getSearchCategory($category,$keyword, $city,$province);


echo json_encode(['result' => $result]);