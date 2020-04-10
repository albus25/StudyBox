<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","studyboxdb") or die(mysqli_error());

$studID = $_REQUEST['studID'];
$qry = "select c.courseTitle,c.courseDescription,c.courseDuration,c.courseStartDate from tblstudybox c,tblstudent s,tblcoursedetail cd where cd.courseID = c.courseID and cd.studID = s.studID and cd.studID = '$studID'";

$res = mysqli_query($con,$qry);
$result['course'] = array();

while($row = mysqli_fetch_array($res))
{
	$course = array();
	$course['courseTitle'] = $row[0];
	$course['courseDescription'] = $row[1];
	$course['courseDuration'] = $row[2];
	$course['courseStartDate'] = $row[3];

	array_push($result['course'],$course);
}
echo json_encode($result);
?>