<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>NavEvent</title>
    <link rel="stylesheet" href="CSS/index2.css">
    <link rel="stylesheet" href="CSS/bootstrap.min.css">
  </head>
  <body>
    <div class="container_page">
      <div class="landing">
        <?php include 'php/includes/header.php'; ?>
        <div class="landing_content">
          <div class="landing_info">
            Eine App zur Zurechtfindung auf Veranstaltungen
          </div>
          <div class="button landingButton btnNeueKarteHinzufuegen">
            Neue Karte erstellen
          </div>
          <div class="button landingButton btnGetApp">
            Get the App
          </div>

        </div>

      </div>

      <div class="container content">
        <div class="row">
          <h2 class="page-header">Über die App</h2>
          <div class="col-md-4">
            <img class="bild logo" src="Bilder/NavEventLogo.jpeg">
          </div>
          <div class="col-md-8">
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
              sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
              sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
              Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum
              dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
               dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores
                et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor
              sit amet.</p>
          </div>
        </div>
        <div class="row">
          <h2 class="page-header">Neue Karte hinzufügen</h2>
          <div class="col-md-8">
            <p>Hier können Sie eine neue Karte für ihre Veranstaltung entwerfen und individuell an ihre Bedürfnisse anpassen.
               Klicken Sie hiefür auf den Knopf <b>Neue Karte hinzufügen</b> und folgen Sie dem Guide der Sie durch das erstellen führen wird.</p>
               <button type="button" class="btn btn-default btn-lg btnNeueKarteHinzufuegen">
                 <span class="glyphicon glyphicon glyphicon-plus" aria-hidden="true"> Neue Karte erstellen</span>
               </button>
          </div>
          <div class="col-md-4">
            <img class="bild logo" src="Bilder/NavEventLogo.jpeg">
          </div>
        </div>
      </div>

    </div>
  </body>
</html>
