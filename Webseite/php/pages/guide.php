<div class="container guideContent">
  <h1 class="page-header">Guide</h2>
  <div class="panel panel-primary">
    <div class="panel-heading">
      <h3 class="panel-title">The following things are required: </h3>
    </div>
    <div class="panel-body">
      <ul>
        <li>iBeacons (Depends on how many places you want to show)</li>
        <li>Android Smartphone to configure the iBeacons</li>
        <li>PC to create a map</li>
      </ul>
    </div>
  </div>

  <div class="panel panel-primary">
    <div class="panel-heading">
      <h3 class="panel-title">Step 1: Create map (PC)</h3>
    </div>
    <div class="panel-body">
      <ul>
        <li>Upload a picture of your building. You can upload it in the Editor when you click <b>start</b>(e.g. escape plan)</li>
        <li>Place the Beacons on the map through clicking. Place the Beacons there where you want to display information about the room/event</li>
        <li>Edit the Beacons. You can add a name, a description and set it to a special place. This Data will be shown later at the visitors smartphone</li>
        <li>To finish the Map, click on the upload button. You have to add a Name for the event and then youre ready for step 2.</li>
      </ul>
    </div>
  </div>

  <div class="panel panel-primary">
    <div class="panel-heading">
      <h3 class="panel-title">Step 2: Initialize the Beacons (Android Smartphone)</h3>
    </div>
    <div class="panel-body">
      <ul>
        <li>Go into the admin area by doing ...</li>
        <li>Scan the qr-code with your phone (try to hold your camera parallel to the screen)</li>
        <li>Prepare the beacon you want to configure:</li>
        <ul>
          <li>Plugin the batteries</li>
          <li>Be sure this beacon is the only one which is turned on in the local area</li>
          <li>In the app, select the beacon name which should be associated with beacon device</li>
          <li>Wait until the beacon was successfully configured or try again if it failed</li>
          <li>You may have to enter a pin</li>
          <li>Place the beacon device where you have defined it in your map
          Repeat step 3 for all your beacons</li>
        </ul>

      </ul>
    </div>
  </div>


  <a class="btn btn-default btn-lg" href="<?php if(isset($_SESSION['nutzername'])){echo 'php/pages/KartenEditor.php?user='.$_SESSION['accountId'];} else {echo 'index.php?action=register&from=guide';}?>" target="_blank" role="button">Start creating</a>
</div>
