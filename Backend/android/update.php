<?php
require '/www/csce4444/.Lambd.LambdAPI.php';
$api = new Lambd;
$name=$_GET["table"];
$row=$_GET["item"];//selects the item row
$colum=$_GET["atr"];//selects the item atribute to modify
$value=$_GET["value"]//sets the colum name for search
$data= $_GET["info"];
api.Update($name,$row,$colum,$data,$value);
?>