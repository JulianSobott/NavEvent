
$(document).ready(function() {

});

function mapEditor(user_id) {
  window.location='http://localhost/NavEvent/php/pages/Karteneditor.php?user='+user_id;
}

function edit(map_id) {
  window.location='http://localhost/NavEvent/php/pages/Karteneditor.php?status=edit&id='+map_id;
}

function delete_map(map_id) {
  $.ajax({
    type: "POST",
    url: "../../includes/datenbank.inc.php",
    data: {
      'map_id': map_id,
      'action': "delete_map",
    }
  }).success(function() {
    alert("success");
  }).fail(function() {
    alert("fail");
  });
}
