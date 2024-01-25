<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<style>
	div.title_area {
		display: block;
		width: 100%;
		min-height: 40px;
		position: relative;
		clear: both;
		padding: 4px 0 0px 0;
		border-bottom: solid 1px #979798;
	}

	div.title_box {
		margin-bottom: 5px;
		margin-top: 10px;
	}

	table.list_tbl01 {
		position: relative;
		width: 100%;
		border-top: solid 1px #D9D9D9;
		border-bottom: 1px solid #F0F0F0;
		border-collapse: collapse;
	}

	table.list_tbl01 > thead > tr > th {
		word-break: break-word;
		padding: 8px 4px;
		border-bottom: 1px solid #F0F0F0;
		color: #707070;
		font-weight: normal;
		word-break: break-word;
		background: #FCFCFC;
	}

	table.list_tbl01 > tbody > tr > td {
		text-align: left;
		border-bottom: 1px solid #F0F0F0;
		padding: 15px 5px;
		word-break: break-word;
	}

	.scrollit {
		overflow:scroll;
		height:300px;
	}

</style>
<body>
<div class="container">
	<div class="title_area">
		<h2>Quản lý báo cáo E-Tax</h2>
	</div>
	<div>
		<div class="title_box">
			<h4>Vui lòng chọn tất cả các tài liệu liên quan</h4>
		</div>
		<form action="http://localhost:8080/uploadFile" enctype="multipart/form-data" method="post">
			<input type="file" id="file" name="file" class="form-control" multiple="multiple">
			<br>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>


		<div class="title_box">
			<h4>Danh sách file đã tải lên(${countUpload})</h4>
		</div>

		<div class="scrollit">
			<table class="list_tbl01">
				<colgroup>
					<col style="width: 20%"/>
					<col style="width: 20%"/>
					<col style="width: 20%"/>
				</colgroup>

				<thead>
				<tr>
					<th scope="col">Số thứ tự</th>
					<th scope="col">Tên file</th>
					<th scope="col">File size</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="item" items="${result}">
					<tr>
						<td>${item.count}</td>
						<td>${item.fileName}</td>
						<td>${item.size}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

	<div>

		<div class="title_box">
			<h4>Danh sách file đã xử lý xong(${countDownload})</h4>
		</div>

		<table class="list_tbl01">
			<colgroup>
				<col style="width: 20%"/>
				<col style="width: 20%"/>
				<col style="width: 20%"/>
			</colgroup>

			<thead>
			<tr>
				<th scope="col">Số thứ tự</th>
				<th scope="col">Tên file</th>
				<th scope="col">File size</th>
			</tr>
			</thead>
			<tbody>
				<tr>
					<td>${resultDownload.count}</td>
					<td>${resultDownload.fileName}</td>
					<td>${resultDownload.size}</td>
				</tr>
			</tbody>
		</table>

		<form action="http://localhost:8080/downloadFile" enctype="multipart/form-data" method="get">
			<br>
			<button type="submit" class="btn btn-primary">Download All</button>
		</form>
	</div>
</div>

</body>
</html>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
