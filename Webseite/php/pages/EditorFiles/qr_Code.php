<script src="../../../JS/qrcode.min.js"></script>

<div id="qrcode"></div>
<script type="text/javascript">
var url = window.location.href;
var code = url;
var code = code.replace("http://localhost/NavEvent/php/pages/EditorFiles/qr_Code.php?map_id=", "");
console.log(code);
var qrcode = new QRCode(document.getElementById("qrcode"), {
text: code,
width: 128,
height: 128,
colorDark : "#000000",
colorLight : "#ffffff",
correctLevel : QRCode.CorrectLevel.H
});
</script>
