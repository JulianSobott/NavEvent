<?php
session_start();
require '../includes/DatenbankConnect.inc.php';

?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="../../CSS/bootstrap.min.css">
    <link rel="stylesheet" href="../../CSS/kartenEditor.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <!--<script src="//cloud.tinymce.com/stable/tinymce.min.js"></script>
    <script>tinymce.init({ selector:'textarea' });</script>-->
    <meta charset="utf-8">
    <title>Karte Bearbeiten</title>
  </head>
  <body>
    <?php include 'EditorFiles/EditorHeader.php'; ?>
    <div class="content">
      <?php
      include 'EditorFiles/Seitenmenue.php';
      include 'EditorFiles/KartenContainer.php';
      include 'EditorFiles/Formfeld.php';
      ?>

      <div class="publishMap" <?php if(!isset($_GET['status'])) echo 'style = "display: none;"' ?>>
        <button type="button" name="publishMap" class="btnPublish"><i class="material-icons miPublish">publish</i></button>
        <div class="textPublishMap">
          &nbspUpload and publish map&nbsp
        </div>
      </div>
      <div class="map_name">
        <div class="close">✖</div>
        <div class="fKartenKonfiguration" id="fKartenKonfiguration">
          <label for="kartenName">Name of the map: </label>
          <input type="text" id="tf_map_name" class="kartenName" name="kartenName" value="" placeholder="Example map">
          <input type="button" id="btnWeiter" name="submit" value="Continue">
        </div>
      </div>
      <div class="mask"></div>
    </div>
  </body>
  <script src="../../JS/JsKarteBearbeiten.js"></script>
  <script src="../../JS/kartenEditor.js"></script>
  <script src="../../JS/editor_sidebar.js"></script>
  <script src="../../JS/qrcode.min.js"></script>

</html>
