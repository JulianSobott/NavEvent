<?php
session_name("session");
session_start();
session_unset();
session_destroy();

setcookie("identifier", "", time()-(3600*24*365));
setcookie("securitytoken", "", time()-(3600*24*365));
header("Location: http://localhost/NavEvent/");
?>
