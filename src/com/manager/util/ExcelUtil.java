package com.manager.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
/**
 *解析Excel文件数据
 * 
 */
public class ExcelUtil {

	/**
	 *  读取Excel中的字段
	 * @param bytes
	 * @param headprovider
	 */
	public static List<Map<String,Object>> readExcel(byte[] bytes,List<Map<String, Object>> headprovider) {
		return readExcel(bytes, headprovider,"dataField","headText");
	}
	/**
	 *  读取Excel中的字段
	 */
	public static List<Map<String,Object>> readExcel(byte[] bytes,List<Map<String, Object>> headprovider,String dataField,String headText) {
		Map<String, String> namemap =new HashMap<>();
		for(Map<String, Object> m:headprovider){
			if(!m.containsKey(dataField)){
				continue;
			}
			String df=m.get(dataField).toString();
			String ht=df;
			if(m.containsKey(headText)){
				 ht=m.get(headText).toString();
			}
			namemap.put(ht,df);
		}
		return readExcel(bytes, namemap);
	}

	/**
	 *  读取Excel中的字段
	 * @param bytes
	 * @param namemap
	 */
	public static List<Map<String,Object>> readExcel(byte[] bytes,Map<String, String> namemap) {
		if(bytes==null){
			throw new RuntimeException("空数据！");
		}
		POIFSFileSystem fs = null;
		HSSFWorkbook workbook = null;
		try {
			try {
				fs = new POIFSFileSystem(new ByteArrayInputStream(bytes));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("请确定所选的文件的格式是否为“.xls”");
			}
			workbook =new HSSFWorkbook(fs); 
		} catch (IOException e) {
			throw new RuntimeException("文件类型应为\".xls");
		}
		HSSFSheet sheet = workbook.getSheetAt(0);
		int FirstRowNum = sheet.getFirstRowNum();
		int LastRowNum = sheet.getLastRowNum();
		if(FirstRowNum==LastRowNum){
			throw new RuntimeException("该表格数据为空!");
		}
		HSSFRow row0 = sheet.getRow(FirstRowNum);//获取表头
		int FirstCellNum = row0.getFirstCellNum();
		int LastCellNum = row0.getLastCellNum();
		ArrayList<String> head=new ArrayList<String>();

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for (int rowNum = FirstRowNum; rowNum <= LastRowNum; rowNum++) {
			HSSFRow row = sheet.getRow(rowNum);
			if(row==null){
				break;
			}
			HashMap<String,Object> map=new HashMap<String,Object>();
			boolean flag=false;
			int valuenums=0;
			for (int cellNum = FirstCellNum; cellNum < LastCellNum; cellNum++) {
				@SuppressWarnings("deprecation")
				HSSFCell cell = row.getCell((short)cellNum);
				String value = null;
				int type = HSSFCell.CELL_TYPE_BLANK;
				try {
					type = cell.getCellType();

				} catch (Exception e) {
				}
				if (type == HSSFCell.CELL_TYPE_STRING) {
					value = cell.getStringCellValue().trim();
				} else if (type == HSSFCell.CELL_TYPE_NUMERIC) {
					double num = cell.getNumericCellValue();
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						value = sdf.format(DateUtil.getJavaDate(num));
					} else {

						if (num % 1 == 0) {
							value = Long.toString(Math.round(num));
						} else {
							value = Double.toString(num);
						}
					}
				} else if (type == HSSFCell.CELL_TYPE_BLANK) {
					value = "";
				} else if (type == HSSFCell.CELL_TYPE_FORMULA) {
					throw new RuntimeException("行"+(rowNum+1)+"列"+(cellNum+1)+
							":不支持Excel公式,请用Ctrl+A-->Ctrl+C-->Ctrl+V到另一个Sheet-->在选择性粘贴选项中选择\"只有值\"");
				} else {
					if(type==HSSFCell.CELL_TYPE_BOOLEAN){
						System.out.println("cell.CELL_TYPE_BOOLEAN");
					}else if(type==HSSFCell.CELL_TYPE_ERROR){
						System.out.println("cell.CELL_TYPE_ERROR");
					}
					throw new RuntimeException(rowNum+"行"+cellNum+"列单元格类型错误:"+type);
				}
				if(rowNum==FirstRowNum){
					head.add(value);
				}else{
					flag=true;
					if(value!=null&&!value.toString().equals("")){
						valuenums++;
					}
					if(namemap.containsKey(head.get(cellNum-FirstCellNum))){
						map.put(namemap.get(head.get(cellNum-FirstCellNum)), value);
					}else{
						map.put(head.get(cellNum-FirstCellNum), value);
					}
				}
			}
			if(flag&&valuenums>0){
				list.add(map);
			}

		}
		return list;
	}


	/**
	 * 读取Excel第一个Sheet的数据,以第一行为key
	 */
	public static List<Map<String,Object>> readExcel(byte[] bytes) {
		Map<String, String> map=new HashMap<>();
		return readExcel(bytes, map);
	}
	
	/**
	 * 写Excel,当所得到的数据大于60000条时将分成多个sheet存储
	 * @param maplist 
	 */
	public static byte[] export_excel(List<Map<String,Object>> maplist,List<Map<String, String>> keylist) {
		return export_excel(maplist, keylist,"dataField","headText");
	}
	
	/**
	 * 将数据导出Excel文件字节流
	 * @param maplist：源数据
	 * @param keylist：表头
	 * @param dataField
	 * @param headText
	 * @return 数据字节流
	 */
	@SuppressWarnings("deprecation")
	public static byte[] export_excel(List<Map<String,Object>> maplist,List<Map<String, String>> keylist,String dataField,String headText) {
		if(maplist!=null){
			ArrayList<String> keys=new ArrayList<String>();
			Map<String,Object> head=new HashMap<String,Object>();
			for(int i=0;i<keylist.size();i++){
				Map<String, String> m=keylist.get(i);
				if(!m.containsKey(dataField)){
					continue;
				}
				String df=m.get(dataField);
				String ht=df;
				if(m.containsKey(headText)){
					ht=m.get(headText);
				}
				keys.add(df);
				head.put(df, ht);
			}
			maplist.add(0, head);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet =workbook.createSheet();
			int rowNum=0;
			for(int index=0;index<maplist.size();index++){
				Map<String,Object> map=maplist.get(index);
				HSSFRow 	row= sheet.createRow(rowNum);
				int cellNum=0;
				for(int i=0;i<keys.size();i++){
					String key=keys.get(i);
					HSSFCell cell=row.createCell((short)cellNum);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if(map.containsKey(key)){
						Object v=map.get(key);
						if(v==null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(v+"");
						}
					}else{
						cell.setCellValue("");
					}
					cellNum++;
				}
				rowNum++;
			}


			ByteArrayOutputStream out=new ByteArrayOutputStream();
			try {
				workbook.write(out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] bytearray=out.toByteArray();
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bytearray;
		}else{
			throw new RuntimeException("没有数据!");
		}
	}

}
