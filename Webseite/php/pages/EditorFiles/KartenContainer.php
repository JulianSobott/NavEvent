<?php require '../includes/DatenbankConnect.inc.php'; ?>

<div class="mitte">
  <div class="kartenContainer">

    <div class="bildUpload" style="<?php if(isset($_GET['status']))echo"display: none"?>">

      <form name="uploadformular" enctype="multipart/form-data" action="http://localhost/NavEvent/php/pages/Karteneditor.php " method="post" >
        <input type="file" name="uploaddatei" size="60" maxlength="255" >
        <input type="Submit" name="submit" value="Datei hochladen" class="btnUpload">
      </form>

      <div class="spinner spinner1"></div>

      <?php
      // löscht alle Bilder aus den Verzeichnis
      //array_map('unlink', glob("../../uploads/*"));

      if (isset($_FILES['uploaddatei']))
      {
          if (is_uploaded_file($_FILES['uploaddatei']['tmp_name'])) {
            $erlaubteEndungen = array('png', 'jpg', 'jpeg', 'gif');
            $endung = strtolower(pathinfo($_FILES['uploaddatei']['name'],PATHINFO_EXTENSION));
            $bildinfo = pathinfo($_FILES['uploaddatei']['name']);
            if(in_Array($endung, $erlaubteEndungen)){
              $image = $_FILES['uploaddatei']['tmp_name'];
              $data = addslashes(file_get_contents($image));
              $meta = getimagesize($image);
              $mime = $meta['mime'];


              mysql_query("INSERT INTO `karten`
              (
                `kartenId`,
                `kartenName`,
                `bild`,
                `mime`,
                `fk_accountId`)
                VALUES(
                  Null,
                  'karte01',
                  '$data',
                  '$mime',
                  '1'
                );");
              header('Location: http://localhost/NavEvent/php/pages/Karteneditor.php?status=edit');

            }else{
              echo "<p class='pError'>ERROR: Keine passende Datei ausgewählt</p>";
            }
          }else {
            echo "<p class='pError'>ERROR: Keine passende Datei ausgewählt</p>";
          }
      }


    ?>
    </div>

    <div id="bildContainer"<?php if(!isset($_GET['status']))echo "style='display: none'"; ?>>
      <?php
      $result = mysql_query("SELECT kartenId FROM karten WHERE kartenName='karte02'");
      while($row = mysql_fetch_object($result)){
        echo '<img id="bild" alt="" src="EditorFiles/image.php?id='.$row->kartenId.'"/>';
      }

      ?>
    </div>

  </div>
</div>
