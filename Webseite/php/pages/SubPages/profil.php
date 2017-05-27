<?php
$account_id = $_GET['accountId'];
?><input type="checkbox" id="toggleSidebar" name="" value="">
<div class="body">
  <label class="toggleSidebar" for="toggleSidebar">☰</label>
  <div class="seitenmenue">
    <h2>Navigation</h2>
    <ul class="navigation">
      <li>Meine Karten</li>
      <li>Profil bearbeiten</li>
      <li><a href="http://localhost/NavEvent/php/includes/logout.inc.php?action=logout">Logout</a></li>
    </ul>
    <h3>Anhang</h3>
    <ul class="anhang">
      <li><div class="material-icons icons SM settings">settings</div>
      Optionen</li>
      <li><div class="material-icons icons SM help">help</div>
      Hilfe</li>
      <li><div class="material-icons icons SM description">description</div>
      Impressum</li>
      <li class="linkSeiteAnpassen"><div class="material-icons icons SM description">edit</div>
      Seite Anpassen</li>
    </ul>
  </div>

  <div class="content">
    <div class="meineKarten">
      <h2>Meine Karten</h2>
      <div class="row kartenBilder">
        <div class="col-md-1 button btnMap btnNeueKarteHinzufuegen" onclick="mapEditor(<?php if(isset($_SESSION["nutzername"]))echo $_SESSION["accountId"]?>)">
          <div class="material-icons add">add</div>
          <p>Neue Karte hinzufügen</p>
        </div>
        <?php
        //$_SESSSION['accountId'] = 2;

        if(isset($account_id)){
          //print_r($_SESSSION);
          //$account_id = $_SESSSION['accountId'];
          $sql = "SELECT * FROM maps WHERE fk_account_id = '$account_id'";
          $res = mysqli_query($con, $sql);
          //echo mysqli_num_rows($res);
          while ($result = mysqli_fetch_assoc($res)) {
            //echo $result['id']."\n";
            $map_id = $result['id'];
            $img = $result['img_file'];
            $mime = explode('/', $result['mime']);
            $mime = $mime[0];
            $map_name = $result['name'];
            //echo $mime;
            ?>
            <div class=" col-md-1 button btnMap map_<?php echo $map_id?>">
              <img id="bild" alt="<?php echo $map_id ?>" src="http://localhost/NavEvent/uploads/<?php echo $img.'.'.$mime?>"/>
              <div class="map_name">
                <?php echo $map_name?>
              </div>
              <div class="option_field">
                <div class="o_field delete">
                  <i class="material-icons miDelete btnDelete">delete</i>
                </div>
                <div class="o_field edit">
                  <i class="material-icons miEdit btnEdit" onclick="edit(<?php echo $map_id; ?>)">mode edit</i>
                </div>
              </div>
            </div>
            <?php

          }
        }else {
          //echo "unset";
        } ?>

      </div>
      </div>
    </div>


  <div class="phpOutput">
    <?php
    $pictures = scandir("http://localhost/NavEvent/uploads");
    echo var_dump($pictures);
     ?>
  </div>
</div>
