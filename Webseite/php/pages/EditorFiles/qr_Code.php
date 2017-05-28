<!DOCTYPE html>
<html>
  <head>
    <script src="../../../JS/qrcode.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="../../../CSS/qr_Code.css">
    <meta charset="utf-8">
    <title>NavEvent QR-Code</title>
  </head>
  <body>
    <div class="container">
      <div class="info">
        <p>Scannen Sie den QR-Code mit iherer <i>NavEvent App</i> um die Karte zu konfigurieren</p>
      </div>
      <div id="qrcode"></div>
      <div class="buttonContainer">
        <button type="button" name="button" class="button btnProfil">Profil</button>
        <button type="button" name="button" class="button btnEditMap">Karte weiter bearbeiten</button> <!--TODO Possible? -->
      </div>

    </div>

    <script type="text/javascript">
    $('document').ready(function() {
      $('.btnProfil').click(function() {
        window.location='../../../index.php?action=profil';
      });
      $('.btnEditMap').click(function() {
        window.location='http://localhost/NavEvent/php/pages/Karteneditor.php?status=edit&id=<?php echo $_GET["map_id"];?>';
      });
      var url = window.location.href;
      var code = url;
      code = code.replace("http://localhost/NavEvent/php/pages/EditorFiles/qr_Code.php?map_id=", "");

      console.log(code);
      var qrcode = new QRCode(document.getElementById("qrcode"), {
      text: code,
      colorDark : "#000000",
      colorLight : "#ffffff",
      correctLevel : QRCode.CorrectLevel.H
      });
    })

    </script  </body>
</html>
