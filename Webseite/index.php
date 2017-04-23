<?php
session_name("session");
session_start();

require 'php/includes/DatenbankConnect.inc.php';
 ?>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>NavEvent</title>
    <link rel="stylesheet" href="CSS/bootstrap.min.css">
    <!--<link rel="stylesheet" href="CSS/main.css">-->
    <link rel="stylesheet" href="CSS/template.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
  </head>
  <body>
    <?php
    include 'php/includes/header.php';

    if(!isset($_SESSION['accountId']) && isset($_COOKIE['identifier']) && isset($_COOKIE['securitytoken'])){
      $identifier = $_COOKIE['identifier'];
      $securitytoken = $_COOKIE['securitytoken'];

      $statement = $pdo->prepare("SELECT * FROM securitytokens WHERE identifier = ?");
      $result = $statement->execute(array($identifier));
      $securitytoken_row = $statement -> fetch();

      if(sha1($securitytoken) !== $securitytoken_row['securitytoken']){
        echo('Ein vermutlich gestohlener Security Token wurde identifiziert');
      }else{
        $neuer_securitytoken = randomString();
        $insert = $pdo->prepare("UPDATE securitytokens SET securitytoken = :securitytoken WHERE identifier = :identifier");
        $insert->execute(array('securitytoken' => sha1($neuer_securitytoken), 'identifier' => $identifier));
        setcookie("identifier", $identifier, time()+(3600*24*365));
        setcookie("securitytoken", $neuer_securitytoken, time()+(3600*24*365));

        $_SESSION['accountId'] = $securitytoken_row['user_id'];
        $_SESSION['loggedIn'] = true;
        echo $_SESSION['accountId'];
        echo "string";
        $statement = $pdo->prepare("SELECT * FROM accounts WHERE id = :id");
        $result = $statement->execute(array('id' => $_SESSION['accountId']));
        $user = $statement->fetch();
        $_SESSION['nutzername'] = $user['nutzername'];
      }
    }
    if(!isset($_SESSION['loggedIn']))
    {
      $_SESSION['loggedIn'] = false;
    }
    if (!isset($_GET['action'])){
      $_GET['action'] = "index";
    }
    if($_GET['action'] == "index") include 'php/pages/start.php';
    if($_GET['action'] == "guide") include 'php/pages/guide.php';
    if($_GET['action'] == "editor") include 'php/pages/karteEinfuegen.php';
    if(!$_SESSION['loggedIn'] && $_GET['action']=="register") include 'php/pages/subPages/registrieren.php'; //Design fertig
    if($_GET['action']=="about") include 'php/pages/subPages/about.php';
    if(!$_SESSION['loggedIn'] && $_GET['action'] == "login") include 'php/pages/subPages/anmelden.php'; //Design fertig
    if($_GET['action']=="impressum") include 'php/pages/subPages/impressum.php';
    if($_GET['action']=="kontakt") include 'php/pages/subPages/kontakt.php';
    if($_SESSION['loggedIn'] && $_GET['action']=='profil') include 'php/pages/subPages/profil.php'; // TODO: profil Karte erstellen
    include 'php/includes/footer.php';
    ?>

  </body>
  <script src="JS/bootstrap.min.js"></script>
  <script src="JS/JavaScriptMain.js"></script>
</html>
