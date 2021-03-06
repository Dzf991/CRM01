<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<base href="<%=basePath%>">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js" ></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js" charset="UTF-8"></script>
<script type="text/javascript">

	$(function(){
		$("#addBtn").click(function (){
			// document.getElementById("createActivity")
			var $create = $("#createActivity");
			// $create[0].reset();
			$(".time").datetimepicker({
				minView: "month",
				language:'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});

			$("#createActivityModal").modal("show");
			$.ajax({
				url:"workbench/activity/findUser",
				type:"get",
				dataType:"json",
				success:function (resp){

					if (resp !== null){
						$("#create-marketActivityOwner").append("<option> </option>")
						$.each(resp,function (i,user){
							$("#create-marketActivityOwner").append("<option value='"+user.id+"'>"+user.name+"</option>")
							$("#create-marketActivityOwner").val("${sessionScope.user.id}");

						})

					}
				}
			})

			$("#saveAcivity").click(function (){

				$.ajax({
					url: "workbench/activity/saveActivity",
					type: "post",
					data:{
						"owner": $("#create-marketActivityOwner").val(),
						"name": $("#create-marketActivityName").val(),
						"startDate":$("#create-startTime").val(),
						"endDate" :$("#create-endTime").val(),
						"cost":$("#create-cost").val(),
						"description":$("#create-describe").val(),
						"createBy": "${sessionScope.user.name}"
					},
					dataType: "json",
					success:function (resp){
						if (resp.success){
							alert("????????????");
							pageList(1
									,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						}else {
							alert("????????????");

						}
					}
				})

			})


		})



		pageList(1,2)

		$("#searchBtn").click(function(){
			$("#hidden-name").val($("#name").val())
			$("#hidden-owner").val($("#owner").val())
			$("#hidden-startDate").val($("#startDate").val())
			$("#hidden-endDate").val($("#endDate").val())
			pageList(1
					,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		});



		
		$("#checkAll").click(function (){
			$("[name='checkbox']").prop("checked",this.checked);
		})
		$("#activityeList").on("click",$("[name='checkbox']"),function (){
			$("#checkAll").prop("checked",$("[name='checkbox']").length === $("[name='checkbox']:checked").length )
		})



		$("#delActivity").click(function (){
			var parm = "";
			var checkIds = $("[name='checkbox']:checked");
			if (checkIds.length === 0){
				alert("??????????????????????????????");
			}else if (confirm("??????????????????????????????")){
				for (var i = 0; i < checkIds.length; i++) {
					parm = parm + "id=" + checkIds[i].value;
					if (i < checkIds.length - 1){
						parm = parm + "&";
					}

				}

				$.ajax({
					url: "workbench/activity/delActivity",
					type: "post",
					data: parm,
					dataType:"json",
					success:function (resp){
						if (resp.success){
							alert("????????????!");
							pageList(1
									,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						}else {
							alert("????????????!");
						}
					}
				})

			}





		})

		$("#updateBtn").click(function (){
			var $checkId = $("[name='checkbox']:checked");
			if ($checkId.length < 1){
				alert("??????????????????????????????");
			}else if ($checkId.length > 1){
				alert("????????????????????????");
			}else {
				$("#editActivityModal").modal("show");
				$.ajax({
					url:"workbench/activity/getActivityByAId",
					type:"post",
					data:{
						id:$checkId.val()
					},
					dataType:"json",
					success: function (resp) {
						$.each(resp.users,function (i,user) {
							$("#edit-marketActivityOwner").append("<option value='"+user.id+"' >"+user.name+"</option>")
						})
						$("#edit-marketActivityName").val(resp.activity.name);
						$("#edit-startTime").val(resp.activity.startDate);
						$("#edit-endTime").val(resp.activity.endDate);
						$("#edit-describe").val(resp.activity.description);
					}
				})

			}




			$("#editActivity").click(function () {
				$.ajax({
					url:"workbench/activity/editActivity",
					type:"post",
					data:{
						id:$checkId.val(),
						"owner": $("#edit-marketActivityOwner").val().trim(),
						"name": $("#edit-marketActivityName").val().trim(),
						"startDate":$("#edit-startTime").val().trim(),
						"endDate" :$("#edit-endTime").val().trim(),
						"cost":$("#edit-cost").val().trim(),
						"description":$("#edit-describe").val().trim(),
						"editBy": "${sessionScope.user.name}"
					},
					dataType:"json",
					success:function (resp) {
						if (resp.success){
							alert("????????????!");
							pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
									,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						}else {
							alert("????????????!");
						}
					}
				})
			})



		})
	});




	function pageList(pageNo,pageSize) {
		$("#checkAll").prop("checked",false)
		$("#name").val($("#hidden-name").val())
		$("#owner").val($("#hidden-owner").val())
		$("#startDate").val($("#hidden-startDate").val())
		$("#endDate").val($("#hidden-endDate").val())
		$.ajax({
			url:"workbench/activity/pageList",
			type:"get",
			data: {
				"name" : $.trim($("#name").val()),
				"owner" : $.trim($("#owner").val()),
				"startDate" : $.trim($("#startDate").val()),
				"endDate" : $.trim($("#endDate").val()),
				"pageNo":pageNo,
				"pageSize": pageSize
			},
			dataType:"json",
			success:function (resp){
				$("#activityeList").empty();
				$.each(resp.pageList,function (i,activity){
					$("#activityeList").append(	'<tr class="active">'+
							'<td><input type="checkbox" name="checkbox" value =  '+activity.id+' /></td>'+
							'<td><a style="text-decoration: none; cursor: pointer;" onclick=\"window.location.href=\'workbench/activity/getActivityDetails?id='+activity.id+'\'\">'+activity.name+'</a></td>'+
							'<td>'+activity.owner+'</td>'+
							'<td>'+activity.startDate+'</td>'+
							'<td>'+activity.endDate+'</td>'+
							'</tr>')
				})
				var totalPages = resp.total %pageSize===0 ? resp.total/pageSize :parseInt(resp.total / pageSize)+1;
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // ??????
					rowsPerPage: pageSize, // ???????????????????????????
					maxRowsPerPage: 20, // ?????????????????????????????????
					totalPages: totalPages, // ?????????
					totalRows: resp.total, // ???????????????

					visiblePageLinks: 3, // ??????????????????

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});
			}
		})
	}

	
</script>
</head>
<body>
	<input type="hidden" id="hidden-name">
	<input type="hidden" id="hidden-owner">
	<input type="hidden" id="hidden-startDate">
	<input type="hidden" id="hidden-endDate">
	<!-- ????????????????????????????????? -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">??</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">??????????????????</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" id="createActivity" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">?????????<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">??????<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">????????????</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">????????????</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">??????</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">??????</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
					<button type="button" class="btn btn-primary" id="saveAcivity" data-dismiss="modal">??????</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ????????????????????????????????? -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">??</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">??????????????????</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">?????????<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">??????<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="?????????">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">????????????</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">????????????</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">??????</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">??????</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">????????????Marketing??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
					<button type="button" id="editActivity" class="btn btn-primary" data-dismiss="modal">??????</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>??????????????????</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">??????</div>
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">?????????</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">????????????</div>
					  <input class="form-control" type="text" id="startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">????????????</div>
					  <input class="form-control" type="text" id="endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchBtn">??????</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn" ><span class="glyphicon glyphicon-plus"></span> ??????</button>
				  <button type="button" class="btn btn-default" id="updateBtn"><span class="glyphicon glyphicon-pencil"></span> ??????</button>
				  <button type="button" id="delActivity" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> ??????</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover" >
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>??????</td>
                            <td>?????????</td>
							<td>????????????</td>
							<td>????????????</td>
						</tr>
					</thead>
					<tbody id="activityeList">
<%--						<tr class="active">--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">?????????</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--							<td>2020-10-10</td>--%>
<%--							<td>2020-10-20</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">?????????</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>2020-10-10</td>--%>
<%--                            <td>2020-10-20</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage"></div>

			</div>
			
		</div>
		
	</div>
</body>
</html>