<?php
session_start();
error_reporting(E_ALL);
//Verbindung mit Datenbank herstellen
//include 'DatenbankConnect.inc.php';

//------Daten aus Datenbank lesen--------
if(isset($_POST['mapName']))
{
	$con = mysqli_connect("localhost", "root", "password", "navevent01");
	
	$mapName = $_POST['mapName'];
	$sql = "SELECT * FROM map WHERE name='" . $mapName . "'";
	$res = mysqli_query($con, $sql);
	
	if( $result = mysqli_fetch_assoc($res) )//Select only first result, if more than one
	{
		echo $result['name'] . "
"			. $result['id'] . "
"			. $result['major_id'] . "
"			. $result['description'] . "
"			. $result['img_file'] . "
";
	}
}
else echo "Wrong argument";

?>
