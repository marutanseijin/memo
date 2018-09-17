$(function(){

    //show acording scroll point
    var scrollShowElment = $(".scroll-show");
    var showPoint = 20;
    scrollShowElment.hide();

    $(window).scroll(function () { 

        if($(this).scrollTop() > showPoint){

            scrollShowElment.fadeIn();

        } else {

            scrollShowElment.fadeOut();

        }
    });

});