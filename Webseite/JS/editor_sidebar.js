var editing = false;

$(function() {
  $('.seitenmenue').on('mouseenter', '.beaconInfoContainer', function(e) {
    id = this.className.split('-')[1];
    id = id.replace(' undo_show','');
    var background_beacon = $('.beacon-'+id).css('background');
    $('.beacon-'+id).css('box-shadow', '0 0 5px 3px #027E94');
    show_biDescription(id, "side");
  });

  $('.seitenmenue').on('mouseleave', '.beaconInfoContainer', function(e) {
    if (editing == false) {
      id = this.className.split('-')[1];
      id = id.replace(' show','');
      $('.beacon-'+id).css('box-shadow', '0 0 0 0 #027E94');
      undo_show_biDescription(id);
      updateSidebar();
    }
    if (!$(this).hasClass('actualInfoContainer')) {
      id = this.className.split('-')[1];
      id = id.replace(' show','');
      $('.beacon-'+id).css('box-shadow', '0 0 0 0 #027E94');
      undo_show_biDescription(id);
    }
  });

  $('.seitenmenue').on('click', '.beaconInfoContainer', function(e) {
    id = this.className.split('-')[1];
    id = id.replace(' show','');
    $('.actualInfoContainer').removeClass('actualInfoContainer');
    $(this).addClass('actualInfoContainer');
    $('.actualBeacon').removeClass('actualBeacon');
    $('.beacon-'+id).addClass('actualBeacon');
  });

  $('.seitenmenue').on('click', '.biName', function(e) {
    var pThis = this;
    editing = true;
    set_edit_infoContainer(pThis, "name");
  });

  $('.seitenmenue').on('click', '.biDescription', function(e) {
    var pThis = this;
    editing = true;
    set_edit_infoContainer(pThis, "description");
  });
});

function set_edit_infoContainer(pThis, field) {
  $('#tfName_side').remove();
  $('#tfDescription_side').remove();
  if (field == "name") {
    var name_text = $(pThis).text().replace(/\s/g, "");
    var input = '<textarea onfocusout="saveData(this.name, this.value) "id="tfName_side" class="eingabe tfName" type="text" name="name" rows="1">'+name_text+'</textarea>';
    $(pThis).replaceWith(input);
  }else {
    var description_text = $(pThis).text().replace(/\s/g, "");
    var textarea = '<textarea id="tfDescription_side" class="form-control" onfocusout="saveData(this.name, this.value)" type="text" name="description" rows="5">'+description_text+'</textarea>';
    $(pThis).replaceWith(textarea);
  }

}
