$(document).ready(function () {
    var add_html =
        '<div class="col-12 col-md-11 ml-md-5 ml-0 mt-5 study-set-card justify-content-center align-items-center d-flex"><div class="row" style="width: 100%; height: 100%;"><div class="col-3 d-flex align-items-center justify-content-center"><img src="picture/head1.jpg" style="width:100%; height:auto; border-radius: 50%;"></div><div class="col-8 d-flex flex-column justify-content-center align-items-center"><p class="study-set-title">Title</p><div class="study-set-content"><p class="study-set-content-font p-4"></p></div></div><div class="col-1 d-flex justify-content-center align-items-center"><a href="#" class="fa fa-arrow-right fa-3x study-set-detail-link"></a></div></div></div>';
    function autochangesize1() {
        var sidebar_top = $("header").height() + "px";
        $(".usercenter-sidebar").css("top", sidebar_top);
        $(".test1").css("top", sidebar_top);
    }
    $(".add-rows").click(function () {
        $(".add-study-set").before(add_html);
        autochangesize1();
    });
});