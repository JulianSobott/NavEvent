<?php
session_start();
error_reporting(E_ALL);
//Verbindung mit Datenbank herstellen
//include 'DatenbankConnect.inc.php';

//Read from database
if(isset($_POST['query']))
{
	$con = mysqli_connect("localhost", "root", "password", "navevent01");
	
	//Find map data
	$query = $_POST['query'];
	$limit = 10;
	if( isset($_POST['limit']) ) $limit = $_POST['limit'];//Set another limit.
	$sql = "SELECT name, id, major_id, description FROM maps WHERE name LIKE '%" . $query . "%' ORDER BY name LIMIT " . $limit;
	$res = mysqli_query($con, $sql);
	
	echo mysqli_num_rows($res) . "
";
	while( $result = mysqli_fetch_assoc($res) )
	{
		echo $result['name'] . "
"			. $result['id'] . "
"			. $result['major_id'] . "
"			. $result['description'] . "
";
	}
	
}
else echo "Wrong argument";

?>
