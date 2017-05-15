<input type="checkbox" id="toggleSidebar" name="" value="">
<div class="body">
  <label class="toggleSidebar" for="toggleSidebar">☰</label>
  <div class="seitenmenue">
    <h2>Navigation</h2>
    <ul class="navigation">
      <li>Meine Karten</li>
      <li>Profil bearbeiten</li>
      <li>Abmelden</li>
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
      <div class="kartenBilder">

      </div>
    </div>
    <div class="button map btnNeueKarteHinzufuegen">
      <div class="material-icons add">add</div>
      <p>Neue Karte hinzufügen</p>
    </div>
    <?php
    if(isset($_SESSSION['accountId'])){
      print_r($_SESSSION);
      $account_id = $_SESSSION['accountId'];
      $sql = "SELECT * FROM maps WHERE fk_account_id = '$account_id'";
      $res = mysqli_query($con, $sql);
      while ($result = mysql_fetch_assoc($res)) {
        echo result['id'];
      }
    }else {
      echo "unset";
    }
     ?>
  </div>
</div>
