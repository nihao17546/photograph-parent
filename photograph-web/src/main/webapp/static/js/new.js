var page = $('#page_span');
var h1=0,h2=0,h3=0,h4=0;
var put1=$('#put1'),put2=$('#put2'),put3=$('#put3'),put4=$('#put4');
$('#load-div').height($(window).height());
var searchKey='',nextPage=1,pageSize=30,curPage=0,pageCount=0,recordCount=0;
var w = $('body').width(),h = $('body').height();
function imgerror(o) {
    $(o).prop("src", "http://fdfs.nihaov.com/404.jpg");
}
function imgload(o) {
    // h = $('body').height();
    // $('#show-img-div').height(h);
}
function mover(o) {
    $(o).find('.bar').show();
}
function mleave(o) {
    $(o).find('.bar').hide();
}
function imgclick(o) {
    $('body').css({
        "overflow-x":"hidden",
        "overflow-y":"hidden"
    });
    var image = {
        src:$(o).attr('large-src'),
        width:$(o).attr('img-width'),
        height:$(o).attr('img-height'),
        title:$(o).attr('alt')
    }
    $('#show-img-dd').width($(window).width())
    $('#show-img-dd').height($(window).height()*0.65)

    var img = new Image();
    img.src = pt(image.src.replace('\n',''));
    img.id = 'show-img';
    var ddW = $('#show-img-dd').width(),ddH = $('#show-img-dd').height();
    var ddHW = (ddW/ddH).toFixed(5);
    var imgHW = (image.width/image.height).toFixed(5);
    if(imgHW>ddHW){
        if(image.width > ddW){
            img.width = ddW;
        }
    }
    else{
        if(image.height > ddH){
            img.height = ddH;
        }
    }
    img.onerror = function (e) {
        imgerror(img)
        $(img).removeAttr('width')
        img.height = ddH;
    }
    $(img).hide()
    img.onload = function (e) {
        $(img).show()
    }
    $('#show-img-dd').append(img);
    $(img).click(function () {
        return false;
    })
    $('#show-tip').html(image.title).show()
    $('#show-img-div').show();
}
function find() {
    $('#load-div').show();
    exchange('find');
    $.ajax({
        type:'post',
        url:'/img/rand',
        dataType: "json",
        success: function (data) {
            reloadCache();
            for(var i=0;i<data.length;i++){
                appenPic(data[i]);
            }
            $('#load-div').hide();
        },
        error:function () {
            $('#load-div').hide();
        }
    })
}
function random() {
    var type = page.attr('page');
    if(type=='find'){
        $('#load-div').show();
        exchange('find');
        $.ajax({
            type:'post',
            url:'/img/rand',
            dataType: "json",
            success: function (data) {
                reloadCache();
                for(var i=0;i<data.length;i++){
                    appenPic(data[i]);
                }
                $('#load-div').hide();
            },
            error:function () {
                $('#load-div').hide();
            }
        })
    }
    else if(type=='search'){
        if(searchKey!=''){
            if(nextPage!=-1){
                search(false);
            }
            else{
                layer.msg('没有更多了')
            }
        }
    }
}
function reloadCache() {
    $('.put-div').html('');
    h1=0;h2=0;h3=0,h4=0;
}
function appenPic(obj) {
    var app='<div class="img-div" onmouseover="mover(this)" onmouseleave="mleave(this)"> ' +
        '<div class="bar"> ' +
        '<div class="img-title">'+obj.title+'</div> ' +
        '</div> ' +
        '<img class="slide-img" alt="'+obj.title+'" img-width="'+obj.width+'" img-height="'+obj.height+'" large-src="'+obj.src+'" onclick="imgclick(this)" src="'+obj.compressSrc+'" onload="imgload(this)" onerror="imgerror(this)"> ' +
        '</div> ';
    var wi = Math.min(obj.width, 200), he = Math.min(obj.height, 300);
    var to=he/wi;
    var appDiv=put1;
    if(h2<h1){
        if(h3<h2){
            if(h4<h3){
                appDiv=put4;
                h4=h4+to
            }
            else{
                appDiv=put3;
                h3=h3+to
            }
        }
        else{
            if(h4<h2){
                appDiv=put4;
                h4=h4+to
            }
            else{
                appDiv=put2;
                h2=h2+to;
            }
        }
    }
    else if(h3<h1){
        if(h4<h3){
            appDiv=put4;
            h4=h4+to
        }
        else{
            appDiv=put3;
            h3=h3+to;
        }
    }
    else{
        if(h4<h1){
            appDiv=put4;
            h4=h4+to
        }
        else{
            h1=h1+to;
        }
    }
    $(appDiv).append(app);
}
$(function () {
    $('#show-img-div').height($(window).height());
    window.onresize = function () {
        $('#show-img-dd').width($(window).width())
        $('#show-img-dd').height($(window).height()*0.65)
        if($('#show-img')){
            var ddW = $('#show-img-dd').width(),ddH = $('#show-img-dd').height();
            var imgW = $('#show-img').width(),imgH = $('#show-img').height();
            var imgHW = (imgW/imgH).toFixed(5);
            var ddHW = (ddW/ddH).toFixed(5);
            if(imgHW>ddHW){
                $('#show-img').removeAttr('height');
                if(imgW > ddW){
                    $('#show-img').prop('width',ddW);
                }
                else{
                    $('#show-img').removeAttr('width');
                }
            }
            else{
                $('#show-img').removeAttr('width');
                if(imgH > ddH){
                    $('#show-img').prop('height',ddH);
                }
                else{
                    $('#show-img').removeAttr('height');
                }
            }
        }
        $('#load-div').height($(window).height());
        w = $('body').width();
        h = $('body').height();
        $('#show-img-div').height($(window).height());
    }
    layer.open({
        type: 1,
        title: '提示',
        skin: 'layui-layer-demo',
        anim: 1,
        shadeClose: true,
        content: '' +
        '<div style="padding: 15px;width: 230px;margin: auto">' +
        '<p>微信扫描二维码,使用小程序,体验更多好玩功能。</p>' +
        '<img src="http://ox2n31sqv.bkt.clouddn.com/gh_09c56cbd2e5d_258-3.jpg" width="100%">' +
        '' +
        '</div>'
    });
})
function submi() {
    var o = $('#search_from');
    $('#search_input').blur();
    searchKey = $.trim($(o).find('input[name="key"]').val());
    nextPage=1;pageSize=30;curPage=0;pageCount=0;recordCount=0;
    if(searchKey!=''){
        reloadCache();
        exchange('search')
        search(true);
    }
    return false;
}
function search(record) {
    if(nextPage!=-1){
        $('#load-div').show();
        var param = {
            k:searchKey
        };
        var sort_type = $('#sort_type');
        if(sort_type){
            var t = $(sort_type).attr('t');
            if(t==2){
                param = {
                    sort:'image_date',
                    asc:'desc',
                    k:searchKey
                }
            }
            else if(t==3){
                param = {
                    sort:'image_date',
                    asc:'asc',
                    k:searchKey
                }
            }
        }
        param.record = record
        $.ajax({
            type:'post',
            url:'/img/que/'+nextPage+'/'+pageSize+'/'+searchKey,
            dataType: "json",
            data: param,
            success: function (data) {
                var list = data.data;
                var len = list.length;
                curPage = data.curPage;
                pageCount = data.pageCount;
                recordCount = data.recordCount;
                if(curPage==pageCount){
                    nextPage = -1;
                }
                else{
                    nextPage = curPage+1;
                }
                if(len>0){
                    for(var i=0;i<len;i++){
                        appenPic(list[i]);
                    }
                }
                else if(recordCount==0){
                    layer.msg('很抱歉，您搜索的内容不存在~');
                }
                if($('#recordCount').length < 1){
                    $(page).append('&nbsp;<span id="recordCount" style="font-size: 13px;float: right;">结果'+recordCount+'张</span>')
                }
                else{
                    $('#recordCount').html('结果'+recordCount+'张')
                }
                $('#load-div').hide();
            },
            error:function () {
                $('#load-div').hide();
            }
        })
    }
}
function exchange(type) {
    if(type=='find'){
        goToWhere(0);
        nextPage=1;pageSize=30;curPage=0;pageCount=0;recordCount=0;searchKey='';
        $(page).attr('page','find').html('发 现');
        $('#bottom_btn').html('换一批');
        $('#search_input').val('');
    }
    else if(type=='search'){
        $(page).html('搜 索: '+searchKey+'&nbsp;&nbsp;<a id="sort_type" href="javascript:void(0)" style="color: orangered;" t="2" onclick="searchSort(this)">收录时间⇩</a>').attr('page','search');
        $('#bottom_btn').html('加载更多');
    }
}
function searchSort(o) {
    layer.open({
        type: 1,
        title: '选择排序方式',
        skin: 'layui-layer-demo', //样式类名
        anim: 2,
        shadeClose: true, //开启遮罩关闭
        content: '<div style="padding: 15px;">' +
        '<button onclick="sortBtn(this)" t="1" class="btn btn-primary btn-sm">默认排序</button><br>' +
        '<button onclick="sortBtn(this)" t="2" style="margin-top: 5px;" class="btn btn-primary btn-sm">收录时间⇩</button>' +
        '<button onclick="sortBtn(this)" t="3" style="margin-top: 5px;" class="btn btn-primary btn-sm">收录时间⇧</button><br>' +
        '</div>'
    });
}
function sortBtn(o) {
    $('#sort_type').attr('t',$(o).attr('t')).html($(o).html());
    $('.layui-layer-close').click();
    nextPage=1;pageSize=30;curPage=0;pageCount=0;recordCount=0;
    if(searchKey!=''){
        reloadCache();
        search(false);
    }
}
function closeShowImg() {
    $('#show-tip').html('').hide()
    $('#show-img').remove();
    $('#show-img-div').hide();
    $('body').css({
        "overflow-x":"auto",
        "overflow-y":"auto"
    });
}
function login() {
    layer.open({
        type: 1,
        title: '正在开发中~',
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 0, //不显示关闭按钮
        anim: 2,
        shadeClose: true, //开启遮罩关闭
        content: '<div style="padding: 15px;">' +
        '账号你注册了吗~' +
        '</div>'
    });
}
function regist() {
    layer.open({
        type: 1,
        title: '正在开发中~',
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 0, //不显示关闭按钮
        anim: 2,
        shadeClose: true, //开启遮罩关闭
        content: '<div style="padding: 15px;">' +
        '告诉我你的手机号~' +
        '</div>'
    });
}
function hotSearch(o) {
    var hotKey = $(o).html();
    $('#search_input').val(hotKey);
    // submi();
    $('#search_input').blur();
    searchKey = hotKey;
    nextPage=1;pageSize=30;curPage=0;pageCount=0;recordCount=0;
    if(searchKey!=''){
        reloadCache();
        exchange('search')
        search(false);
    }
}
function newst() {
    layer.open({
        type: 1,
        title: '正在开发中~',
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 0, //不显示关闭按钮
        anim: 2,
        shadeClose: true, //开启遮罩关闭
        content: '<div style="padding: 15px;">' +
        '别点我,' +
        '点我也没用~' +
        '</div>'
    });
}
function about() {
    layer.open({
        type: 1,
        title: '使用微信扫一扫体验更多功能',
        skin: 'layui-layer-demo',
        anim: 2,
        shadeClose: true,
        content: '<div style="padding: 15px;width: 230px;">' +
        '<img src="http://ox2n31sqv.bkt.clouddn.com/gh_09c56cbd2e5d_258-3.jpg" width="100%">' +
        '' +
        '</div>'
    });
}
function pt(a){
    if(config&&config.a){
        var c = CryptoJS.enc.Utf8.parse(config.a);
        var decrypted = CryptoJS.DES.decrypt({
            ciphertext: CryptoJS.enc.Base64.parse(a)
        }, c, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypted.toString(CryptoJS.enc.Utf8);
    }
    else{
        return "http://fdfs.nihaov.com/404.jpg";
    }
}
var goToWhere = function (where){
    var me = this;me.site = [];me.sleep = me.sleep ? me.sleep : 16;me.fx = me.fx ? me.fx : 6;
    clearInterval (me.interval);
    var dh = document.documentElement.scrollHeight || document.body.scrollHeight;
    var height = !!where ? dh : 0;
    me.interval = setInterval (function (){
        var top = document.documentElement.scrollTop || document.body.scrollTop;
        var speed = (height - top) / me.fx;
        if (speed === me.site[0]){
            window.scrollTo (0, height);
            clearInterval (me.interval);}
        window.scrollBy (0, speed);me.site.unshift (speed);}, me.sleep);
};