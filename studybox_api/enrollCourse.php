<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","studyboxdb") or die(mysqli_error());

$studID = $_REQUEST['studID'];
$courseID = $_REQUEST['courseID'];
$studName = $_REQUEST['studName'];
$courseTitle = $_REQUEST['courseTitle'];
$courseDuration = $_REQUEST['courseDuration'];
$courseStartDate = $_REQUEST['courseStartDate'];

$qry = "insert into tblcoursedetail values(0,'$studID','$courseID','$studName','$courseTitle','$courseDuration','$courseStartDate')";

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