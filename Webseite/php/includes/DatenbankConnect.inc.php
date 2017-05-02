<?php
//Verbindung mit Datenbank herstellen
$connectionError = 'Verbindung fehlgeschlagen';

$host = 'localhost';
$username = 'root';
$password = '';
$mysqlDb = 'navevent01';

$con = mysqli_connect($host, $username, $password, $mysqlDb);
//$pdo = new PDO('mysql:host=localhost;dbname=navevent01', 'root', '');

function randomString()
{
  if(function_exists('random_bytes')){
    $bytes = random_bytes(16);
    $str = bin2hex($bytes);
  }else if (function_exists('openssl_random_pseudo_bytes')) {
    $bytes = openssl_random_pseudo_bytes(16);
    $str = bin2hex($bytes);
  }else if (function_exists('mycrypt_create_iv')) {
    $bytes = mycrypt_create_iv(16, MCRYPT_DEV_URANDOM);
    $str = bin2hex($bytes);
  }else{
    $str = md5(uniqid('bLfjAoynrhowwqsF', true));
  }
  return $str;
}
/*if (!@mysql_connect($host, $username, $password) || !mysql_select_db($mysqlDb)) {
  die('error'. mysql_error());
}*/
?>
