$(document).ready(function(){
    function autochangesize(){
        var sidebar_height=$(window).height()-$("header").height()+"px";
        var sidebar_top=$("header").height()+"px";
        $(".usercenter-sidebar").css("top",sidebar_top);
        $(".usercenter-sidebar").css("height",sidebar_height);
    }
    autochangesize();
    $(window).resize(autochangesize);
})
var mySwiper = new Swiper ('.swiper-container', {
    direction: 'horizontal', // 垂直切换选项
    loop: false, // 循环模式选项
    speed: 800,
    
    // 如果需要分页器
    pagination: {
      el: '.swiper-pagination',
    },
    
    // 如果需要前进后退按钮
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    
    // 如果需要滚动条
    scrollbar: {
      el: '.swiper-scrollbar',
    },
  })        