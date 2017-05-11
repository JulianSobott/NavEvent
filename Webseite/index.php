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

    /*if(!isset($_SESSION['accountId']) && isset($_COOKIE['identifier']) && isset($_COOKIE['securitytoken'])){
      $identifier = $_COOKIE['identifier'];
      echo $identifier." ";
      $securitytoken = $_COOKIE['securitytoken'];
      echo $securitytoken;



      $statement = "SELECT * FROM securitytokens WHERE identifier ='$identifier'";
      $res = mysqli_query($con, $statement);
      $result = mysqli_fetch_assoc($res);
      echo "</br>".sha1($securitytoken);
      echo "</br>".$result['securitytoken'];

      if(sha1($securitytoken) !== $result['securitytoken']){
        echo('Ein vermutlich gestohlener Security Token wurde identifiziert');
      }else{
        $neuer_securitytoken = randomString();
        $neuer_securitytokenCrypted = sha1($neuer_securitytoken);
        $statement = "UPDATE securitytokens SET securitytoken = ".$neuer_securitytokenCrypted." WHERE identifier =".$identifier;
        setcookie("identifier", $identifier, time()+(3600*24*365));
        setcookie("securitytoken", $neuer_securitytoken, time()+(3600*24*365));

        $_SESSION['accountId'] = $result['user_id'];
        $accountId = $result['user_id'];
        $_SESSION['loggedIn'] = true;
        echo $_SESSION['accountId'];
        echo "string";
        $statement = "SELECT * FROM accounts WHERE id =".$accountId;
        $res = mysqli_query($con, $statement);
        $result = mysqli_fetch_assoc($res);
        $_SESSION['nutzername'] = $result['nutzername'];
      }
    }*/

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

  </script>
</html>
