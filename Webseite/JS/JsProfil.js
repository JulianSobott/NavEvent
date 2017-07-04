var map_id;
$(document).ready(function() {
  $('.content').on("swiperight", function() {
    ('#toggleSidebar').prop("checked", true);
  })
  $('body').on("swipeleft", function() {
    ('#toggleSidebar').prop("checked", false);
  })
});

function mapEditor(user_id) {
  window.location='php/pages/KartenEditor.php?user='+user_id;
}

function edit_map(map_id) {
  window.location='php/pages/KartenEditor.php?status=edit&id='+map_id;
}
$( function() {
  $( "#dialog_delete_map" ).dialog({
  resizable: false,
  autoOpen: false,
  height: "auto",
  width: 400,
  modal: true,
  buttons: {
    "Delete map": function() {
      delete_map();
      $( this ).dialog( "close" );
    },
    Cancel: function() {
      $( this ).dialog( "close" );
    }
  }
  });
} );
function dialog_delete_map(p_map_id) {
  map_id = p_map_id;
  $('#dialog_delete_map').dialog("open");
}

function delete_map() {
  $.ajax({
    type: "POST",
    url: "php/includes/datenbank.inc.php",
    data: {
      'map_id': map_id,
      'action': "delete_map",
    }
  }).success(function() {
    window.location='index.php?action=profil';
  }).fail(function() {
  });
}
