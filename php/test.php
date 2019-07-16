<?php
/**
 * Created by PhpStorm.
 * User: Besat
 * Date: 5/23/2019
 * Time: 11:56 AM
 */

date_default_timezone_set('Asia/Tehran');
include 'Time/converterTime.php';

list($gy,$gm,$gd)=explode('-',date('Y-n-d'));

$j_date_string=gregorian_to_jalali($gy,$gm,$gd,'/'); //خروجی رشته

echo $j_date_string."-".date("H:i:s");