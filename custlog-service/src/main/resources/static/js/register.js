$(function(){
    $("#msg").text("");
    var rand=Math.round(Math.random()*899999+100000);
    $("#sendCode").click(function(){
        //验证号码是否为可用的
        if($("[name=phone]").val()==""){
            alert("请填写正确的电话号码")
            return;
        }
        $.ajax({
            url:"http://utf8.api.smschinese.cn",
            data:{
                Uid:"李帆123",
                Key:"123cptbtptp",
                smsMob:$("[name=phone]").val(),
                smsText:"感谢您注册大特保,验证码1分钟内有效,验证码是"+rand
            },
            success:function(data){
                if(data>0){
                    alert("发送成功")
                }
            }
        })
    });
    $("#uname").blur(function(){
        var uname=$(this).val();
        if(uname==""){
            return;
        }
        $.post("check/"+uname+"",function(data){
            if(data=='true'){
                $("#msg").text("用户名可用");
                $("#loginBtn").prop("disabled",false);
                $("#loginBtn").css("backgroundColor","#ff6700");
                $("#loginBtn").css("cursor","pointer");
            }else{
                $("#msg").text("用户名已存在");
                $("#loginBtn").prop("disabled",true);
                $("#loginBtn").css("backgroundColor","gray");
                $("#loginBtn").css("cursor","default");
            }
        });

    });
    // $("#loginBtn").click(function(){
    //     if(rand!=$("#code").val()){
    //         alert("验证码错误");
    //         return false;
    //     }
    // })


})