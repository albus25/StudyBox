<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","studyboxdb") or die(mysqli_error());

$studName = $_REQUEST['studName'];
$studMob = $_REQUEST['studMob'];
$studPass = $_REQUEST['studPass'];
$studAge = $_REQUEST['studAge'];
$studCity = $_REQUEST['studCity'];

$qry = "insert into tblstudent values(0,'$studName','$studMob','$studPass','$studAge','$studCity')";

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