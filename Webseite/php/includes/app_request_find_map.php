<?php
session_start();
error_reporting(E_ALL);
//Verbindung mit Datenbank herstellen
include 'DatenbankConnect.inc.php';

//Read from database
if(isset($_POST['query']))
{	
	//Find map data
	$query = $_POST['query'];
	$limit = 10;
	if( isset($_POST['limit']) ) $limit = $_POST['limit'];//Set another limit.
	$sql = "SELECT name, id, major_id, description FROM maps WHERE name LIKE '%" . $query . "%' ORDER BY name LIMIT " . $limit;
	$res = mysqli_query($con, $sql);

	echo mysqli_num_rows($res) . chr(0x0A);
	while( $result = mysqli_fetch_assoc($res) )
	{
		echo $result['name'] . chr(0x0A)
			. $result['id'] . chr(0x0A)
			. $result['major_id'] . chr(0x0A)
			. $result['description'] . chr(0x0A);
	}

}
else if(isset($_POST['majorID']))
{
	$con = mysqli_connect("localhost", "root", "password", "navevent01");

	//Find map data
	$majorID = $_POST['majorID'];
	$limit = 1;
	if( isset($_POST['limit']) ) $limit = $_POST['limit'];//Set another limit.
	$sql = "SELECT name, id, major_id, description FROM maps WHERE major_id = '" . $majorID . "' ORDER BY name LIMIT " . $limit;
	$res = mysqli_query($con, $sql);

	echo mysqli_num_rows($res) . chr(0x0A);
	while( $result = mysqli_fetch_assoc($res) )
	{
		echo $result['name'] . chr(0x0A)
			. $result['id'] . chr(0x0A)
			. $result['major_id'] . chr(0x0A)
			. $result['description'] . chr(0x0A);
	}

}
else echo "Wrong argument" . chr(0x0A);

?>
