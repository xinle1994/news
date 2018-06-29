<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>hello  spring boot jsp</h4>
</body>
</html>
<!DOCTYPE html>
-->


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
                     <li class="js-login"><a href="javascript:void(0);">${user.name}</a></li>
                      <a class="list-head">
                        <img alt="头像" src="${user.headUrl}"><br>
                    </a>
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



<!-- ----------------------------显示 资讯 主体 的  区域  ------------------------ -->
       <div id="main">
        <div class="container">
            <div class="post detail">

                <div class="votebar">
                    <button class="click-like up" aria-pressed="false" title="赞同" value="${news.id}" name="${user.id}"><i class="vote-arrow"></i><span class="count">${news.likeCount}&nbsp;
                                <c:if test="${likeOrDisLike == 1}">
                                                                                                                           赞
                                </c:if> </span></button>
                    <button class="click-dislike down" aria-pressed="true" title="反对" value="${news.id}" name="${user.id}"><i class="vote-arrow"></i>
                    <c:if test="${likeOrDisLike == -1}">
                                                                                                                           踩
                                </c:if>
                    </button>
                </div>

                <div class="content" data-url="http://nowcoder.com/posts/5l3hjr">
                      <div class="content-img">
                          <img src="${news.image}" alt="">
                      </div>
                      <div class="content-main">
                          <h3 class="title"> 
                              <a target="_blank" rel="external nofollow" href="${news.link}">${news.title}</a>
                          </h3>
                          <div class="meta">
                              ${news.link}
                              <span>
                                  <i class="fa icon-comment"></i> ${news.commentCount}
                              </span>
                          </div>
                      </div>
                  </div>
            <div class="user-info">
                <div class="user-avatar">
                    <a href="/user/${owner.id}"><img width="32" class="img-circle" src="${owner.headUrl}"></a>
                </div>
                <!--
                <div class="info">
                    <h5>分享者</h5>

                    <a href="http://nowcoder.com/u/125701"><img width="48" class="img-circle" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x" alt="Thumb"></a>

                    <h4 class="m-b-xs">影浅</h4>
                    <a class="btn btn-default btn-xs" href="http://nowcoder.com/signin"><i class="fa icon-eye"></i> 关注TA</a>
                </div>
                -->
                </div>

                <div class="subject-name">来自 <a href="/user/${owner.id}">${owner.name}</a></div>
            </div>
         
<!-- -----------------------添加 评论的  区域-----显示评论框的 区域  ------------------------ -->
            <div class="post-comment-form">
               
                <span>评论 (${news.commentCount})</span>
                <!-- 用户登陆状态  显示评论框 -->
                  <c:if test="${user != null }">
                 
                    <form method="post" action="/addComment">
                  <div class="form-group text required comment_content">
                      <label class="text required sr-only">
                          <abbr title="required">*</abbr> 评论
                      </label>
                      <!-- 隐藏区 把被评论的资讯的 id 传到从controller -->
                      <input type="hidden" name="newsId" value="${news.id}"/>
                      <textarea rows="5" class="text required comment-content form-control" name="content" id="content"></textarea>
                  </div>
                  <div class="text-right">
                    <input type="submit" name="commit" value="提 交" class="btn btn-default btn-info">
                  </div>
                </form>
                 </c:if>
                 
             <!-- 用户未登录状态 -->
              <c:if test="${user == null }">
                <div class="login-actions">
                    <a class="btn btn-success" href="/logNews/news/${news.id }">登录后评论</a>
                </div>
               </c:if>
               
            </div>
<!-- ----------  -  显示 评论区 -------------------------------------------------------------------- -->
         <div id="comments" class="comments">
                <c:forEach items="${comments }" var="commentvo">
                <div class="media">
                    <a class="media-left" href="http://nowcoder.com/u/210176">
                        <img src="${commentvo.user.headUrl}">
                    </a>
                    <div class="media-body">
                        <h4 class="media-heading"> <small class="date">${commentvo.user.name}</small></h4>
                        <br>
                        <div>${commentvo.comment.content}</div>
                        <br>
                        <h4 class="media-heading"> <small class="date">发表于：<fmt:formatDate value="${commentvo.comment.createdDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                        &nbsp;  &nbsp;&nbsp; <c:if test="${user.id == commentvo.user.id }">
                         <a href="/deleteComment/${commentvo.comment.id}/${news.id} ">删除</a>
                        </c:if></small></h4>
                        
                        
                        
                    </div>
                </div>
               </c:forEach>
               
                  <br> 共有 ${pageInfo.totalPageNumber } 页 &nbsp; &nbsp; 当前
		${pageInfo.pageNo + 1} 页 &nbsp;&nbsp;
		<c:if test="${pageInfo.hasPrev }">
			<a href="/index?pn=0">首页</a>
     	&nbsp;&nbsp;
     	<a href="/index?pn=${pageInfo.prevPage }">上一页</a>
		</c:if>


		&nbsp;&nbsp;
		<c:if test="${pageInfo.hasNext }">

			<a href="/index?pn=${pageInfo.nextPage }">下一页</a>
     	  &nbsp;&nbsp;
     	<a
				href="/index?pn=${pageInfo.totalPageNumber-1 }">末页</a>
		</c:if>

		&nbsp;&nbsp; 转到 <input type="text" size="1" id="pageNo" /> 页
            </div>

      
      

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
<script type="text/javascript">
    $(function(){

    	//当点击  点赞 超链接时 ，发送 ajax 请求， 并实时 更新 点赞人数
		$(".click-like").click(function(){
			<%-- 若为未登录 状态，则直接返回 --%>
			var $name = this.name;
			if($name <= 0 || $name == null){
				return false;
			}
			
			var $value = this.value;
			$.ajax({
				url:"/likeNews/"+ $value,
				type:"GET",
				success:function(data){
					
					data = eval("("+data+")");
					var likeCount = data.likeCount;
					
					
					
					
					<%--
					var href = "http://localhost:8080/news" + "/" + $value;
					 --%>
					var href = "/news/"+ $value;
					window.location.href = href;
					window.location.href = href;
					
					
				}
			});
				
		});
    	
    	
		$(".click-dislike").click(function(){

			<%-- 若为未登录 状态，则直接返回 --%>
			var $name = this.name;
			if($name <= 0 || $name == null){
				return false;
			}
					var $value = this.value;
					$.ajax({
						url:"/disLikeNews/"+ $value,
						type:"GET",
						success:function(data){
							
							data = eval("("+data+")");
							var likeCount = data.likeCount;
							
							<%--
							var test = $(this).parent().find("span").html();
							
							alert(test);
							alert("bbb");
							 --%>
							
							 <%--
								var href = "http://localhost:8080/news" + "/" + $value;
								 --%>
								var href = "/news/"+ $value;
							window.location.href = href;
							
							
						}
					});
						
				});
		
	});
    
    </script>

</body></html>