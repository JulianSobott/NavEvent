<?php
//session_name("session");
//session_start();?>
<nav class="navbar navbar-inverse navbar-static-top">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand " href="index.php?action=index">NavEvent</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <?php
        if (!isset($_SESSION['loggedIn'])) {
          $_SESSION['loggedIn'] = false;
        }
        if($_SESSION['loggedIn'])
        {
          ?>
          <li><a href="index.php?action=profil">Profile from <?php if(isset($_SESSION["nutzername"]))echo $_SESSION["nutzername"]?><span class="sr-only">(current)</span></a></li>
          <li><a href="php/includes/logout.inc.php?action=logout">Logout</a></li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">More <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="php/pages/KartenEditor.php?user=<?php if(isset($_SESSION["nutzername"]))echo $_SESSION["accountId"]?>" target="_blank">Create new map</a></li>
              <li><a href="http://omfgdogs.com/" target="_blank">Crazy Dogs</a></li>
            </ul>
          </li>
          <?php
        }else{
          ?>
          <li><a href="index.php?action=login">Login<span class="sr-only">(current)</span></a></li>
          <li><a href="index.php?action=register">Register</a></li>
          <?php
        }?>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
