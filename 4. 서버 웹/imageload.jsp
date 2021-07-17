<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.io.File" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" 	href="./recources/bootstrap.min.css">
</head>
<body>
	<%
		String id = request.getParameter("id");

		String path2 = "/var/lib/tomcat9/webapps/ROOT/recources" + "//" + id;
			
		File f2 = new File(path2);
		File[] files2 = f2.listFiles();
		for(int j = 0; j < files2.length; j++) {
			if ( files2[j].isFile() ) {
	%>
				<img src ="recources/<%=id %>/<%=files2[j].getName()%>" style ="width: 100%">
	<%
			}
		}
	%>
</body>
</html>
