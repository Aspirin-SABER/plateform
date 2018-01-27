package com.itheima.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.itheima.constant.Constant;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UploadUtils;

/**
 * Servlet implementation class AddProductServlet
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> mapMsg=new HashMap<>();
		try {
			//解析request请求携带的参数,并将参数分装到Product中
			//a.创建磁盘文件项工厂对象
			DiskFileItemFactory factory=new DiskFileItemFactory();
			//b.获取核心的文件上传对象
			ServletFileUpload upload=new ServletFileUpload(factory);
			//c解析request获取的每一个文件项
			List<FileItem> list = upload.parseRequest(request);
			//d遍历文件list
				//定一个存放请求参数的map集合
				Map<String,Object> map=new HashMap<>();
				for (FileItem fi : list) {
					if(fi.isFormField()){
						//普通文件
						map.put(fi.getFieldName(),fi.getString("utf-8"));
					}else{
						//复制文件
						//e.获取文件的真是名称
						String filedName=fi.getName();
						String realName=UploadUtils.getRealName(filedName);
						//f.获取唯一名称
						String uuidName = UploadUtils.getUUIDName(realName);
						//g.生成路径
						String dir="/upload"+UploadUtils.getDir();
						//System.out.println(getServletContext());
						String realPath = getServletContext().getRealPath(dir);
						//判断该文件夹是否存在
						File file=new File(realPath);
						if(!file.exists()){
							file.mkdirs();
						}
						System.out.println(realPath);
						//获取文件的输入流
						InputStream is = fi.getInputStream();
						FileOutputStream os=new FileOutputStream(file+"/"+uuidName);
						IOUtils.copy(is, os);
						os.close();
						is.close();
						fi.delete();
						//将文件的名称和路径放入map
						map.put("pimage", dir+"/"+uuidName);
						
					}
				}
			Product pro=new Product();
			BeanUtils.populate(pro, map);
			//设置一些必要的参数
				//a.设置id
				pro.setPid(UUIDUtils.getId());
				//b.设置商品是否下架
				pro.setPflag(Constant.PRODUCT_PUSH_UP);
				//c.将请求携带商品分类的id,装到商品分类的实体中,再把商品分类分装到product的实体中
				Category c=new Category();
				c.setCid((String)map.get("cid"));
				pro.setCategory(c);
				pro.setPdate(new Date());;
				ProductService service = (ProductService)BeanFactory.getBean("ProductService");
				
				service.addProduct(pro);
				mapMsg.put("msg", "添加成功");
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mapMsg.put("msg", "添加成功");
		}
		String jsonString = JSON.toJSONString(mapMsg);
		response.getWriter().println(jsonString);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
