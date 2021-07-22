### 소스 코드 - summerweb.jsp

+ 파일을 읽어 올 경로를 지정해 줍니다.

      String path = "/var/lib/tomcat9/webapps/ROOT/recources";
      File f = new File( path );
      File[] files = f.listFiles();
#
 
+ 파일이 생성된 날짜를 저장해 줍니다.(=> 이것의 역할은 움직임이 감지되면 폴더가 생성되며 폴더가 생성된 날짜가 곧 움직임을 감지한 시간이 됩니다.)

      BasicFileAttributes attrs;
			
      attrs = Files.readAttributes(f2.toPath(), BasicFileAttributes.class);
      FileTime time = attrs.creationTime();
			    
      String pattern = "yyyy-MM-dd HH:mm";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

      String formatted = simpleDateFormat.format( new Date( time.toMillis() ) );
 
#

+ 지정된 경로에서의 폴더 개수만큼 버튼을 생성해 주고 imageload.jsp에 id값을 넘겨줍니다.

      <p><a href="./imageload.jsp?id=<%=i%>" class="button" role="button"><%=formatted %> 움직임 감지  &raquo;</a>
      
#

### 소스 코드 - imageload.jsp


+ summerweb.jsp에서 보낸 id값을 받아오고 id값을 통해 경로를 새로 지정해 줍니다.

      String id = request.getParameter("id");

      String path2 = "/var/lib/tomcat9/webapps/ROOT/recources" + "//" + id;
      
#

+ 새로운 경로를 통해 File객체를 생성해주고 이 경로에 있는 파일의 개수만큼 for문을 돌려 이미지를 생성해 줍니다.

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
      
#

# 결과



## 웹 스트리밍

![KakaoTalk_20210722_173946760_09](https://user-images.githubusercontent.com/77609451/126638733-f641ac1b-87b3-4c2d-b2ed-8c03df4ec7b2.png)



## 움직임 감지 버튼 생성

![KakaoTalk_20210722_173946760_08](https://user-images.githubusercontent.com/77609451/126638764-11471d14-d9d0-47a2-a07a-e4bce4e226bf.jpg)
