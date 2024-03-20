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

	div.date_form {
		margin-bottom: 5px;
		margin-top: 30px;
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
		height:250px;
	}

	.ui-datepicker-calendar {
		display: none;
	}

</style>
<body>
<div class="container">
	<div class="title_area">
		<h2>Manage E-Tax reports</h2>
	</div>
	<div>
		<div class="title_box">
			<h4>Please select all relevant documents</h4>
		</div>
		<form action="/uploadFile" enctype="multipart/form-data" method="post">
			<input type="file" id="file" name="file" class="form-control" multiple="multiple">
			<br>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>


		<div class="title_box">
			<h4>List of uploaded files(${countUpload})</h4>
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
					<th scope="col">No</th>
					<th scope="col">File Name</th>
					<th scope="col">File Size</th>
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

		<div class="date_form">
		</div>

	<%--		<div class="input-append date" id="datepicker" data-date="02-2012"--%>
<%--			 data-date-format="mm-yyyy">--%>

<%--			<input  type="text" readonly="readonly" name="date" >--%>
<%--			<span class="add-on"><i class="icon-th"></i></span>--%>
<%--		</div>--%>

		<div class="title_box">
			<h4>List of processed files(${countDownload})</h4>
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
				<th scope="col">No</th>
				<th scope="col">File Name</th>
				<th scope="col">File Size</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${resultDownload}">
				<tr>
					<td>${item.count}</td>
					<td>${item.fileName}</td>
					<td>${item.size}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>

		<div class="date_form">
			<div class="container">
				<div class="row">
					<div class="col-2">
						<label class="title_box">Select Month :</label>
					</div>

					<div class="col-4">
						<div class="col-lg-2">
							<div class='input-group date' id='datetimepicker'>
								<input type='text' class="form-control" name="selectMonth" id="selectMonth"/>
								<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar">
						</span>
					</span>
							</div>
						</div>
					</div>

				</div>
				<%--				</div>--%>
			</div>
		</div>

		<form action="/downloadFile" enctype="multipart/form-data" method="get" id="formDownload">
			<br>
			<button type="submit" class="btn btn-primary">Download</button>
		</form>
	</div>
</div>

</body>
</html>

<%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">--%>
<link rel="stylesheet" href="/css/bootstrap.min.css">

<!-- jQuery library -->
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>--%>
<script src="/css/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>--%>
<script src="/css/bootstrap.min.js"></script>

<%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>--%>
<script src="/js/jquery-ui.min.js"></script>

<%--<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">--%>
<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">

<%--<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment-with-locales.min.js"></script>--%>
<script src="/js/moment-with-locales.min.js"></script>

<%--<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>--%>
<script src="/js/bootstrap-datetimepicker.min.js"></script>


<script type="text/javascript">
	$(document).ready(function(){
		var now = new Date();
		$('#selectMonth').val((now.getMonth() + 1) + "/" + now.getFullYear())
	});

	$(function () {
		$('#datetimepicker').datetimepicker({
			viewMode: 'years',
			format: 'MM/YYYY'
		});
	});

	$('#formDownload').submit(function(){ //listen for submit event
		var month =  $('#selectMonth').val();
		$('<input />').attr('type', 'hidden')
				.attr('name', 'monthD')
				.attr('value', month)
				.appendTo('#formDownload');
		return true;
	});

</script>

