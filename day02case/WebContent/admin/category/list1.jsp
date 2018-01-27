
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		
		$('#dg').datagrid({
			
			//servlet的请求路径,该请求为ajax请求,返回值是json
		    url:'${pageContext.request.contextPath }/adminCategory?method=findCategory',    
		    columns:[[    
		        {field:'cid',title:'ID',width:100,align:'center'},    
		        {field:'cname',title:'分类名称',width:100,align:'center'}, 
		        {field:'saber',title:'操作',width:100,align:'center',
		        	formatter: function(value,row,index){
						return "<a href='JavaScript:void(0)' onclick='findBycid(\""+row.cid+"\")'>修改</a>|<a href='JavaScript:void(0)' onclick='deleteBycid(\""+row.cid+"\")'>删除</a>";
					}

		        }    
		    ]],
		    //均分
		    fitColumns:true,
		 	// 只选中一个
		    singleSelect:true,
		    // 添加隔行换色
		    striped:true,
		    rownumbers:true,
		    toolbar: [{
				iconCls: 'icon-add',
				handler: function(){
					//alert('编辑按钮')
					$("#add_dialog").dialog('open');
			},
				text:"添加分类"
			}]
		}); 
	});
	//添加分类商品
	function saveCategory(){
		$("#add_form").form('submit',{
			url:"${pageContext.request.contextPath }/adminCategory?method=addCategory",
			success:function(data){
				//0.将data文本装换为json对象
				data = eval("("+data+")");
				//alert(data);
				//1.清空表单
				$("#add_form").form('clear');
				 //2.关闭对话框
				$("#add_dialog").dialog('close');
				//3.展示提示信息
				$.messager.show({
					title:'添加分类提示信息',
					msg:data.msg,
					timeout:2000,
					showType:'slide'
				});
				//4.刷新数据表格
				$("#dg").datagrid('load');
			}
		});
	};
	//查找分类
	function findBycid(cid){
		//alert(cid);
		//自动填充到对话框中
		$("#update_form").form("load","${pageContext.request.contextPath}/adminCategory?method=getBycId&cid="+cid);
		$("#update_dialog").dialog('open');
		
	}
	//修改商品
	function updateCategory(){
		//$("#update_dialog").dialog('close');
		$("#update_form").form('submit',{
			url:"${pageContext.request.contextPath }/adminCategory",
			success:function(data){
				//0.将data文本转换问json
				data=eval("("+data+")");
				//alert(data);
				//1.清空表单
				 $("#update_form").form('clear');
				//2.关闭对话框
				$("#update_dialog").dialog('close');
				//3.提示信息显示
				 $.messager.show({
					title:'添加分类提示信息',
					msg:data.msg,
					timeout:2000,
					showType:'slide'
				});
				//4.刷新数据表格
				$("#dg").datagrid('load'); 
			}
		});
		//提交表单
		//$("#update_form").submit();
	}
	function deleteBycid(cid){
		$.messager.confirm('确认','您确认想要删除记录吗?',function(r){
			if(r){
				$.post("${pageContext.request.contextPath }/adminCategory?method=delete&id="+cid,function(data){
					$.messager.alert('警告',data.msg);
		    		//刷新表格
					$("#dg").datagrid("reload");
				},"json");
			}
		});
		
	}
</script>
</head>
<body>
	<!-- 数据表格 -->
	<table id="dg"></table>
	<!-- 添加分类的对话框 --> 
	<div id="add_dialog" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	   <!-- 添加一个表单 -->
	   <form method="post" id="add_form">
	   		<!-- 请求方法标识 -->
	   		<input type="hidden" name="method" value="addCategory" />
	   		<table width="100%">
	   			<tr align="center">
	   				<td>
	   					分类名称:
	   				</td>
	   				<td>
	   					<input type="text" class="easyui-textbox" name="cname"><br>
	   				</td>
	   			</tr>
	   			<tr  align="center">
	   				<td colspan="2">
	   					<input type="button" class="easyui-linkbutton" value="保存" onclick="saveCategory()" style="width:80px"/>
	   				</td>
	   			</tr>
	   		</table>
	   	</form>   
	</div>
	<!-- 修改分类的对话框 --> 
	<div id="update_dialog" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	   <!-- 添加一个表单 -->
	   <form method="post" id="update_form">
	   		<!-- 请求方法标识 -->
	   		<input type="hidden" name="method" value="updateCategory" />
	   		<input type="hidden" name="cid"  />
 
	   		<table width="100%">
	   			<tr align="center">
	   				<td>
	   					分类名称:
	   				</td>
	   				<td>
	   					<input type="text" class="easyui-textbox" name="cname"><br>
	   				</td>
	   			</tr>
	   			<tr  align="center">
	   				<td colspan="2">
	   					<input type="button"  value="保存修改" onclick="updateCategory()" style="width:80px"/>
	   				</td>
	   			</tr>
	   		</table>
	   	</form>   
	</div>    

</body>
</html>