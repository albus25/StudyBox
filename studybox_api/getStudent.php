<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","studyboxdb") or die(mysqli_error());

$studMob = $_REQUEST['studMob'];

$qry = "select * from tblstudent where studMob = '$studMob'";
$res = mysqli_query($con,$qry);

$result['stud'] = array();
while($row = mysqli_fetch_array($res))
{
	$stud = array();
	$stud['studID'] = $row[0];
	$stud['studName'] = $row[1];
	$stud['studMob'] = $row[2];
	$stud['studPass'] = $row[3];
	$stud['studAge'] = $row[4];
	$stud['studCity'] = $row[5];

	array_push($result['stud'],$stud);
}
echo json_encode($result);
?>