function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

var picArray=[];
var h1=0,h2=0,h3=0,h4=0;
var put1=$('#put1'),put2=$('#put2'),put3=$('#put3'),put4=$('#put4');
$('#load-div').height($(window).height());
var searchKey='',nextPage=1,pageSize=30,curPage=0,pageCount=0,recordCount=0;
var ifSearching=false;
var w = $('body').width(),h = $('body').height();
if(w<768){
    $('#form2').hide();
    $('#form1').show();
}
else{
    $('#form1').hide();
    $('#form2').show();
}
$(function () {
    $('#find').click(function () {
        $('#load-div').show();
        nextPage=1;pageSize=30;curPage=0;pageCount=0;recordCount=0;searchKey='';
        reloadCache();
        removeLook();
        $('.search-input').val('');
        active($(this));
        $.ajax({
            type:'post',
            url:'/random',
            dataType: "json",
            success: function (data) {
                for(var i=0;i<data.length;i++){
                    appenPic(data[i]);
                }
                // layer.photos({
                //     photos: '#put',
                //     shift: 5
                // });
                $('#load-div').hide();
            },
            error:function () {
                $('#load-div').hide();
            }
        })
    });
    // $('#find').trigger("click");
    window.onresize=function () {
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
        $('#show-img-div').height(h);
        if(w<768){
            $('#form2').hide();
            $('#form1').show();
        }
        else{
            $('#form1').hide();
            $('#form2').show();
        }
    }
    $(window).scroll(function () {
        var winHeight=$(this).height();
        var docHeight=$(document).height();
        var topHeigth=$(this).scrollTop();
        if(topHeigth>winHeight){
            $('#top-btn').show();
        }
        else{
            $('#top-btn').hide();
        }
        if(topHeigth>=docHeight-winHeight){
            if(searchKey!=''){
                search();
            }
        }
        else if(topHeigth<=0){
        }
    })
})
function appenPic(obj) {
    var app='<div class="img-div" onmouseover="mover(this)" onmouseleave="mleave(this)"> ' +
        '<div class="bar"> ' +
        '<div class="img-title">'+obj.title+'</div> ' +
        '</div> ' +
        '<img class="slide-img" alt="'+obj.title+'" position="'+picArray.length+'" onclick="imgclick(this)" src="'+obj.compressSrc+'" onload="imgload(this)" onerror="imgerror(this)"> ' +
        '</div> ';
    var wi=obj.width,he=obj.height;
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
    picArray.push({
        id: obj.id,
        title: obj.title,
        src: obj.src,
        date: obj.date,
        width: obj.width,
        height: obj.height
    });
}
function reloadCache() {
    $('.put-div').html('');
    $('.notfound').remove();
    $('.nomore').remove();
    h1=0;h2=0;h3=0,h4=0;
    picArray.splice(0,picArray.length);
}
function mover(o) {
    $(o).find('.bar').show();
}
function mleave(o) {
    $(o).find('.bar').hide();
}
function imgerror(o) {
    $(o).prop("src", "http://fdfs.nihaov.com/404.jpg");
}
function imgload(o) {
    h = $('body').height();
    $('#show-img-div').height(h);
}
function imgclick(o) {
    $('body').css({
        "overflow-x":"hidden",
        "overflow-y":"hidden"
    });
    var image = picArray[$(o).attr('position')];
    $('#show-img-dd').width($(window).width())
    $('#show-img-dd').height($(window).height()*0.65)

    var img = new Image();
    img.src = image.src;
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
function submi(o) {
    $('.notfound').remove();
    $('.nomore').remove();
    searchKey = $.trim($(o).find('input[name="key"]').val());
    nextPage=1;pageSize=30;curPage=0;pageCount=0;recordCount=0;
    if(searchKey!=''){
        reloadCache();
        $('.top-li').removeClass('select-active');
        search();
    }
    return false;
}
function active(o) {
    $('.top-li').removeClass('select-active');
    $(o).parent().addClass('select-active');
}
function group(array, subGroupLength) {
    var index = 0;
    var newArray = [];

    while(index < array.length) {
        newArray.push(array.slice(index, index += subGroupLength));
    }

    return newArray;
}
function removeLook() {
    $('.LookPicture_Background').remove();
    $('.LookPicture').remove();
}
function search() {
    if(!ifSearching){
        if(nextPage!=-1){
            removeLook();
            $('#load-div').show();
            ifSearching=true;
            $.ajax({
                type:'post',
                url:'/query/'+nextPage+'/'+pageSize+'/'+searchKey,
                dataType: "json",
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
                        // layer.photos({
                        //     photos: '#put',
                        //     shift: 5
                        // });
                    }
                    else if(recordCount==0){
                        $('#row-div').append('<div class="notfound" style="text-align: center;color: lavender;">很抱歉，您搜索的内容不存在~</div>');
                    }
                    ifSearching=false;
                    $('#load-div').hide();
                },
                error:function () {
                    ifSearching=false;
                    $('#load-div').hide();
                }
            })
        }
        else if(!$('#no-more').length){
            $('#row-div').append('<div id="no-more" class="nomore" style="text-align: center;color: lavender;">我是有底线的~</div>');
        }
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

function closeShowImg() {
    $('#show-tip').html('').hide()
    $('#show-img').remove();
    $('#show-img-div').hide();
    $('body').css({
        "overflow-x":"auto",
        "overflow-y":"auto"
    });
}