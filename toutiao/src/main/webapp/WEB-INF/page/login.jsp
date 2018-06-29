<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



<c:if test="${! LoginType}">
<h2>非首页-请登录</h4>
<form action="/login/" method="post">
   username:<input type="text" name="username"><br>
   password:<input type="text" name="password" >
   <br>
   <input type="hidden" name="newsLink" value="${newsLink }">
   <input type="submit" value="Submit" name="submit">
</form>

</c:if>


<c:if test="${LoginType}">
<h2>首页登陆-请登录</h4>
<form action="/login/" method="post">
   username:<input type="text" name="username"><br>
   password:<input type="text" name="password" >
   <br>
  
   <input type="submit" value="Submit" name="submit">
</form>

</c:if>

</body>
</html>