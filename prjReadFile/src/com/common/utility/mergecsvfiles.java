package com.common.utility;

import java.io.File;
import java.io.FileFilter;

//import java.io.FileFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
//import java.util.Iterator;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;

//import java.lang.Object;
import java.text.SimpleDateFormat;



import org.apache.poi.hssf.usermodel.HSSFCell;


//Import files Required for Csv to Csv format
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import com.opencsv.CSVWriter;

import java.util.Properties;

public class mergecsvfiles {
	Map<String,String> dictionary = null;
	@SuppressWarnings("resource")
	public void MergeCSV(String filestart) throws Exception{
		CSVReader csvReader = null;
		
		//String dirpath = "E:\\Projects\\Sunil\\Data";
		
		//read property file for file path
		Properties prop = new Properties();
		FileInputStream input = null;
		
		input = new FileInputStream("config.properties");
		prop.load(input);
			
		File filedir = new File(prop.getProperty("filepath"));
		
		
		FileFilter filefilter = new WildcardFileFilter(filestart + "*.csv");
		File[] dirlisting = filedir.listFiles(filefilter);
		boolean isheader = false;
		
		ArrayList<String[]> items = new ArrayList<String[]>();
		String[] headerRow=null;
		String[] nextLine;
		/*String filename = "";
		String fileext = "";
		String ext = "csv";*/
		if(dirlisting != null){
			for(File file : dirlisting){
				
				try {			
					csvReader = new CSVReader(new FileReader(file.getPath()));			
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("Error occured while executing file. "
							+ e.getMessage());
				}
				
				if(isheader == false){
					headerRow = csvReader.readNext();
					
					if (null == headerRow) {
						throw new FileNotFoundException(
								"No columns defined in given CSV file." +
								"Please check the CSV file format.");
					}
					
					items.add(headerRow);
					isheader = true;
				}
				
				
				nextLine = csvReader.readNext();
				
				while (nextLine != null) {
					items.add(nextLine);
					nextLine = csvReader.readNext(); 			
				}
			}
		}
		
		//Write data to target csv file		
		//String csv = "E:\\Projects\\Sunil\\Data\\finalempfile.csv";
		
		CSVWriter writer = new CSVWriter(new FileWriter(prop.getProperty("targetfile")));
		writer.writeAll(items);
		writer.close();
	}
}
