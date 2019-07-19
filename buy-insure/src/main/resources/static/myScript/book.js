layui.use('form', function(){
    var form = layui.form;

    $.ajax({
        url: "/loadUserInfo",
        type: "post",
        dataType: "json",
        success: function (result) {
            if(result!=null && result!=undefined){
                $("[name=cust_name]").val(result.cust_name);
                $("[name=license]").find("option[value = '"+result.license_id+"']").attr("selected","selected");
                $("[name=cust_licenseno]").val(result.cust_licenseno);
                $("[name=cust_email]").val(result.cust_email);
                $("[name=cust_bir]").val(result.cust_bir);
                $("[name=cust_address]").val(result.cust_address);
                if(result.cust_sex=='男'){
                    $("#cust_nan").prop("checked",true);
                    $("#cust_nv").prop("checked",false);
                } else {
                    $("#cust_nan").prop("checked",false);
                    $("#cust_nv").prop("checked",true);
                }
                $("[name=cust_id]").val(result.cust_id);
            }
        }
    });



    form.render();

});