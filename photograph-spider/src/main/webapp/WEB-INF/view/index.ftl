<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui">
    <title>爬虫</title>
    <meta name="keywords" content="壁纸,图片,手机壁纸,美图,发现,美文,唯美">
    <meta name="description" content="发现优美图片,高清手机壁纸">
</head>
<body>

<table border="1">
    <tr>
        <td>爬虫状态</td>
        <td id="status" colspan="3">未知</td>
    </tr>
    <tr>
        <td>待处理图片数量</td>
        <td id="count" colspan="3">-1</td>
    </tr>
    <tr>
        <td>操作</td>
        <td><button onclick="start('TOPIT')">运行TOPIT</button></td>
        <td><button onclick="start('JUJU')">运行JUJU</button></td>
        <td><button onclick="start('UNSPLASH')">运行UNSPLASH</button></td>
        <td><button onclick="stop()">停止</button></td>
    </tr>
</table>
<hr>
<table border="1">
    <tr>
        <td>图片处理状态</td>
        <td id="img_status" colspan="2">未知</td>
    </tr>
    <tr>
        <td>操作</td>
        <td><button onclick="startSolr()">运行</button></td>
        <td><button onclick="stopSolr()">停止</button></td>
    </tr>
</table>
<hr>
<h5>JUJU cookie 操作</h5>
<table border="1">
    <tr>
        <td><button onclick="jujuCookies()">查看当前cookie</button></td>
        <td id="juju_cookies" colspan="2"></td>
    </tr>
    <tr>
        <td>name:<input type="text" id="name"></td>
        <td>value:<input type="text" id="value"></td>
        <td><button onclick="jujuSetCookie()">添加cookie</button></td>
    </tr>
    <tr>
        <td colspan="3"><button onclick="jujuReset()">重置cookie</button></td>
    </tr>
</table>
<hr>
<h5>Unsplash Client ID 操作</h5>
<table border="1">
    <tr>
        <td><button onclick="clientId()">查看当前Client ID</button></td>
        <td id="client_id" colspan="2"></td>
    </tr>
    <tr>
        <td>client id:<input type="text" id="client"></td>
        <td><button onclick="setClient()">设置Client ID</button></td>
    </tr>
</table>

<script src="/static/libs/jquery.min.js"></script>
<script>
    function setClient() {
        var client = $.trim($('#client').val());
        if(client == ''){
            alert('请填写参数')
            return;
        }
        $.ajax({
            type:'get',
            url:'/spider/unsplash/setClient',
            dataType: "json",
            data:{
                clientId: client
            },
            success: function (data) {
                if(data.code == 200){
                    $('#client').val('')
                    clientId();
                }
                else{
                    alert(data.message);
                }
            }
        })
    }
    function clientId() {
        $.ajax({
            type:'get',
            url:'/spider/unsplash/client',
            dataType: "json",
            success: function (data) {
                $('#juju_cookies').html('');
                if(data.code == 200){
                    if(data.client){
                        $('#client_id').html(data.client);
                    }
                    else{
                        $('#client_id').html('无');
                    }
                }
                else{
                    alert(data.message);
                }
            }
        })
    }
    function jujuReset() {
        $.ajax({
            type:'get',
            url:'/spider/juju/reset',
            dataType: "json",
            data:{
                name: na,
                value: va
            },
            success: function (data) {
                if(data.code == 200){
                    jujuCookies();
                }
                else{
                    alert(data.message);
                }
            }
        })
    }
    function jujuSetCookie() {
        var na = $.trim($('#name').val());
        var va = $.trim($('#value').val());
        if(na == '' || va == ''){
            alert('请填写参数')
            return;
        }
        $.ajax({
            type:'get',
            url:'/spider/juju/setCookie',
            dataType: "json",
            data:{
                name: na,
                value: va
            },
            success: function (data) {
                if(data.code == 200){
                    $('#name').val('')
                    $('#value').val('')
                    jujuCookies();
                }
                else{
                    alert(data.message);
                }
            }
        })
    }
    function jujuCookies() {
        $.ajax({
            type:'get',
            url:'/spider/juju/cookies',
            dataType: "json",
            success: function (data) {
                $('#juju_cookies').html('');
                if(data.code == 200){
                    if(data.cookies.length == 0){
                        $('#juju_cookies').html("无");
                    }
                    else{
                        var cookies = data.cookies;
                        var lis = '<ul>';
                        for(var i=0;i<cookies.length;i++){
                            lis = lis + '<li>' + cookies[i] + '</li>';
                        }
                        lis = lis + '</ul>';
                        $('#juju_cookies').html(lis);
                    }
                }
                else{
                    alert(data.message);
                }
            }
        })
    }
    function startSolr() {
        $.ajax({
            type:'get',
            url:'/spider/startSolr',
            dataType: "json",
            success: function (data) {
                alert(data.message);
            }
        })
    }
    function stopSolr() {
        $.ajax({
            type:'get',
            url:'/spider/stopSolr',
            dataType: "json",
            success: function (data) {
                alert(data.message);
            }
        })
    }
    function start(type) {
        $.ajax({
            type:'get',
            url:'/spider/start',
            dataType: "json",
            data:{
                type:type
            },
            success: function (data) {
                alert(data.message);
            }
        })
    }
    function stop() {
        $.ajax({
            type:'get',
            url:'/spider/stop',
            dataType: "json",
            success: function (data) {
                alert(data.message);
            }
        })
    }
    $(function () {
        window.setInterval(function () {
            $.ajax({
                type:'get',
                url:'/spider/status',
                dataType: "json",
                success: function (data) {
                    if(data.code == 200){
                        $('#status').html(data.status);
                        $('#count').html(data.count);
                        $('#img_status').html(data.imgStatus);
                    }
                    else{
                        alert(data.message);
                    }
                }
            })
        }, 1000 * 3)
    })
</script>
</body>
</html>