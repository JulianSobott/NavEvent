<?php
require '../../includes/DatenbankConnect.inc.php';

$id = addslashes($_GET['id']);
$id = $_GET['id'];
$result = mysql_query("SELECT `bild`, `mime` FROM `karten` WHERE `kartenId`='$id'");
$row = mysql_fetch_object($result);
header("Content-type: $row->mime");
echo $row->bild;

?>
