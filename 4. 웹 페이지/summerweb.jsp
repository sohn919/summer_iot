<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.attribute.BasicFileAttributes" %>
<%@ page import="java.nio.file.attribute.FileTime" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style>

.button {
  background-color: #6c757d;
  border-color: #6c757d;
  color: #fff;
  width: 500pt;   
  height: 20pt;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 20px;
  margin: 4px 2px;
  cursor: pointer;
}

</style>

</head>
<body>

	<%
		String path = "/var/lib/tomcat9/webapps/ROOT/recources";
		File f = new File( path );
		File[] files = f.listFiles();
		
		
		int count = 0;
		for (int i = 0; i < files.length; i++) {

		if ( files[i].isFile() ) {
	%>
		
		
	<%
		} else {
			
			String path2 = "/var/lib/tomcat9/webapps/ROOT/recources" + "//" + i;
			File f2 = new File( path2 );
			
			BasicFileAttributes attrs;
			
			attrs = Files.readAttributes(f2.toPath(), BasicFileAttributes.class);
			FileTime time = attrs.creationTime();
			    
			String pattern = "yyyy-MM-dd HH:mm";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				
		 	String formatted = simpleDateFormat.format( new Date( time.toMillis() ) );
			%>
			
			<div class="container">
			<div class="column" align="center">
				<p><a href="./imageload.jsp?id=<%=i%>" class="button" role="button"><%=formatted %> 움직임 감지  &raquo;</a>

			
			<%
		}
		}
	%>
	</div>
	</div>
</body>
</html>
