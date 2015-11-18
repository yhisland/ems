/**
 * 
 */
package com.manager.comm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 表单中不论是基本文本数据还是上传的文件，都是封装在FileItem中的
 * 一个字段对应一个FileItem
 */
public class FileUpload {
	private Map<String,FileItem> map = null;

	public FileUpload(HttpServletRequest request) {
		this(request, 10 * 1024 * 1024);
	}

	@SuppressWarnings("unchecked")
	public FileUpload(HttpServletRequest request, long maxSize) {
		if(maxSize<0||request==null){
			throw new RuntimeException("参数不合法!");
		}
		map = new HashMap<String, FileItem>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(maxSize);
		try {
			List<FileItem> filelist = upload.parseRequest(request);
			Iterator<FileItem> itor = filelist.iterator();
			FileItem item = null;
			while(itor.hasNext()){
				item = itor.next();
				map.put(item.getFieldName(), item);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据表单控件的name值获取对应的value
	 * @param fieldName
	 * @return Object
	 */
	public Object getValue(String fieldName){
		FileItem item = map.get(fieldName);
		if(item!=null){
			if(item.isFormField()){
				return item.getString();
			}else{
				return item.get();
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 根据表单控件的name值获取对应的FileItem
	 * @param fieldName
	 * @return FileItem
	 */
	public FileItem getFileItem(String fieldName){
		return map.get(fieldName);
	}
}
