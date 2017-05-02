<?php
require '../../includes/DatenbankConnect.inc.php';

$id = addslashes($_GET['id']);
$sql = "SELECT * FROM `karten` WHERE `kartenId`='$id'";
$res = mysqli_query($con, $sql);
$result = mysqli_fetch_assoc($res);
$mime = $result['mime'];
echo $mime;

header("Content-type: $mime");
echo $result['bild'];

?>
