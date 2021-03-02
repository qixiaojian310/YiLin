import $ from 'jquery';
import './css/homepage.css'

var width;
$(document).ready(function () {
    var height = $(window).height();
    $("#htc-1").css("height", height * 0.9 + "px");
    $("#htc-2").css("height", height * 0.9 + "px");
    function autochange_base() {
        //var height = $(window).height();
        width = $(window).width();
        var half_width = width / 2 + "px";
        // var homepage_height = width*0.4+"px"
        $("#htc-1").css("height", height * 0.9 + "px");
        $("#htc-2").css("height", height * 0.9 + "px");
        //调整每个块（content）的高让其刚好覆盖页面
        $(".homepage-background").css("height", height * 1.2 + "px");
        $(".homepage-content-nopicture").css("height", height * 0.9 + "px");
        $(".homepage-content-picture").css("height", height * 0.9 + "px");
        if (width > 576) {
            $("#htc-1").css("width", half_width);
            $("#htc-2").css("width", half_width);
        } else {
            $("#htc-1").css("width", width + "px");
            $("#htc-2").css("width", width + "px");
        }
        //$("#homepage-image1").css("height",height*0.6+"px")

    }
    autochange_base();
    //轮播
    $(window).resize(autochange_base);
    // document.getElementById('htc-1').style.width = half_width + "px";
    // document.getElementById('htc-2').style.width = half_width + "px";
    // document.getElementById('htc-1').style.height = half_width + "px";
    // document.getElementById('htc-2').style.height = half_width + "px";
    // document.getElementById('homepage-image1').width = half_width;
    $("#homepage-roll-text").html("Aaaaaaaaaaaaa");
    $("#homepage-roll-text").fadeIn(2500);
    $("#homepage-roll-text").fadeOut(500);
    var count = 0;
    setInterval(autochange, 4000);
    function show1() {
        $("#homepage-roll-text").html("Bbbbbbbbbbbb");
        $("#homepage-roll-text").fadeIn(2500);
        $("#homepage-roll-text").fadeOut(500);
    }
    function show2() {
        $("#homepage-roll-text").html("Cccccccccccc");
        $("#homepage-roll-text").fadeIn(2500);
        $("#homepage-roll-text").fadeOut(500);
    }
    function show0() {
        $("#homepage-roll-text").html("Aaaaaaaaaaaa");
        $("#homepage-roll-text").fadeIn(2500);
        $("#homepage-roll-text").fadeOut(500);
    }
    function autochange() {
        if (count == 0) {
            show1();
        }
        if (count == 1) {
            show2();
        }
        if (count == 2) {
            show0();
        }
        count = count + 1;
        if (count > 2) {
            count = 0;
        }
    }



    let faceTop = document.getElementById("face").getBoundingClientRect().top;
    $(".homepage-background").css("bottom", (-height * 0.9 - faceTop) * 0.1 + "px");
    $(window).scroll(function () {
        faceTop = document.getElementById("face").getBoundingClientRect().top;
        let faceBottom = document.getElementById("face").getBoundingClientRect().bottom;
        if (faceTop < height * 0.1 && faceBottom > 0) {
            $(".homepage-background").css("bottom", (-height * 0.9 - faceTop) * 0.15 + "px");
            $(".homepage-background").css("background", "url(\"../picture/background1.jpg\") no-repeat");
            $(".homepage-background").css("background-size", "120% 120%");
        }
        //获取intro相对于视窗的高
        let offsetIntro1Bottom = document.getElementById("intro1").getBoundingClientRect().bottom;
        let offsetIntro1Top = document.getElementById("intro1").getBoundingClientRect().top;
        if (offsetIntro1Top < height * 0.1 && offsetIntro1Bottom > 0) {
            $(".homepage-background").css("bottom", (0 - height * 1.8 - offsetIntro1Top) * 0.15 + "px");
            $(".homepage-background").css("background", "url(\"../picture/background4.jpg\") no-repeat");
            $(".homepage-background").css("background-size", "120% 120%");
        }
        let offsetIntro2Bottom = document.getElementById("intro2").getBoundingClientRect().bottom;
        let offsetIntro2Top = document.getElementById("intro2").getBoundingClientRect().top;
        if (offsetIntro2Top < height * 0.1 && offsetIntro2Bottom > 0) {
            $(".homepage-background").css("bottom", (0 - height * 1.8 - offsetIntro2Top) * 0.15 + "px");
            $(".homepage-background").css("background", "url(\"../picture/background2.jpg\") no-repeat");
            $(".homepage-background").css("background-size", "120% 120%");
        }
        let offsetIntro3Bottom = document.getElementById("intro3").getBoundingClientRect().bottom;
        let offsetIntro3Top = document.getElementById("intro3").getBoundingClientRect().top;
        if (offsetIntro3Top < height * 0.1 && offsetIntro3Bottom > 0) {
            $(".homepage-background").css("bottom", (0 - height * 1.8 - offsetIntro3Top) * 0.15 + "px");
            $(".homepage-background").css("background", "url(\"../picture/background3.jpg\") no-repeat");
            $(".homepage-background").css("background-size", "120% 120%");
        }
    })
})
