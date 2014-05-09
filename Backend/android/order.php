<?php

require_once('class.Lambda.php');
$api = new Lambda;
$name="Order";
$table=$_GET["TableId"];
$data="null";
$api->Read($name,$table,$data);//if NULL does not work just send "all"
?>
