package com.common.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import com.ReadFile.DAL.*;


public class Utility {
	
	

	public FileData ReadProperty(){
    	String filename = "E:\\Projects\\Java\\CSVRead\\PropertyFile.txt";
    	BufferedReader br = null;
		FileReader fr = null;
		FileData fdata = new FileData();
    	try {

			fr = new FileReader(filename);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filename));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split("->");
				
				switch(parts[0]){
					case "ConnectionString":
						fdata.JDBC_CONNECTION_URL = parts[1];
						break;
					case "DriverName":
						fdata.DRIVER = parts[1];
						break;
					case "UserName":
						fdata.User = parts[1];
						break;
					case "Password":
						fdata.Password = parts[1];
						break;
					case "Step1SourceFilePath":
						fdata.SourceFile = parts[1];
						break;
					case "Step1TableName":
						fdata.Step1Table = parts[1];
						break;
					case "Step2TargetFilePath":
						fdata.TargetFile = parts[1];
						break;
					case "Step2TableName":
						fdata.Step2Table = parts[1];
						break;
				}
								
			}

		} catch (IOException e) {

			e.printStackTrace();

		} 
    	finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
    	return fdata;
    }
}
