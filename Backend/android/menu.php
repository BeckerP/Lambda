<?php
require_once('class.Lambda.php');
$api = new Lambda;
$name="Menu";
$table="null";
$data="null";
$api->Read($name,$data,$table);//if NULL does not work just send "all"
?>
