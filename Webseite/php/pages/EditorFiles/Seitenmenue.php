<div class="seite seite-links <?php if(isset($_GET['status']))if($_GET['status']==edit)echo "animate-in"?>">
  <div class="seitenmenue">
    <?php
    if (isset($_SESSION['map_id'])){
      $map_id = $_SESSION['map_id'];
      $sql = "SELECT * FROM beacons WHERE fk_map_id = '$map_id'";
      $res = mysqli_query($con, $sql);
      while($result = mysqli_fetch_assoc($res)){
        $name = $result['name'];
        $description = $result['description'];
        ?>
        <div class="beaconInfoContainer">
          <div class="beaconInfo biName">
            <?php echo $name; ?>
          </div>
          <div class="beaconInfo biDescription">
            <?php echo $description; ?>
          </div>
          <div class="beaconInfo biSpecial">

          </div>
        </div>
        <?php
      }
    }

      ?>
  </div>
</div>
