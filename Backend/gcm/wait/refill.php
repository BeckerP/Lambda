<?php

$json = array();

if(isset($_POST["regId"]) && isset($_POST["message"]) && isset($_POST["messageType"]) &&isset($_POST["tableNo"])) {
	$regId = $_POST["regId"];
	$message = $_POST["message"];
	$messageType = $_POST["messageType"];
	$tableNo = $_POST["tableNo"];

	include_once '../GCM.php';

	$gcm = new GCM();

	$registratoin_ids = array($regId);
	$message = array(
		"message" => $message,
		"tableNo" => $tableNo,
		"messageType" => $messageType
	);

	$result = $gcm->send_notification($registratoin_ids, $message, $messageType, $tableNo);

	echo $result;
}
?>
