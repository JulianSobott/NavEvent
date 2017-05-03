<?php
//require 'php/includes/DatenbankConnect.inc.php';

if (isset($_POST['anmelden']))
{
  if(isset($_POST['passwordLI']))
  {
    $password = $_POST['passwordLI'];
  }else{
    $error['passwordLI'] = "Bitte Passwort eingeben";
  }
  $username = $_POST['username'];
  $sql = "SELECT * FROM accounts WHERE nutzername = '$username'";
  $res = mysqli_query($con, $sql);

  $result = mysqli_fetch_assoc($res);
  if(isset($result['nutzername']))
  {
    if(password_verify($password, $result['passwort']))
    {
      $_SESSION['loggedIn'] = true;
      $_SESSION['accountId'] = $result['id'];
      $_SESSION['nutzername'] = $result['nutzername'];
      if(isset($_POST['angemeldetBleiben'])){
        $identifier = randomString();
        $securitytoken = $identifier;
        $securitytokenCrypted = sha1($securitytoken);
        $userId = $result['id'];

        if($insert = mysqli_query($con, "INSERT INTO securitytokens (user_id, identifier, securitytoken) VALUES ('$userId', '$identifier', '$securitytokenCrypted')")){
          setcookie("identifier",$identifier,time()+(3600*24*365)); //1 Jahr Gültigkeit
          setcookie("securitytoken",$securitytoken,time()+(3600*24*365));
        }
      }
      header("Location: http://localhost/NavEvent/index.php?action=profil");
    }else {
      $error['passwordLI'] = "Falsches Passwort";
    }
  }else {
    $error['username'] = "Benutzername nicht vorhanden";
  }
}

?>


<div class="loginContainer">
  <h3 class="hLogin">Login</h3>
  <form class="login" action="" method="post">
    <label for="username">Benutzername
      <input type="text" name="username" class="username <?php if(isset($error['username']))echo " errorClass"; ?>"
      value="<?php
      if(isset($_POST['username'])) echo $_POST['username'];?>" >
      <?php if(isset($error['username']))echo "<div class='error'>".$error['username']."</div>"; ?>
    </label>
    <label for="password">Passwort
      <input type="password" name="passwordLI" class="password <?php if(isset($error['passwordLI']))echo " errorClass"; ?>" value="" autocomplete="off">
      <?php if(isset($error['passwordLI']))echo "<div class='error'>".$error['passwordLI']."</div>"; ?>
    </label>
    <label for="angemeldetBleiben">Amgemeldet bleiben?<input type="checkbox" name="angemeldetBleiben" value="1"></label>
    <input type="submit" name="anmelden" class="btnAnmelden" value="Anmelden" autocomplete="off">
  </form>
</div>
