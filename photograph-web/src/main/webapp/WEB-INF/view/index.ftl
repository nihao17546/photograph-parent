<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui">
    <title>收录优美图片_壁纸_热门_头像_高清</title>
    <meta name="keywords" content="壁纸,图片,手机壁纸,美图,发现,美文,唯美">
    <meta name="description" content="发现优美图片,高清手机壁纸">
    <meta name="referrer" content="never">
    <link rel="shortcut icon" href="http://static.nihaov.com/static/img/favicon.ico">
    <link href="/static/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/new.css?v=2" rel="stylesheet">

    <style>

    </style>
</head>
<body>
<script>
    var config = {
        <#if eckey??>
            a:"${eckey}"
        </#if>
    }
</script>
<div class="container-fluid">
    <div class="row">
        <div>
            <iframe id="md" src="/static/html/${md}.html" style="border: 0;margin: 0px;padding: 0px;" width="100%" height="200px"></iframe>
            <div class="logo_div">
                <div style="float: left">
                    <span class="logo_span">M</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="small_option_span" href="javascript:void(0)" onclick="find()">发现</a>&nbsp;&nbsp;&nbsp;
                    <a class="small_option_span" href="javascript:void(0)" onclick="newst()">最新</a>&nbsp;&nbsp;&nbsp;
                </div>
                <div style="float: right;">
                    <button class="btn btn-danger btn-sm" onclick="regist()">注册</button>
                    <button class="btn btn-default btn-sm" onclick="login()">登录</button>
                </div>
            </div>
            <div class="search_div">
                <form id="search_from" class="search_form" onsubmit="return submi()">
                    <div class="form-group">
                        <div class="input-group">
                            <input id="search_input" maxlength="8" name="key" type="text" class="form-control search_input" placeholder="搜索发现更多...">
                            <a href="javascript:void(0)" onclick="submi()" class="btn input-group-addon search_a">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            </a>
                        </div>
                    </div>
                    <div class="hot_div">热门搜索:
                        <#if hot??>
                            <#list hot as k>
                                <a href="javascript:void(0)" onclick="hotSearch(this)">${k}</a>
                            </#list>
                        </#if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="order">
        <span class="line"></span>
        <span style="white-space:pre"></span>
        <span id="page_span" page="find" class="txt">发现</span>
        <span style="white-space:pre"></span>
        <span class="line"></span>
    </div>
    <div class="row pic_div">
        <div id="put1" class="col-xs-3 put-div">
            <#if pic1??>
                <#list pic1 as obj>
                    <div class="img-div" onmouseover="mover(this)" onmouseleave="mleave(this)">
                        <div class="bar">
                            <div class="img-title">${obj.title}</div>
                        </div>
                        <img class="slide-img" alt="${obj.title}" img-width="${obj.width}" img-height="${obj.height}" large-src="${obj.src}" onclick="imgclick(this)" src="${obj.compressSrc}" onload="imgload(this)" onerror="imgerror(this)">
                    </div>
                </#list>
            </#if>
        </div>
        <div id="put2" class="col-xs-3 put-div">
        <#if pic2??>
            <#list pic2 as obj>
                <div class="img-div" onmouseover="mover(this)" onmouseleave="mleave(this)">
                    <div class="bar">
                        <div class="img-title">${obj.title}</div>
                    </div>
                    <img class="slide-img" alt="${obj.title}" img-width="${obj.width}" img-height="${obj.height}" large-src="${obj.src}" onclick="imgclick(this)" src="${obj.compressSrc}" onload="imgload(this)" onerror="imgerror(this)">
                </div>
            </#list>
        </#if>
        </div>
        <div id="put3" class="col-xs-3 put-div">
        <#if pic3??>
            <#list pic3 as obj>
                <div class="img-div" onmouseover="mover(this)" onmouseleave="mleave(this)">
                    <div class="bar">
                        <div class="img-title">${obj.title}</div>
                    </div>
                    <img class="slide-img" alt="${obj.title}" img-width="${obj.width}" img-height="${obj.height}" large-src="${obj.src}" onclick="imgclick(this)" src="${obj.compressSrc}" onload="imgload(this)" onerror="imgerror(this)">
                </div>
            </#list>
        </#if>
        </div>
        <div id="put4" class="col-xs-3 put-div">
        <#if pic4??>
            <#list pic4 as obj>
                <div class="img-div" onmouseover="mover(this)" onmouseleave="mleave(this)">
                    <div class="bar">
                        <div class="img-title">${obj.title}</div>
                    </div>
                    <img class="slide-img" alt="${obj.title}" img-width="${obj.width}" img-height="${obj.height}" large-src="${obj.src}" onclick="imgclick(this)" src="${obj.compressSrc}" onload="imgload(this)" onerror="imgerror(this)">
                </div>
            </#list>
        </#if>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 random_div">
            <button id="bottom_btn" class="btn btn-default" style="width:33%;" onclick="random()">换一批</button>
        </div>
    </div>
</div>

<nav class="navbar navbar-default" style="min-height: 25px;border-top: 1px solid gray;border-color: gray;background-color: gray;border-radius: 0px;margin-bottom: 0px;">
    <div class="navbar-inner navbar-content-center">
        <p class="text-muted credit" style="padding: 0px;margin: 5px;text-align: center;color: whitesmoke;font-size: 13px;font-weight: 200;">
            © 2017 nihao |
            <a onclick="about()" style="color: whitesmoke;font-size: 13px;font-weight: 200;" href="javascript:void(0)">关于本站</a> |
            <a style="color: whitesmoke;font-size: 13px;font-weight: 200;" href="javascript:void(0)">蜀ICP备17018005号</a>
        </p>
    </div>
</nav>

<div class="span" id="load-div">
    <div class="help"></div>
</div>


<div id="show-img-div" onclick="closeShowImg()">
    <div id="show-img-dd">
    </div>
</div>
<div id="show-tip"></div>

<script src="/static/libs/jquery.min.js"></script>
<script src="/static/libs/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/libs/layer/layer.js"></script>
<script src="/static/js/new.js?v=8"></script>
<script src="/static/js/tripledes.js"></script>
<script src="/static/js/mode-ecb.js"></script>
<script src="/static/js/md5.js"></script>
<script>

</script>
</body>
</html>