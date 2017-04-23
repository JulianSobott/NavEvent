<?php
$myfile = fopen("daten.txt", "a+") or die("Unable to open file!");
$name = $_POST['tfName'];
$name .= "\n";
fwrite($myfile, $name);
fclose($myfile);
?>
