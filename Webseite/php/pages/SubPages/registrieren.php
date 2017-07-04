<?php
//require 'php/includes/DatenbankConnect.inc.php';
if(isset($_POST['register']))
{

  if(strlen($_POST['username']) > 3)
  {
    $username = $_POST['username'];
    $sql = "SELECT nutzername FROM accounts WHERE nutzername ='$username'";
    $res = mysqli_query($con, $sql);
    $result = mysqli_fetch_assoc($res);
    if(isset($result['nutzername']))
    {
      $error['username'] = "Username already exists";
    }
  }else {
    $error['username'] = "Username have to be longer than 3 Charachters";
  }
  $email = $_POST['email'];
  if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
    $error['email'] = "No Valid Email";
  }else{
    $sql = "SELECT email FROM accounts WHERE email ='$email'";
    $res = mysqli_query($con, $sql);
    $result = mysqli_fetch_assoc($res);
    if(isset($result['email']))
    {
      $error['email'] = "E-Mail already exists";
    }
  }
  if(strlen($_POST['password']) > 4)
  {
    if ($_POST['password'] == $_POST['password2']) {
      $password = $_POST['password'];
      $password = password_hash($password, PASSWORD_DEFAULT, ['cost' => 12]);
    }else {
      $error['passwordNotEqual'] = "Passwords are not equal";
    }
  }else {
    $error['passwordShort'] = "Password have to be longer than 4 charachters";
  }
  if(!isset($error['username']) && !isset($error['passwordShort']) && !isset($error['passwordNotEqual']) && !isset($error['email']))
  {
    $statement = "INSERT INTO accounts (nutzername, email, passwort) VALUES ('$username', '$email', '$password')";
    if($res = mysqli_query($con, $statement)){
      $_SESSION['loggedIn'] = true;
      $sql = "SELECT * FROM accounts WHERE nutzername = '$username'";
      $res = mysqli_query($con, $sql);
      $result = mysqli_fetch_assoc($res);
      $accountId = $_SESSION['accountId'] = $result['id'];
      if ($_GET['from']=="guide") {
        header("Location: php/pages/KartenEditor.php?user=$accountId");
      }else {
        header("Location: index.php?action=profil");
      }
    }

  }
}
?>
<?php
if ($_GET['from']=="guide") {
  ?>
  <div class="info_register">
    <p>To create a map you first need to create an account.</p>
    <p>Already have an account? <a href="index.php?action=login">Login</a></p>
  </div>
  <?php
}
 ?>
 <a href="index.php?action=login"></a>
<div class="registerContainer">
  <h3 class="hRegister">Register</h3>
  <form class="register" id="register" action="" method="post">
    <label for="username">Username
      <input type="text" name="username" class="username <?php if(isset($error['username']))echo " errorClass"; ?>"
      value="<?php
      if (isset($_POST['username'])) echo $_POST['username']; ?>"
      >
      <?php if(isset($error['username']))echo "<div class='error'>".$error['username']."</div>"; ?>
    </label>
    <label for="email">Email
      <input type="email" name="email" class="email <?php if(isset($error['email']))echo " errorClass"; ?>"
      value="<?php
      if (isset($_POST['email'])) echo $_POST['email']; ?>"
      >
      <?php if(isset($error['email']))echo "<div class='error'>".$error['email']."</div>"; ?>
    </label>
    <label for="password">Password
      <input type="password" name="password" class="password <?php if(isset($error['passwordShort']))echo " errorClass"; ?>" value="<?php
      if(isset($_POST['password'])  )echo $_POST['password'] ?>" autocomplete="off">
      <?php if(isset($error['passwordShort']))echo "<div class='error'>".$error['passwordShort']."</div>"; ?>
    </label>
    <label for="password">Enter password again
      <input type="password" name="password2" class="password <?php if(isset($error['passwordNotEqual']))echo " errorClass"; ?>" value="<?php
      if(isset($_POST['password2']) && !isset($error['passwordNotEqual']))echo $_POST['password2'] ?>" autocomplete="off">
      <?php if(isset($error['passwordNotEqual']))echo "<div class='error'>".$error['passwordNotEqual']."</div>"; ?>
    </label>
    <input type="submit" name="register" class="btnRegister" value="Register">
  </form>
</div>
