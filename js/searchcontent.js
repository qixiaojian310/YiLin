$(document).ready(function () {
  $(function () {
    $("#slider-range").slider({
      range: true,
      min: 0,
      max: 200,
      values: [75, 150],
      slide: function (event, ui) {
        $("#amount").val( ui.values[0] + " - " + ui.values[1]);
      }
    });
    $("#amount").val($("#slider-range").slider("values", 0) +
      " - " + $("#slider-range").slider("values", 1));
  });
  function changetitletop(){
    var title_top=$("header").height()+"px";
    $(".search-result-title").css("top",title_top);
  }
  changetitletop();
  $(window).resize(changetitletop());
})