<?php
//Verbindung mit Datenbank herstellen
$connectionError = 'Verbindung fehlgeschlagen';

$host = 'localhost';
$username = 'root';
$password = '';

$mysqlDb = 'navevent01';

if (!@mysql_connect($host, $username, $password) || !mysql_select_db($mysqlDb)) {
  die('error'. mysql_error());
}
?>
