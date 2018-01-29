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
        <td id="status" colspan="2">未知</td>
    </tr>
    <tr>
        <td>待处理图片数量</td>
        <td id="count" colspan="2">-1</td>
    </tr>
    <tr>
        <td>操作</td>
        <td><button onclick="start()">运行</button></td>
        <td><button onclick="stop()">停止</button></td>
    </tr>
</table>

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

<script src="/static/libs/jquery.min.js"></script>
<script>
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
    function start() {
        $.ajax({
            type:'get',
            url:'/spider/start',
            dataType: "json",
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