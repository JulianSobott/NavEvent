<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>NavEvent</title>
    <link rel="stylesheet" href="../../CSS/mainPage.css">
    <link rel="stylesheet" href="../../CSS/main.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
  </head>
  <body>
    <?php
    session_name("session");
    session_start();
    if(!isset($_SESSION['loggedIn']))
    {
      $_SESSION['loggedIn'] = false;
    }
    if (!isset($_GET['action'])) {
      $_GET['action'] = "main";
    }
    include '../includes/header.php';
    if(!$_SESSION['loggedIn'] && $_GET['action']=="register") include 'subPages/registrieren.php'; //Design fertig
    if($_GET['action']=="about") include 'subPages/about.php';
    if(!$_SESSION['loggedIn'] && $_GET['action']=="login") include 'subPages/anmelden.php'; //Design fertig
    if($_GET['action']=="impressum") include 'subPages/impressum.php';
    if($_GET['action']=="kontakt") include 'subPages/kontakt.php';
    if($_SESSION['loggedIn'] && $_GET['action']=='profil') include 'subPages/profil.php'; // TODO: profil Karte erstellen
    include '../includes/footer.php';
    ?>
  </body>
  <script src="../../JS/JsProfil.js"></script>
</html>
