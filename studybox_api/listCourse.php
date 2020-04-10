<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","studyboxdb") or die(mysqli_error());

$courseCategory = $_REQUEST['courseCategory'];

$qry = "select courseID,courseTitle,courseDuration,courseStartDate from tblstudybox where courseCategory = '$courseCategory'";
$res = mysqli_query($con,$qry);
$result['course'] = array();

while($row = mysqli_fetch_array($res))
{
	$course = array();
	$course['courseID'] = $row[0];
	$course['courseTitle'] = $row[1];
	$course['courseDuration'] = $row[2];
	$course['courseStartDate'] = $row[3];

	array_push($result['course'],$course);
}
echo json_encode($result);
?>