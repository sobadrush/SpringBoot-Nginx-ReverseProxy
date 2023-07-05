<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>index.jsp</title>
    </head>
    <body>
        <h1 style="color:blue">Im index.jsp</h1>
        <%-- 取得 addFlashAttribute 的值 --%>
        <%-- addFlashAttribute方法：將參數放入了 session 中的 flashmap 中保存起來了，並且隱藏起來，不在瀏覽器中顯示參數，同時傳大對象也不受瀏覽器限制 --%>
        <%-- ref. https://www.cnblogs.com/pu20065226/p/10032048.html --%>
        ERR_MSG: ${sessionScope['org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS'][0]['ERR_MSG']}
        <br/>
        MESSAGE: ${sessionScope['org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS'][0]['MESSAGE']}
    </body>
</html>