<?php require '../includes/DatenbankConnect.inc.php'; ?>
<?php
session_start();
if(!isset($_SESSION['accountId'])){
  $_SESSION['accountId'] = 1;
}
// löscht alle Bilder aus den Verzeichnis
//array_map('unlink', glob("../../uploads/*"));

if (isset($_FILES['uploaddatei']))
{
    if (is_uploaded_file($_FILES['uploaddatei']['tmp_name'])) {
      $erlaubteEndungen = array('png', 'jpg', 'jpeg', 'gif');
      $filePath = 'F:\Programmieren\XAMPP\htdocs\NavEvent\uploads/';
      $endung = strtolower(pathinfo($_FILES['uploaddatei']['name'],PATHINFO_EXTENSION));
      $bildinfo = pathinfo($_FILES['uploaddatei']['name']);
      if(in_Array($endung, $erlaubteEndungen)){
        $image = $_FILES['uploaddatei']['tmp_name'];
        $default_imageName = "bild"; //TODO Name anpassen
        $data = addslashes(file_get_contents($image));
        $meta = getimagesize($image);
        $mime = $meta['mime'];
        $updated_at = time();
        $kartenName = "Karte02"; //TODO anpassen
        $accountId = $_SESSION['accountId'];
        //$_SESSION['timestamp'] = $updated_at;
        //echo $_SESSION['timestamp'];
        $path = $filepath.$default_imageName.'.'.$endung;
        if(file_exists($path)){
          $id = 1;
          do{
            $path = $filepath.$default_imageName.'_'.$id.'.'.$endung;
            $id++;
          }while(file_exists($path));
          $imageName = $default_imageName.'_'.$id;
        }

        $statement = "INSERT INTO karten (kartenName, bild, mime, updated_at, fk_accountId) VALUES('$kartenName', '$imageName', '$endung', '$updated_at', '$accountId')";
        $res = mysqli_query($con, $statement);
        $sql = "SELECT * FROM karten WHERE bild like %'$id'";
        move_uploaded_file($image, $path); //TODO Ordner name anpassen
        //$_SESSION['kartenId'] = $pdo->lastInsertId();
        header ("Location: http://localhost/NavEvent/php/pages/Karteneditor.php?status=edit&id=$updated_at");
      }else{
        $error['datei']="Keine passende Datei ausgewählt";
      }
    }else {
      $error['datei']="Keine passende Datei ausgewählt";
    }
}



?>
<div class="mitte">
  <div class="kartenContainer">

    <div class="bildUpload" style="<?php if(isset($_GET['status']))echo"display: none"?>">

      <form name="uploadformular" enctype="multipart/form-data" action="http://localhost/NavEvent/php/pages/Karteneditor.php " method="post" >
        <input type="file" name="uploaddatei" size="60" maxlength="255"
        class="<?php if(isset($error['datei']))echo "errorClass"; ?>" value="<?php if(isset($_FILES['uploaddatei'])) echo $_FILES['uploaddatei']?>" >
        <?php if(isset($error['datei']))echo "<div class='error'>".$error['datei']."</div>"; ?>
        <input type="Submit" name="submit" value="Karte erstellen" class="btnUpload">
      </form>

      <div class="spinner spinner1"></div>
    </div>




  <div id="bildContainer"<?php if(!isset($_GET['status']))echo "style='display: none'"; ?>>
    <?php
    if (isset($_GET['status'])) {
      $updated_at = $_GET['id'];
      $sql = "SELECT bild, mime FROM karten WHERE updated_at='$updated_at'";
      $res = mysqli_query($con, $sql);
      while($result = mysqli_fetch_assoc($res)){
        $bild = $result['bild'];
        $endung = $result['mime'];

        echo '<img id="bild" alt="" src="http://localhost/NavEvent/uploads/'.$bild.'.'.$endung.'"/>';
      }
    }


    ?>
  </div>

  </div>
</div>
