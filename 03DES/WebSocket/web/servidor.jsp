<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="clases.SocketServidor"%>
<%
    SocketServidor server = new SocketServidor();
    for(SocketServidor ss : server.getConexiones()){
        
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
