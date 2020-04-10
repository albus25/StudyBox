<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","studyboxdb") or die(mysqli_error());

$studMob = $_REQUEST['studMob'];
$studPass = $_REQUEST['studPass'];

$qry = "select studID,studName,studMob from tblstudent where studMob = '$studMob' and studPass = '$studPass'";
$res = mysqli_query($con,$qry);

$result['stud'] = array();
while($row = mysqli_fetch_array($res))
{
	$stud = array();
	$stud['studID'] = $row[0];
	$stud['studName'] = $row[1];
	$stud['studMob'] = $row[2];

	array_push($result['stud'],$stud);
}
echo json_encode($result);
?>