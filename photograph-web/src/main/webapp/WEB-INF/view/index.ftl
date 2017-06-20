<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui">
    <title>发现</title>
    <meta name="keywords" content="美图,发现,美文,唯美">
    <meta name="description" content="">
    <meta name="referrer" content="never">
    <link rel="shortcut icon" href="http://static.nihaov.com/static/img/favicon.ico">
    <#--<link href="http://static.nihaov.com/static/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
    <#--<link href="http://static.nihaov.com/static/libs/loader/loaderskit.css" rel="stylesheet">-->
    <link href="/static/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/libs/loader/loaderskit.css" rel="stylesheet">
    <link href="/static/libs/slide/css/simple.slide.css" rel="stylesheet">
    <link href="/static/css/main.css" rel="stylesheet">

    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button id="collapse-div" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img src="http://fdfs.nihaov.com/log.png" style="no-repeat;" height="22"></a>
            <form id="form1" style="display: none;" onsubmit="return submi(this)">
                <div class="input-group">
                    <input type="text" name="key" class="form-control search-input" placeholder="搜索发现更多...">
                    <span class="input-group-btn">
                    <button type="submit" class="btn btn-default font-color search-btn">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    </button>
                </span>
                </div>
            </form>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="top-li"><a id="find" class="top-btn" href="javascript:void(0)">图片<span class="sr-only">(current)</span></a></li>
                <li class="top-li"><a href="#" onclick="javascript:alert('此功能还未上线!')" class="top-btn">文章</a></li>
            </ul>
            <form id="form2" style="display: none;" onsubmit="return submi(this)">
                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" name="key" class="form-control search-input" placeholder="搜索发现更多...">
                    </div>
                    <button type="submit" class="btn btn-default search-btn">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    </button>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Link</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">我的收藏 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="javascript:alert('此功能还未上线!')">图片</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#" onclick="javascript:alert('此功能还未上线!')">文章</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div id="row-div" class="container">
    <div class="row">
        <div id="put1" class="col-xs-3 put-div"></div>
        <div id="put2" class="col-xs-3 put-div"></div>
        <div id="put3" class="col-xs-3 put-div"></div>
        <div id="put4" class="col-xs-3 put-div"></div>
    </div>
    <div id="top-btn">
        <button onclick="goToWhere(0)" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
        </button>
    </div>
</div>
<div class="span" id="load-div">
    <div class="help"></div>
</div>
<#--<script src="http://static.nihaov.com/static/libs/jquery.min.js"></script>-->
<#--<script src="http://static.nihaov.com/static/libs/bootstrap/js/bootstrap.min.js"></script>-->
<script src="/static/libs/jquery.min.js"></script>
<script src="/static/libs/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/libs/rgbaster.js"></script>
<script src="/static/libs/slide/js/simple.slide.js"></script>
<script src="/static/js/main.js"></script>
</body>
</html>