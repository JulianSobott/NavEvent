<?php
if(!isset($_SESSION))
  session_start();
$sessionName = session_name();
$sessionId = session_id();
//session_name("session");
if (!isset($_GET['action'])){
  $_GET['action'] = "index";
}

require 'php/includes/DatenbankConnect.inc.php';
 ?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="robots" content="index,follow">
    <meta name="description" content="NavEvent is an App which helps you to find your way at events">
    <meta name="keywords" content="NavEvent, Navigation, indoor, Event, events">
    <title>NavEvent - App for Event Navigation</title>
    <link rel="icon" href="Bilder/NavEventLogo.jpeg">
    <link rel="stylesheet" href="CSS/bootstrap.min.css">
    <link rel="stylesheet" href="CSS/template.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src="JS/jquery.js"></script>
    <script src="JS/jquery-ui.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  </head>
  <body>
    <div class="container_index">

      <?php
      //include 'php\includes\app_request_get_map.php';
      /*if($_GET['action'] != "index" && isset($_GET['action']))*/ include 'php/includes/header.php';

      if(!isset($_SESSION['loggedIn']))
      {
        $_SESSION['loggedIn'] = false;
      }

      ?>
      <div class="content_index">
        <?php
            if($_GET['action'] == "index") include 'php/pages/start.php';
            if($_GET['action'] == "guide") include 'php/pages/guide.php';
            if($_GET['action'] == "editor") include 'php/pages/karteEinfuegen.php';
            if(!$_SESSION['loggedIn'] && $_GET['action']=="register") include 'php/pages/SubPages/registrieren.php'; //Design fertig
            if($_GET['action']=="about") include 'php/pages/SubPages/about.php';
            if(!$_SESSION['loggedIn'] && $_GET['action'] == "login") include 'php/pages/SubPages/anmelden.php'; //Design fertig
            if($_GET['action']=="impressum") include 'php/pages/SubPages/impressum.php';
            if($_GET['action']=="contact") include 'php/pages/SubPages/kontakt.php';
            if($_SESSION['loggedIn'] && $_GET['action']=='profil') include 'php/pages/SubPages/profil.php'; // TODO: profil Karte erstellen
         ?>
      </div>


      <?php
      include 'php/includes/footer.php';
      ?>
    </div>
  </body>
  <script src="JS/bootstrap.min.js"></script>
  <script src="JS/JavaScriptMain.js"></script>
  <script src="JS/JsProfil.js"></script>
  </script>
</html>
