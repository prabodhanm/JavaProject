package com.common.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;

import java.text.SimpleDateFormat;



import org.apache.poi.hssf.usermodel.HSSFCell;


//Import files Required for Csv to Csv format
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import com.opencsv.CSVWriter;
 

public class ExcelFileRead {
	Map<String,String> dictionary = null;
	@SuppressWarnings("resource")
	public void FormatCSV() throws Exception{
		CSVReader csvReader = null;
		String csvFile = "E:\\Projects\\Sunil\\Requirement\\DHL.csv";
		try {			
			csvReader = new CSVReader(new FileReader(csvFile));			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error occured while executing file. "
					+ e.getMessage());
		}
		
		ArrayList<String[]> items = new ArrayList<String[]>();
		String[] headerRow = csvReader.readNext();
		
		if (null == headerRow) {
			throw new FileNotFoundException(
					"No columns defined in given CSV file." +
					"Please check the CSV file format.");
		}
		//Create Dictionary
		dictionary = new HashMap<String,String>();
		dictionary.put("Order ID", "PO");
		dictionary.put("Ship Date", "SHIP_DATE");
		dictionary.put("Item Shipped", "ITEM_SHIPPED");
		dictionary.put("Item Ordered", "CATALOG");
		dictionary.put("Quantity Shipped", "QTY_SHIPPED");
		
		
		items.add(headerRow);
		String[] nextLine;
		nextLine = csvReader.readNext();
		
		while (nextLine != null) {
			items.add(nextLine);
			nextLine = csvReader.readNext(); 			
		}
		
		String pos ="";
		String[] posarray = null;
		//Do formatting
		int i  = 0, j = 0, k = 0  ;
		String[] dataarray = null;
		ArrayList<String[]> newitems = new ArrayList<String[]>();
		String dateval = "";
		for(String[] item : items){
			dataarray = new String[5];
			if(i == 0){
				
				for(String itemval : item){
					if(dictionary.containsKey(itemval)){
						pos = pos + j + ",";
						dataarray[k++] = String.valueOf(dictionary.get(itemval)) ;
					}
					j++;
				}
				posarray = pos.split(",");
				
			}
			else{
				
				
				for(int ii = 0 ; ii < posarray.length ; ii++){
					int val = 0;
					val = Integer.parseInt(posarray[ii]);
					if(ii == 1){
						dateval = item[val].split(" ")[0];
						dataarray[k++] = dateval;
					}
					else{
						dataarray[k++] = item[val];
					}
					
				}
				
			}
			String ival = dataarray[2];
			dataarray[2] = dataarray[3];
			dataarray[3] = ival;
			
			//dataarray[1] = dataarray.
			
			newitems.add(dataarray);
			i++;	
			k =0;
		}
		String csv = "E:\\Projects\\Sunil\\Requirement\\DHL-test.csv";
		
		CSVWriter writer = new CSVWriter(new FileWriter(csv));
		writer.writeAll(newitems);
		writer.close();
	}
	
	
	
	public void FormatXlsToCSV() throws IOException{
		FileInputStream fileIn = new FileInputStream("E:\\Projects\\Sunil\\Requirement\\PartII\\In_C.xls");
	    //read file 
	    POIFSFileSystem fs = new POIFSFileSystem(fileIn); 
	    HSSFWorkbook filename = new HSSFWorkbook(fs);
	    //open sheet 0 which is first sheet of your worksheet
	    HSSFSheet sheet = filename.getSheetAt(0);
	    int[] columnNo = new int[7];
	 	try{		 		

		    //we will search for column index containing string "Your Column Name" in the row 0 (which is first row of a worksheet
		    // String columnWanted = "Ship_Date"+"Cust_Order_Num"+"Line_Num"+"Item_Code";
		    //Store in dictionary
		    Hashtable<Integer, String> columnWanted = new Hashtable<Integer, String>();
		    
		    columnWanted.put(1, "Status");
		    columnWanted.put(2, "Ship_Date");
		    columnWanted.put(3, "Cust_Order_Num");
		    columnWanted.put(4, "Line_Num");
		    columnWanted.put(5, "Item_Code");
		    columnWanted.put(6, "Item_Descr1");
		    columnWanted.put(7, "Ship_Qty");
		    
		    
		    //output all not null values to the list
		    //List<Cell> cells = new ArrayList<Cell>();

		    Row firstRow = sheet.getRow(0);
		    Integer colIndex= 0;
		    for(Cell cell:firstRow){
		        if(columnWanted.contains(cell.getStringCellValue()))
		    	{
		    		columnNo[colIndex++] = cell.getColumnIndex();
		    	}
		    }			    			    			    			    
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	    
	 	
	 	FileOutputStream fileOut = new FileOutputStream("E:\\Projects\\Sunil\\Requirement\\PartII\\poi-test.csv");
		//HSSFWorkbook workbook = new HSSFWorkbook();
		//HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
		
		HSSFDataFormat fmt = filename.createDataFormat();
	    CellStyle textStyle = filename.createCellStyle();
	    textStyle.setDataFormat(fmt.getFormat("@"));
	    
	    
		int i = 0;
		String[] svalue;
		String csv = "E:\\Projects\\Sunil\\Requirement\\PartII\\poi-test.csv";
		
		CSVWriter writer = new CSVWriter(new FileWriter(csv));
		
		String[] writestring = null;
		boolean barraycontains =false;
		List<String[]> data = new ArrayList<String[]>();
		try{
			for (Row row : sheet) {
				//HSSFRow targetRow = worksheet.createRow((short) i++);
				writestring = new String[7];
				int writestrpos = 0;
				//int c = 0;
				barraycontains =false;
				for(Cell cell : row){
						if(CheckValueInArray(columnNo,cell.getColumnIndex())){
							switch(cell.getCellType()){
							case HSSFCell.CELL_TYPE_STRING:
								if(i > 0){
									switch(cell.getColumnIndex()){
									case 3:
										svalue = cell.getStringCellValue().split("/");																		
										writestring[writestrpos++] = " " +  String.valueOf(svalue[2] + "-" + svalue[0] + "-" + svalue[1])   ;
										
										break;
									case 5:
										cell.setCellStyle(textStyle);											
										if(cell.getStringCellValue().length() != 10){
											writestring[writestrpos++] =   "Deleted";
											--i;
											//break;
										}
										else{	
											writestring[writestrpos++] =   cell.getStringCellValue();
										}
										break;
									case 18:
										if(cell.getStringCellValue().length() > 12){
											writestring[writestrpos++] =   cell.getStringCellValue().substring(0,12);
										}
										else{
											writestring[writestrpos++] =  cell.getStringCellValue();
										}
										break;
									default:
										writestring[writestrpos++] =  cell.getStringCellValue();
									}
								}
								else{
									writestring[writestrpos++] =  cell.getStringCellValue().toUpperCase();
									/*System.out.print(cell.getColumnIndex());
									System.out.print(cell.getStringCellValue().toUpperCase());*/
								}
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if(cell.getColumnIndex() == 5){
									writestring[writestrpos++] =   "Deleted";
									//--i;
									break;
								}
								else{
									writestring[writestrpos++] =  String.valueOf(cell.getNumericCellValue()) ;
								}
								break;									
							}																																	
						}							
				}
				/*if(!writestring.contains("Deleted")){
					
				}*/
				i++;
				for(int ii = 0; ii<writestring.length; ii++){
					if(writestring[ii] == "Deleted"){
						barraycontains = true;
						break;
					}
				}
				if(barraycontains== false){						
					data.add(writestring);
				}
				
			}
			//workbook.write(fileOut);	
			writer.writeAll(data);

			writer.close();
			System.out.print("Task Complete");
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}	
		finally{
			fileOut.flush();
			fileOut.close();
		}
	}
	 
	public void FormatNextXlsToCSV() throws IOException {
		FileInputStream fileIn = new FileInputStream("E:\\Projects\\Sunil\\Requirement\\Part III\\In_B.xls");
		
	    //read file 
	    POIFSFileSystem fs = new POIFSFileSystem(fileIn); 
	    HSSFWorkbook workbook = new HSSFWorkbook(fs);
	    //open sheet 0 which is first sheet of your worksheet
	    int noofsheets = workbook.getNumberOfSheets();
	    HSSFSheet sheet = workbook.getSheetAt(0);
	    int[] columnNo = new int[5];
	 	try{		 		

		    
		    //Store in dictionary
		    Hashtable<Integer, String> columnWanted = new Hashtable<Integer, String>();
		    
		    columnWanted.put(1, "PO");
		    columnWanted.put(2, "Ship Date");
		    columnWanted.put(3, "Item Ship");
		    columnWanted.put(4, "Catalog#");
		    columnWanted.put(5, "Qty Ship");
		    
		    
		    
		    //output all not null values to the list
		    //List<Cell> cells = new ArrayList<Cell>();

		    Row firstRow = sheet.getRow(2);
		    Integer colIndex= 0;
		    for(Cell cell:firstRow){
		        if(columnWanted.contains(cell.getStringCellValue()))
		    	{
		    		columnNo[colIndex++] = cell.getColumnIndex();
		    	}
		    }			    			    			    			    
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	    	 		 				    
	    
		
		String csv = "E:\\Projects\\Sunil\\Requirement\\Part III\\poi-test_new.csv";
		
		CSVWriter writer = new CSVWriter(new FileWriter(csv));
		//List<String[]> data = null;
		List<String[]> data = new ArrayList<String[]>();
		data = GetSheetData(sheet,columnNo,3,data);
		
		
		
		if(noofsheets > 1){
			for(int isheetno = 1 ; isheetno < noofsheets ; isheetno++ ){
				sheet = workbook.getSheetAt(isheetno);
				data = GetSheetData(sheet,columnNo,0, data);
				//data = MergeLists(data,data1);
				//writer.writeAll(data);
			}
		}
		
		writer.writeAll(data);
		writer.close();
		System.out.print("Task Complete");
	}
	
		
	@SuppressWarnings("finally")
	public List<String[]> GetSheetData(HSSFSheet sheet, int[] columnNo,int rowpos, List<String[]> d){
		int i = 0;
		String[] svalue;
		String[] writestring = null;
		boolean barraycontains =false;
		boolean blnDataOver = false;
		List<String[]> data = new ArrayList<String[]>();
		
		try{
			for (Row row : sheet) {
				//HSSFRow targetRow = worksheet.createRow((short) i++);
				
				writestring = new String[5];
				int writestrpos = 0;
				//int c = 0;
				barraycontains =false;
				if(i < rowpos-1){
					i++;
					continue;
				}
				for(Cell cell : row){
						if(cell.getColumnIndex() == 0){
							if(cell.getStringCellValue()==""){
								blnDataOver = true;
								break;
							}								
						}
						
						if(CheckValueInArray(columnNo,cell.getColumnIndex())){
							switch(cell.getCellType()){
							case HSSFCell.CELL_TYPE_STRING:
								if(i >= rowpos){
									switch(cell.getColumnIndex()){
									case 6:
										svalue = cell.getStringCellValue().split("/");																		
										writestring[writestrpos++] =   String.valueOf(svalue[2] + "-" + svalue[0] + "-" + svalue[1])   ;
										
										break;
									case 0:
										//cell.setCellStyle(textStyle);	
										if(cell.getStringCellValue() == " "){
											blnDataOver = true;
											break;
										}
										if(cell.getStringCellValue().length() != 8){
											writestring[writestrpos++] =   "Deleted";
											--i;
											//break;
										}
										else{	
											writestring[writestrpos++] =   cell.getStringCellValue();
										}
										break;
									case 11:
										if(cell.getStringCellValue().length() > 12){
											writestring[writestrpos++] =   cell.getStringCellValue().substring(0,12);
										}
										else{
											writestring[writestrpos++] =  cell.getStringCellValue();
										}
										break;
									default:
										writestring[writestrpos++] =  cell.getStringCellValue();
									}
								}
								else{
									if(writestrpos==3){
										//writestring[writestrpos++] =  cell.getStringCellValue().toUpperCase().substring(cell.getStringCellValue().length()-1);
										writestring[writestrpos++] = "CATALOG";
									}
									else if(writestrpos==2){
										writestring[writestrpos++] = "ITEM_SHIPPED";
									}
									else if(writestrpos==1){
										writestring[writestrpos++] = "SHIP_DATE";
									}
									else if(writestrpos==4){
										writestring[writestrpos++] = "QTY_SHIP";
									}
									else{
										writestring[writestrpos++] =  cell.getStringCellValue().toUpperCase();
									}
									
								}
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if(cell.getColumnIndex() == 0){
									writestring[writestrpos++] =   "Deleted";
									//--i;
									break;
								}
								else if(cell.getColumnIndex() == 6){
							        
							        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
							        String  s =  sdf.format(cell.getDateCellValue());
							        
							        writestring[writestrpos++] =  s;
								}								
								else{
									writestring[writestrpos++] =  String.valueOf(cell.getNumericCellValue()) ;
								}
								break;								
							}																																	
						}							
				}
				/*if(!writestring.contains("Deleted")){
					
				}*/
				i++;
				if(blnDataOver){
					blnDataOver = false;
					break;
				}
				for(int ii = 0; ii<writestring.length; ii++){
					if(writestring[ii] == "Deleted"){
						barraycontains = true;
						break;
					}
				}
				if(barraycontains== false){						
					d.add(writestring);
				}
				
			}
			
			
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}	
		finally{
			/*fileOut.flush();
			fileOut.close();*/
			data = d;
			return data;
		}
	}
	public static void main(String[] args) throws Exception {  
		    //test file is located in your project path      
		 									    
	 	}
	 	
	 	public static boolean CheckValueInArray(int[] colNo, int targetValue){
	 		for(Integer s: colNo){
	 			if(s.equals(targetValue))
	 				return true;
	 		}
	 		return false;
	 	}
}
