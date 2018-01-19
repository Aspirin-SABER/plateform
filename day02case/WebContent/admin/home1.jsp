<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上商城管理中心</title>
<!--  easyUI环境的搭建 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
<style type="text/css">
	a{
		text-decoration: none;
		font-size: 18px;
	}


</style>
<script type="text/javascript" >
	$(function(){
		//给<a>标签添加点击事件
		$("#aa a").click(function(){
			var $name=$(this).text();
			//alert($name)
			//动态获取跳转路径
			var $href=$(this).attr("href");
			//判断该名称的选项卡是否存在,如果存在则选中,如果不存在则添加
			var flag=$("#tt").tabs('exists',$name);
			//alert(flag);
			if(flag){
				//如果存在,则选中
				$("#tt").tabs('select',$name);
			}else{
				$("#tt").tabs('add',{
					title:$name,
					content:"<iframe src='"+$href+"' style='width:100%;height:100%;border:0px'></iframe>",
					closable:true
				});
			}
			return false;
		});
	});
</script>
</head>
<body>
	<!-- 布局layout -->
	<div id="cc" class="easyui-layout" data-options="fit:true">   
	    <div data-options="region:'north',split:true" style="height:100px;">
	    	<h2 align="center">小羊淘米后台管理页面</h2>
	    </div>   
	    <div data-options="region:'west',title:'菜单',split:true" style="width:200px;" data-options="">
	    <!-- 手风琴 -->
	    	<div id="aa" class="easyui-accordion" data-options="fit:true,border:false">   
			    <div title="分类管理" data-options="iconCls:'icon-save',selected:true" style="overflow:auto;padding:10px;">   
				<a href="${pageContext.request.contextPath }/admin/category/list1.jsp">分类列表</a> 
			    </div>   
			    <div title="商品管理" data-options="iconCls:'icon-reload'" style="padding:10px;">   
			       	<a href="#"> 商品列表 </a>   
			    </div>   
			    <div title="用户管理">   
					<a href="#">用户列表</a>
			    </div>   
			</div>  
	    
	    
	    </div>   
	    <div data-options="region:'center'" style="padding:5px;background:#eee;">
		    <div id="tt" class="easyui-tabs" style="width:500px;height:250px;" data-options="fit:true,border:false">   
			    <div title="主页面" style="padding:20px;display:none;">   
			        <h1 align="center">你还是曾经的那个你么</h1>    
			    </div>   
			      
			</div>  
	    
	    
	    
	    </div>   
	    <div data-options="region:'south',title:'South Title',split:true" style="height:100px;">&copy;小羊淘米</div>   
	</div>  
	

</body>
</html>