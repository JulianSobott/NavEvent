<?php
//Verbindung mit Datenbank herstellen
$connectionError = 'Verbindung fehlgeschlagen';

$host = 'localhost';
$username = 'root';
$password = '';
$mysqlDb = 'navevent01';

$con = mysqli_connect($host, $username, $password, $mysqlDb);
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
//var_dump($con);
?>
