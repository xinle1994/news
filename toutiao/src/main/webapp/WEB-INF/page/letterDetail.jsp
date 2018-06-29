<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>头条资讯 </title>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="keywords" content="读《Web 全栈工程师的自我修养》">
    <meta name="description" content="阅读影浅分享的读《Web 全栈工程师的自我修养》，就在牛客网。">

    <link rel="stylesheet" type="text/css" href="/styles/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/styles/font-awesome.min.css">

    <link rel="stylesheet" media="all" href="/styles/style.css">

    <script src="/scripts/hm.js"></script>
    <script src="/scripts/detail.js"></script>

    <script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.qrcode.min.js"></script>
    
       <script type="text/javascript" src="/scripts/jquery-1.12.4.min.js"></script>
</head>
<body class="welcome_index">

    <header class="navbar navbar-default navbar-static-top bs-docs-nav" id="top" role="banner">
        <div class="container">
            <div class="navbar-header">
                <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                  <span class="sr-only">Toggle navigation</span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                </button>

                <a href="" class="navbar-brand logo">
                 <h1>头条资讯</h1>
                  <h3>你关心的才是头条</h3>
                </a>
            </div>

            <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">

                    <ul class="nav navbar-nav navbar-right">
                    
                     <li class=""><a href="/index">首页</a></li>

                    
                    <c:if test="${user != null }">
                    <li><a href="/msg/list">站内信</a></li>
                     <li class=""><a href="/addPage">发帖</a></li>
                     <li class="js-login"><a href="javascript:void(0);">${user.name}</a>
                     </li>
                   
                     
              
                     <a class="list-head">
                        <img alt="头像" src="${user.headUrl}"><br>
                    </a>
                    
                     <li class="profile-item">
                      <li class=""><a href="/logout/">登出</a></li>
                    </c:if>
                    
                      <c:if test="${empty user.name }">
                    
                     <li class=""><a href="/logNews/news/${news.id }">登陆</a></li>
                      
                    <li class=""><a href="/regger">注册</a></li>
                    </c:if>
                   
                </ul>

            </nav>
        </div>
    </header>



<!-- ----------------------------显示 消息 主体 的  区域  ------------------------ -->
           <div id="main">
        <div class="container">
            <ul class="letter-chatlist">
                <c:forEach items="${messages }" var="msg">
                <li id="msg-item-4009580">
                    <a class="list-head">
                        <img alt="头像" src="${msg.user.headUrl}"><br>
                         
                    </a>
                    <div class="tooltip fade right in">
                        <div class="tooltip-arrow"></div>
                        <div class="tooltip-inner letter-chat clearfix">
                            <div class="letter-info">
                                <p class="letter-time">来自：${msg.user.name}<br><fmt:formatDate value="${msg.message.createdDate}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                                <a href="javascript:void(0);" id="del-link" name="4009580">删除</a>
                            </div>
                            <p class="chat-content">
                                ${msg.message.content}
                            </p>
                        </div>
                    </div>
                </li>
               </c:forEach>
         </ul>

        </div>
        
    </div>

      
   <!-- ----------------------------注脚区域  ------------------------ -->   

    <footer>
        <div class="container">
            <p class="text-center">
              
            </p>
            <p class="text-center">© 2013-2016 头条八卦</p>
        </div>

    </footer>

  <div id="quick-download">
        <button type="button" class="close-link btn-link" data-toggle="modal" data-target="#quick-download-app-modal"><i class="fa icon-times-circle"></i></button>

    <a class="download-link" href="http://nowcoder.com/download">
      <h3>牛客网</h3>
      <h4>程序员的首选学习分享平台</h4>
      <button type="button" class="btn btn-info btn-sm">下载 APP</button>
    </a>

    <div class="modal fade" id="quick-download-app-modal" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">提示</div>
          <div class="modal-body">
            <div class="checkbox">
              <label class="i-checks">
                <input id="already-installed" type="checkbox"><i></i> 我已安装了牛客网App，不再显示
              </label>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-block btn-default" id="close-quick-download-app-modal">关 闭</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script>
  </script>
  

</body></html>