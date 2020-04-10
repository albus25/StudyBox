<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","studyboxdb") or die(mysqli_error());

$courseCategory = $_REQUEST['courseCategory'];
$courseTitle = $_REQUEST['courseTitle'];
$courseDescription = $_REQUEST['courseDescription'];
$courseDuration = $_REQUEST['courseDuration'];
$courseStartDate = $_REQUEST['courseStartDate'];

$qry = "insert into tblstudybox values(0,'$courseCategory','$courseTitle','$courseDescription','$courseDuration','$courseStartDate')";

$res = mysqli_query($con,$qry);
if($res)
{
	$response['success'] = 1;
	echo json_encode($response);
}
else
{
	$response['success'] = 0;
	echo json_encode($response);
}
?>