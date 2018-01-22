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
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		$('#dg').datagrid({ 
			//servlet的请求路径,该请求为ajax请求,返回值是json
		    url:'${pageContext.request.contextPath }/adminProduct?method=findPageProduct',    
		    columns:[[    
		        {field:'pid',title:'商品编号',width:100},    
		        {field:'pname',title:'商品名称',width:100},    
		        {field:'market_price',title:'市场价格',width:100,align:'right'},    
		        {field:'shop_price',title:'商城价格',width:100,align:'right'},   
		       	{field:'pimage',title:'图片',width:100,align:'right',
		        	formatter: function(value,row,index){
						return "<img src='${pageContext.request.contextPath }/"+value+"'width='50px' height='50px'/>"
					}
			
		        },    
		        {field:'pdate',title:'上架日期',width:100,align:'right'},    
		        {field:'is_hot',title:'是否热门',width:100,align:'right',
		        		formatter: function(value,row,index){
		            		if(value==1){
		            			return "是";
		            		}else{
		            			return "否";
		            		}
		    			}
		        },    
		        {field:'pdesc',title:'商品描述',width:100,align:'right'},    
		        {field:'pflag',title:'是否下架',width:100,align:'right',
		        	formatter: function(value,row,index){
		        		if(value==1){
		        			return "是";
		        		}else{
		        			return "否";
		        		}
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
		 	// 显示分页条
		    pagination:true,
		    pageNumber:1,
		    pageSize:5,
		    pageList:[5,15,20],
		    fit:true,
		    fitColumns:true,
		    nowrap:false,
		    toolbar: [{
				iconCls: 'icon-add',
				handler: function(){
					$("#add_dialog").dialog('open');
				},
				text:"添加商品"
			
			}]

		    
		    
		});  
		// 添加商品时,所需的分类信息
		$('#caid').combobox({  
			// 向服务器发送请求,查询分类信息
		    url:'${pageContext.request.contextPath}/adminCategory?method=findCategory',    
		    valueField:'cid',    
		    textField:'cname'   
		});

	});


</script>
</head>
<body>
	<table id="dg"></table>
	<div id="add_dialog" class="easyui-dialog" style="width:520;height:300;padding:10px"
		data-options="title:'添加商品',iconCls:'icon-save',modal:true,closable:true,closed:true">
		<form id="add_form" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>商品名称:</td>
					<td><input class="easyui-textbox" name="pname" style="width:100%"></td>
					<td>所属分类:</td>
					<td><input id="caid" name="cid" value="请选择分类" style="width:100%"></td>
				</tr>
				<tr>
					<td>市场价格:</td>
					<td><input class="easyui-textbox" name="market_price" style="width:100%"></td>
					<td>商城价格:</td>
					<td><input class="easyui-textbox" name="shop_price" style="width:100%"></td>
				</tr>
				<tr>
					<td>是否热门:</td>
					<td>
						<select class="easyui-combobox" name="is_hot" style="width:100%" data-options="panelHeight:'auto'">   
						    <option value="1">是</option>   
						    <option value="0">否</option>   
						</select>
					</td>
					<td>图片:</td>
					<td>
						<input name="pimage" class="easyui-filebox" data-options="buttonText:'选择文件'" style="width:100%">
					</td>
				</tr>
				<tr>
					<td>商品描述:</td>
					<td colspan="3">
						<input class="easyui-textbox" name="pdesc" style="width:100%" data-options="multiline:true">
					</td>
				</tr>
				<tr style="text-align:center;padding:5px 0">
					<td colspan="4">
						<input type="button" value="保存" class="easyui-linkbutton" onclick="save_product()" style="width:80px">
						<input type="reset" value="重置" class="easyui-linkbutton"  style="width:80px">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>