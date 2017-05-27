
$(document).ready(function() {
  function mapEditor(user_id) {
    window.location='http://localhost/NavEvent/php/pages/Karteneditor.php?user='+user_id;
  }

  function edit(map_id) {
    window.location='http://localhost/NavEvent/php/pages/Karteneditor.php?status=edit&id='+map_id;
  }

});
