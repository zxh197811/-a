$(function(){
    $.ajaxSetup({
        beforeSend:function(request){
            var token=sessionStorage.getItem("token");
            if(token==null){
                request.setRequestHeader("token","");
            }else{
                request.setRequestHeader("token",token);
            }

        },
        xhrFields:{
            withCredentials:true
        }


    })
})
