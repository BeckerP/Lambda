<?php
require_once ('class.Lambda.php');
$api = new Lambda;
$Name=$_GET["name"];
$value=$_GET["value"];
$Data=$_GET["data"];//needs to be informat value1,value2,...

$api->Create($Name,$value,$Data);
//need a functioncall to push.
?>
