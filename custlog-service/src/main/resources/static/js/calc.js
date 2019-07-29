var loadtype  = $("#hkfs").val();

//一次性还本付息 设置
$(".calc-addon").on("click",'input[name="nyy"]',function(){
    //alert($(this).val());
    if($(this).val()==2){
        $('#hkfs').val(2);
    }
});

//还款方式
$("#hkfs").change(function(){
    var val = $(this).val();
    var dw = $('input[name="nyy"]:checked').val();
    /* alert($(this).val()+"1111111111111");
     alert(dw);*/
    if(dw==2 && val!==1){
        layer.msg("单位是天,只能有一次还本付息！", { time: 2000,offset: ['60px']});
        $('#hkfs').val(2);
    }
});

//重置
$("#btn-reset").on("click",function(){
    var resultList = $("#bxmx");
    var $table = $("#repayplan");

    resultList.fadeOut(0,function(){
        $table.find("tr:gt(0)").remove();
        $('input[name="nyy"]').eq(0).click();
        $("#jkje,#jkqx,#nhlv").val("");
        $("#tzjexs,#tzqxxs,#lxsy,#bxhj").html("");
    });

});


$("#btn-profit").on("click",function(){

    var jkje =  $('#jkje').val();
    var nhlv = $('#nhlv').val();
    var jkqx = $('#jkqx').val();
    var type = $("#hkfs").val();
    var $table = $("#repayplan");
    var resultLists = $("#bxmx");
    var loadtype = $('input[name="nyy"]:checked').val();
    //alert(jkje);
    if(jkje<100){
        layer.tips('投资金额输入不能小于100！',$('#jkje'), {tips: [3, '#53a0e3'],time: 2000});
        $("#jkje").val('').focus();
        return false;
    }
    if (nhlv<=0) {
        layer.tips('年利率输入不正确！',$('#nhlv'), {tips: [3, '#53a0e3'],time: 2000});
        $("#nhlv").val('').focus();
        return false;
    }
    if (parseFloat(jkqx)<=0) {
        layer.tips('投资期限输入不正确！',$('#jkqx'), {tips: [3, '#53a0e3'],time: 2000});
        $("#jkqx").val('').focus();
        return false;
    }
    if (parseFloat(nhlv) >50) {
        layer.tips('请输入正确的年利率，年利率不能超过50%！',$('#nhlv'), {tips: [3, '#53a0e3'],time: 2000});
        $("#nhlv").val('').focus();
        return false;
    }
    if (parseInt(jkqx)>48 && loadtype==1) {
        layer.tips('期限不能超过48个月！',$('#jkqx'), {tips: [3, '#53a0e3'],time: 2000});
        $("#jkqx").val('').focus();
        return false;
    }
    if(parseFloat(jkqx)!==parseInt(jkqx)){
        layer.tips('期限不能为小数！',$('#jkqx'), {tips: [3, '#53a0e3'],time: 2000});
        $("#jkqx").val('').focus();
        return false;
    }
    if (parseFloat(jkje)>2000000) {
        layer.tips('投标金额不能超过2000000',$('#jkje'), {tips: [3, '#53a0e3'],time: 2000});
        $("#jkje").val('').focus();
        return false;
    }
    if ( loadtype == 2 ){
        if(parseFloat(jkqx)>1095){
            layer.tips('借款期限不能超过1095天',$('#jkqx'), {tips: [3, '#53a0e3'],time: 2000});
            $("#jkqx").val('').focus();
            return false;
        }
        jkqx /=30;
    }

    resultLists.fadeOut(0,function(){
        $table.find('tr:gt(0)').remove();
        $("#tzjexs,#tzqxxs,#lxsy,#bxhj").html("");
    });


    switch(parseInt(type)){
        case 2:
            var sum = jkje * nhlv / 100 * (jkqx / 12);
            var sumfinal = sum.toFixed(2);
            resultLists.fadeOut(0);
            $("#tzjexs").html(jkje);
            if(loadtype ==1){
                $("#tzqxxs").html(jkqx);
            }else{
                $("#tzqxxs").html(jkqx.toFixed(1));
            }
            $("#bxhj").html((jkje * 1 + sumfinal * 1).toFixed(2));
            $("#lxsy").html(sumfinal);
            break;
        case 1:
            var sum = jkje * nhlv / 100* (jkqx / 12);
            var sumfinal = sum.toFixed(2);
            var sumfinalEve = (sumfinal / jkqx).toFixed(2);
            var objtitle = $("");

            if ( loadtype == 1 ){
                $("#tzqxxs").html(parseFloat(jkqx).toFixed(0));
            }else {
                $("#tzqxxs").html(parseFloat(jkqx).toFixed(1));
            }

            $("#tzjexs").html(parseFloat(jkje).toFixed(2));

            $("#bxhj").text((jkje * 1 + sumfinal * 1).toFixed(2));
            $("#lxsy").text(sumfinal);
            resultLists.fadeIn(0);

            for(var i=1;i<jkqx;i++){
                var objb = $(
                    "<tr style='height: 40px;'>"
                    + " <td align='center' >"
                    + i
                    + "</td>"
                    + " <td align='center' >"
                    + sumfinalEve
                    + "元</td>"
                    + " <td align='center' >0元</td>"
                    + " <td align='center' >"
                    + sumfinalEve
                    + "元</td>"
                    + " <td align='center' >"
                    + (parseFloat(jkje) * 1 + sumfinal * 1 - sumfinalEve
                    * i).toFixed(2)
                    + "元</td></tr>");
                $("#repayplan").append(objb);
            }
            var objb =
                " <tr style='height:40px'>"
                + " <td align='center'>"
                + jkqx
                + "</td>"
                + " <td align='center'>"
                + (parseFloat(jkje) * 1 + sumfinalEve * 1).toFixed(2)
                + "元</td>"
                + " <td align='center'>"
                + parseFloat(jkje).toFixed(2)
                + "元</td>"
                + " <td align='center'>"
                + sumfinalEve
                + "元</td>"
                + " <td align='center'>"
                + 0
                + "元</td></tr>";
            $("#repayplan").append(objb);
            break;
        case 3:
            resultLists.fadeIn(0);
            invest_calc(jkje,nhlv,jkqx);
            break;
    }

});




function invest_calc(jkje,nhlv,jkqx){
    var jkje =  parseInt($('#jkje').val());
    var nhlv = parseInt($('#nhlv').val());
    var jkqx = parseInt($('#jkqx').val());
    var Deadline = parseInt(jkqx);
    var hkfs = parseInt($("#hkfs").val());
    var datalist = new Array(jkqx);

    if(jkje){
        if(jkqx){
            if(nhlv){
                var tablett = $("tablett");

                datalist = Calculate(jkje, nhlv, jkqx);
                $("#tzjexs").html(jkje);
                if(jkqx>1){
                    $("#tzqxxs").html(jkqx);
                }else{
                    $("#tzqxxs").html(jkqx.toFixed(1));
                }
                $("#tzjexs").html(jkje.toFixed(2));

                for(var i=0;i<Deadline;i++){
                    var newTr = repayplan.insertRow(-1);
                    var newTd0 = newTr.insertCell(-1);
                    var newTd1 = newTr.insertCell(-1);
                    var newTd2 = newTr.insertCell(-1);
                    var newTd3 = newTr.insertCell(-1);
                    var newTd4 = newTr.insertCell(-1);

                    //这里修改与页面对应值
                    newTd0.innerHTML = datalist[i][0] + "";
                    newTd1.innerHTML = datalist[i][1] + "元";
                    newTd2.innerHTML = datalist[i][3] + "元";
                    newTd3.innerHTML = datalist[i][2] + "元";
                    newTd4.innerHTML = datalist[i][4] + "元";
                }
            }
        }
    }
}

function Calculate(jkje,nhlv,jkqx){
    var Deadline = parseInt(jkqx);
    var Amount = parseFloat(jkje);
    var Rate = parseFloat(nhlv) / 1200;
    var datalist = new Array(Deadline);
    var i;
    var a; // 偿还本息
    var b; // 偿还利息
    var c; // 偿还本金
    var TotalRate = (Amount * Deadline * Rate * Math.pow((1 + Rate),Deadline))/ (Math.pow((1 + Rate), Deadline) - 1) - Amount;
    //alert(TotalRate+"Amount!!!!!!!!!");
    var TotalRepay = TotalRate + Amount;//本金+利息
    var d = Amount + TotalRate; // 剩余本金
    //alert(d + "ddddddddddd");
    TotalRate = Math.round(TotalRate * 100) / 100;// 支付总利息
    //alert(TotalRate + "支付总利息");
    TotalRepay = Math.round(TotalRepay * 100) / 100;
    //alert(TotalRepay + "TotalRepay");
    a = TotalRepay / Deadline;
    //alert(a +" TotalRepay / Deadline;")
    a = Math.round(a * 100) / 100;// 支付总还款额



    for (i = 1; i <= Deadline; i++) {
        b = (Amount * Rate * (Math.pow((1 + Rate), Deadline) - Math.pow((1 + Rate), (i - 1))))/ (Math.pow((1 + Rate), Deadline) - 1);
        //alert(b + "bbbbbbbbbb");
        b = Math.round(b * 100) / 100;
        c = a - b;
        c = Math.round(c * 100) / 100;
        d = d - a;
        d = Math.round(d * 100) / 100;
        if (i == Deadline) {
            c = c + d;
            b = b - d;
            c = Math.round(c * 100) / 100;
            b = Math.round(b * 100) / 100;
            d = 0;
        }
        ;
        var tempList = new Array([ 5 ]);
        tempList[0] = i;// 期数
        tempList[1] = a.toFixed(2);// 偿还本息
        tempList[2] = b.toFixed(2);// 偿还利息
        tempList[3] = c.toFixed(2);// 偿还本金
        tempList[4] = d.toFixed(2);// 剩余本金
        datalist[i - 1] = tempList;
    }
    lab_totalmomey = (Math.round((Amount + TotalRate) * 100) / 100).toFixed(2);
    $("#lxsy").html(TotalRate);
    $("#bxhj").html(lab_totalmomey);
    return datalist;
}

function debx(){
    var jkje =  parseInt(10000);
    var nhlv = parseInt(0.12);
    var jkqx = parseInt(12);
    var ylv = nhlv / 100 / 12;
    var rest = jkje * ylv * Math.pow(1 + ylv, jkqx) / (Math.pow(1 + ylv, jkqx) - 1)*jkqx;
    return rest;
}
