package com.ReadFile.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;

//import net.viralpatel.java.CSVLoader;
//import CSVLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.common.utility.*;
//import com.ReadFile.Utility.*;

public class ExcelFileReadUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Utility utility = new Utility();
        fdata = utility.ReadProperty();
        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcelFileReadUI window = new ExcelFileReadUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private static FileData fdata = new FileData();
	
	public ExcelFileReadUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 665, 403);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblStepISuccessfully = new JLabel("");
		lblStepISuccessfully.setForeground(Color.BLUE);
		lblStepISuccessfully.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 14));
		lblStepISuccessfully.setBounds(134, 250, 350, 20);
		frame.getContentPane().add(lblStepISuccessfully);
		
		JLabel lblPartI = new JLabel("Part I");
		lblPartI.setFont(new Font("Verdana", Font.BOLD, 14));
		lblPartI.setBounds(50, 58, 69, 24);
		frame.getContentPane().add(lblPartI);
		
		JButton btnStepI = new JButton("Step I");
		btnStepI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create object of CSVLoader
         		CSVLoader csvloader = new CSVLoader(getCon());
         		try {
					csvloader.loadCSV(fdata.SourceFile, fdata.Step1Table, true);
					lblStepISuccessfully.setText("Step I Successfully Completed...");
										
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnStepI.setFont(new Font("Verdana", Font.BOLD, 14));
		btnStepI.setBounds(134, 48, 89, 44);
		frame.getContentPane().add(btnStepI);
		
		JButton btnStepIi = new JButton("Step II");
		btnStepIi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				java_select_csv getcsv = new java_select_csv();
         		
         		try{
         			getcsv.insertToCSV();
         			lblStepISuccessfully.setText("Step II Successfully Completed...");         			
         			
         		}
         		catch(Exception ex){
         			ex.printStackTrace();
         		}
			}
		});
		btnStepIi.setFont(new Font("Verdana", Font.BOLD, 14));
		btnStepIi.setBounds(298, 48, 99, 44);
		frame.getContentPane().add(btnStepIi);
		
		JLabel lblPartIi = new JLabel("Part II");
		lblPartIi.setFont(new Font("Verdana", Font.BOLD, 14));
		lblPartIi.setBounds(50, 123, 69, 24);
		frame.getContentPane().add(lblPartIi);
		
		JButton btnExcelFormat = new JButton("Excel Format I");
		btnExcelFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExcelFileRead objFileRead = new ExcelFileRead();
				
				try{
					objFileRead.FormatXlsToCSV();
					lblStepISuccessfully.setText("First Excel file successfully formatted to csv...");
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		btnExcelFormat.setFont(new Font("Verdana", Font.BOLD, 14));
		btnExcelFormat.setBounds(134, 113, 158, 44);
		frame.getContentPane().add(btnExcelFormat);
		
		JButton btnExcelFormatIi = new JButton("Excel Format II");
		btnExcelFormatIi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//FormatNextXlsToCSV
				ExcelFileRead objFileRead = new ExcelFileRead();
				
				try{
					objFileRead.FormatNextXlsToCSV();
					lblStepISuccessfully.setText("Second Excel file successfully formatted to csv...");
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		btnExcelFormatIi.setFont(new Font("Verdana", Font.BOLD, 14));
		btnExcelFormatIi.setBounds(308, 113, 158, 44);
		frame.getContentPane().add(btnExcelFormatIi);
		
		JButton btnFormatCSV = new JButton("Format CSV");
		btnFormatCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExcelFileRead objFileRead = new ExcelFileRead();
				
				try{
					objFileRead.FormatCSV();
					lblStepISuccessfully.setText("Second Excel file successfully formatted to csv...");
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		});
		btnFormatCSV.setBounds(308, 113, 158, 44);
		frame.getContentPane().add(btnFormatCSV);
		
		
		//Merge CSV
		JTextField txtFileName = new JTextField("");
		txtFileName.setBounds(134, 190, 150, 44);
		//134, 113, 158, 44
		frame.getContentPane().add(txtFileName);
		
		
		JButton btnMergeCSV = new JButton("Merge CSV");
		btnMergeCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mergecsvfiles objmergecsv = new mergecsvfiles();
				//JOptionPane.showMessageDialog(null, "You Entered "  + txtFileName.getText());
				
				try{
					objmergecsv.MergeCSV(txtFileName.getText());
					lblStepISuccessfully.setText("merging csv files completed successfully...");
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		});
		btnMergeCSV.setBounds(308, 190, 192, 44);
		frame.getContentPane().add(btnMergeCSV);
		//Merge CSV End
	}
	
	private static Connection getCon() {
		Connection connection = null;
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Class.forName(fdata.DRIVER);
			
			/*System.out.println("Driver=" + fdata.DRIVER);
	        System.out.println("Connectionstring=" + fdata.JDBC_CONNECTION_URL);
	        System.out.println("User=" + fdata.User);
	        System.out.println("Password=" + fdata.Password);*/
	        
	        
			//System.out.println("Connection String =" + JDBC_CONNECTION_URL);
			connection = DriverManager.getConnection(fdata.JDBC_CONNECTION_URL,fdata.User,fdata.Password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		return connection;
	}
}
