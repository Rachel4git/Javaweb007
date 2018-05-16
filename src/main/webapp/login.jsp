<%@ page import="main.com.TokenProcesser" %><%--
  Created by IntelliJ IDEA.
  User: hd48552
  Date: 2018/5/16
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/token" method="post">
    <%--调用save方法 获取一个token 并将token保存在session中--%>
    <%--在请求该页面时即调用方法生成token并将其存放在session和表单的隐藏域中--%>
    <input type="hidden" name="AT_RACHEL" value="<%=TokenProcesser.getInstance().saveToken(request)%>">
    name:
    <input type="text" name="name">
        <br>
    password:
    <input type="text" name="password">
        <br>
        <%--提交表单时，将带有token的request发送到servlet--%>
        <%--第一次提交时，servlet处理请求并清除token--%>
        <%--如果此时点击返回，不刷新页面重新提交，使用的仍然是原session进行会话，不会产生新的token
        由于该sessionID对应的token已被清除，因此校验不通过，转发到sorry.jsp
        如果点击返回后刷新页面，使用的是新的session进行会话，和第一次提交时效果相同--%>
        <%--如果页面没有响应时，连续点击提交，因为第一次提交后即清楚token，因此后面的提交都不会验证通过 会转发到sorry.jsp--%>
    <input type="submit" name="submit">
        <br><br>
</form>
</body>
</html>
