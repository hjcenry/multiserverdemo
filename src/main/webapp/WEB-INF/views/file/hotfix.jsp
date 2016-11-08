<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.file {
	text-align: center;
	padding-top: 50px;
}

.file ul li {
	list-style-type: none;
	margin: 10px;
	text-align: center;
}

.btn {
	width: 60%;
	height: 50px;
	line-height: 50px;
	background-color: lightblue;
}

.btn:hover {
	opacity: 0.8;
}

.filediv {
	display: inline-block;
	width: 80%;
	text-align: center;
}

.fileversion {
	margin: 5px;
	width: 29%;
	line-height: 50px;
	display: inline-block;
	float: left;
	height: 50px;
	padding: 5px;
	background-color: lightblue;
}

.fileversion a {
	text-decoration: none;
	font-family: "微软雅黑";
	color: black;
}
</style>
<script type="text/javascript">
	function doUpload() {
		var formData = new FormData($("#uploadForm")[0]);
		$.ajax({
			url : '../file/fileupload',
			type : 'POST',
			data : formData,
			dataType : 'text',
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(data) {
				if (data == 'success') {
					alert('上传更新包成功');
				} else {
					alert('文件服务器错误');
				}
			},
			error : function(data) {
				console.log(data);
			}
		});
	}
</script>
</head>
<body>
	<div class="file">
		<ul>
			<li>
				<form id="uploadForm">
					更新包名： <input type="text" name="name" value="" />&nbsp;&nbsp;&nbsp;&nbsp;
					更新版本号： <input type="text" name="version" value="" />&nbsp;&nbsp;&nbsp;&nbsp;
					基础版本号： <input type="text" name="base" value="" /> <br /> <br />
					上传更新包：<input type="file" name="file" /> <input type="button"
						value="上传更新包" onclick="doUpload()" />
				</form>
			</li>
			<%-- <li>
				<p>
					点击下方列表下载要修改的CSV文件，修改保存并上传（<span style="color: red">按照列表中的文件名上传</span>），然后点击更新CSV表到服务器生效
				</p>
			</li>
			<li><div class="csvdiv">
					<c:forEach items="${csvNames }" var="name">
						<center>
							<div class="csvfile">
								<a href="../csv/csvdownload?name=${name }.csv" target="_blank">${name }.csv</a>&nbsp;&nbsp;&nbsp;&nbsp;<span>${name }</span>
							</div>
						</center>
					</c:forEach>
				</div></li> --%>
		</ul>
	</div>
</body>
</html>