<?php
require_once ('class.Lambda.php');
$api = new Lambda;
$Name=$_GET["name"];//name of table
$Value=$_GET["value"];//value to look for.
$Data=$_GET["data"];//data to compair to value
echo $Name."<br>";
echo $Value."<br>";
echo $Data."<br>";

$api->delete($Name,$Value,$Data);

?>
