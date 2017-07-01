<?php //require '../includes/DatenbankConnect.inc.php'; ?>
<?php
if(isset($_GET['user'])){
  $accountId = $_GET['user'];
  $_SESSION['accountId'] = $accountId;
}else if(isset($_SESSION['accountId'])){
  $accountId = $_SESSION['accountId'];
}

if (isset($_FILES['uploaddatei']))
{
    if (is_uploaded_file($_FILES['uploaddatei']['tmp_name'])) {
      $erlaubteEndungen = array('png', 'jpg', 'jpeg', 'gif');
      $filepath = '../../uploads/';
      $endung = strtolower(pathinfo($_FILES['uploaddatei']['name'],PATHINFO_EXTENSION));
      $bildinfo = pathinfo($_FILES['uploaddatei']['name']);
      if(in_Array($endung, $erlaubteEndungen)){
        $image = $_FILES['uploaddatei']['tmp_name'];
        $default_imageName = "bild";
        $data = addslashes(file_get_contents($image));
        $meta = getimagesize($image);
        $mime = $endung;
        $updated_at = time();
        $mapName = "default";
        $path = $filepath.$default_imageName.'.'.$endung;
        $id = 1;
        $imageName = $default_imageName.'_'.$id;
        $major_id = 0;
        $fk_account_id = $accountId;
        $statement = "INSERT INTO maps (name, major_id, img_file, mime, updated_at, fk_account_id) VALUES(
          '$mapName', '$major_id', '$imageName', '$mime', '$updated_at', '$fk_account_id')";
        $res = mysqli_query($con, $statement);
        $id = mysqli_insert_id($con);
        $imageName = $default_imageName.'_'.$id;
        $sql = "UPDATE maps SET img_file = '$imageName' WHERE id = '$id'";
        mysqli_query($con, $sql);
        $path = $filepath.$default_imageName.'_'.$id.'.'.$endung;
        move_uploaded_file($image, $path);
        header ("Location: KartenEditor.php?status=edit&id=$id");
      }else{
        $error['datei']="No suitable file selected2";
      }
    }else {
      $error['datei']="No suitable file selected1";
    }
}




?>
<div class="mitte">
  <div class="kartenContainer">

    <div class="bildUpload" style="<?php if(isset($_GET['status']))echo"display: none"?>">

      <form name="uploadformular" enctype="multipart/form-data" action="KartenEditor.php " method="post" >
        <input type="file" name="uploaddatei" size="60000000000" maxlength="25500000000"
        class="<?php if(isset($error['datei']))echo "errorClass"; ?>" value="<?php if(isset($_FILES['uploaddatei'])) echo $_FILES['uploaddatei']?>" >
        <?php if(isset($error['datei']))echo "<div class='error'>".$error['datei']."</div>"; ?>
        <input type="Submit" name="submit" value="Create map" class="btnUpload">
      </form>

      <div class="spinner spinner1"></div>
    </div>




  <div id="bildContainer"<?php if(!isset($_GET['status']))echo "style='display: none'"; ?>>
    <?php
    if (isset($_GET['status'])) {
      $id = $_GET['id'];
      $sql = "SELECT id, img_file, mime FROM maps WHERE id='$id'";
      $res = mysqli_query($con, $sql);
      while($result = mysqli_fetch_assoc($res)){
        $img = $result['img_file'];
        $mime = explode('/', $result['mime']);
        $mime = $mime[0];
        $map_id = $result['id'];
        $_SESSION['map_id'] = $map_id;

        echo '<img id="bild" alt="'.$map_id.'" src="../../uploads/'.$img.'.'.$mime.'"/>';
      }

      $sql = "SELECT * FROM beacons WHERE fk_map_id = '$map_id'";
      $res = mysqli_query($con, $sql);
      while($result = mysqli_fetch_assoc($res)){
        $pos_x = $result['pos_x'];
        $pos_y = $result['pos_y'];
        $minor_id = $result['minor_id'];
        $special = $result['fk_special'];
        $ordinary = $result['fk_ordinary'];
        ?>
        <div class="beaconContainer" id="beaconContainer-<?php echo $minor_id; ?>" style="left: <?php echo $pos_x; ?>%; top: <?php echo $pos_y; ?>%;">
          <span class="beacon beacon-<?php echo $minor_id; if($special != 0 || $ordinary != 0)echo ' specialBeacon';?>"></span>
        </div>
        <?php
      }
    }


    ?>
  </div>

  </div>
</div>
