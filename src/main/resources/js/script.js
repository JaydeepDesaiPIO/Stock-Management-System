const toggleSidebar(){
    if($(".sidebar").is(":visible")){
    //hide
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
    }
   else{
   //show
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
   }
}