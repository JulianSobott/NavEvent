<?php
//require 'php/includes/DatenbankConnect.inc.php';

if (isset($_POST['anmelden']))
{
  if(isset($_POST['passwordLI']))
  {
    $password = $_POST['passwordLI'];
  }else{
    $error['passwordLI'] = "Please insert your password";
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
      $accountId = $result['id'];
      $_SESSION['nutzername'] = $result['nutzername'];
      session_write_close();
      header("Location: index.php?action=profil&accountId=$accountId");
      exit();
    }else{
      $error['passwordLI'] = "Wrong Password";
    }
  }else {
    $error['username'] = "Username doesn´t exist";
  }
}

?>


<div class="loginContainer">
  <h3 class="hLogin">Login</h3>
  <form class="login" action="" method="post">
    <label for="username">Username
      <input type="text" name="username" class="username <?php if(isset($error['username']))echo " errorClass"; ?>"
      value="<?php
      if(isset($_POST['username'])) echo $_POST['username'];?>" >
      <?php if(isset($error['username']))echo "<div class='error'>".$error['username']."</div>"; ?>
    </label>
    <label for="password">Password
      <input type="password" name="passwordLI" class="password <?php if(isset($error['passwordLI']))echo " errorClass"; ?>" value="" autocomplete="off">
      <?php if(isset($error['passwordLI']))echo "<div class='error'>".$error['passwordLI']."</div>"; ?>
    </label>
    <input type="submit" name="anmelden" class="btnAnmelden" value="Login" autocomplete="off">
  </form>
</div>
