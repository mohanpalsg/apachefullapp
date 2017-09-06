package datainsert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.mr.app.CPSdiff;
import com.mr.data.HistoricHighLow;

import convertcsv.ConvertEQ;

public class MainDownload {

	public static void updateeod()
	{
		HashMap <String,HistoricHighLow> HistHL = new HashMap <String,HistoricHighLow>();
		HashMap <String,HistoricHighLow> FinHistHL = new HashMap <String,HistoricHighLow>();

		Utilities objUtils = new Utilities();
		Dataconn dataconn = new Dataconn();
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		Connection conn = dataconn.getconn();
		Statement stmt,stmt1;
		try {
			stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("select * from historichighlow");
			while(rs1.next())
			{
				HistoricHighLow HL=new HistoricHighLow();
				HL.setStocksymbol(rs1.getString(1));
				HL.setHighdate(rs1.getDate(2));
				HL.setLowdate(rs1.getDate(3));
				HL.setHighprice(rs1.getDouble(4));
				HL.setLowprice(rs1.getDouble(5));
				HistHL.put(HL.getStocksymbol(), HL);
			}
			stmt1.close();
			rs1.close();
			
			stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("select max(tradedate) from stockprice");
		
		//Date startDate = null;
		//Date endDate = new Date();
		if (rs.next())
		{   if(rs.getDate(1) != null )
		{
			startDate.setTime((Date)rs.getDate(1));
			startDate.add(Calendar.DATE, 1);
		}
		else
			startDate.add(Calendar.MONTH, -24);
		
		}
		else
		{
			
			startDate.add(Calendar.MONTH, -24);
		}
		rs.close();
		stmt.close();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	while(!startDate.after(endDate)){
		try{
			cleardirectory(System.getProperty("user.home")+"\\AppData\\Local\\Temp\\EODData\\");
			objUtils.saveFile(objUtils.createDownloadURL(startDate.getTime()));	
			unzipfiles(System.getProperty("user.home")+"\\AppData\\Local\\Temp\\EODData\\");
			//new DownloadFileThread(fromDateStr, toDateStr, this);
			
			try {
				 File file = new File(System.getProperty("user.home")+"\\AppData\\Local\\Temp\\EODData\\");
				 File[] listOfFiles = file.listFiles();
				 for (int i = 0; i < listOfFiles.length; i++) 
				 {
				if (listOfFiles[i].getName().endsWith(".csv") && listOfFiles[i].getName().startsWith("cm"))
				{
					HashMap <String,HistoricHighLow> TempHistHL = new HashMap <String,HistoricHighLow>();
					TempHistHL = new ConvertEQ().convertToDesiredFormat(System.getProperty("user.home")+"\\AppData\\Local\\Temp\\EODData\\" + listOfFiles[i].getName(),HistHL);
					if (TempHistHL != null && !TempHistHL.isEmpty())
						FinHistHL = TempHistHL;
				}
				
				 }
				 
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		catch (Exception e){
			System.out.println("Error " + e.getMessage());
		}
		startDate.add(Calendar.DATE, 1);
	}
	
	
	
	
	// parsefinallistHL;
	
	if(!FinHistHL.isEmpty())
	{
		Statement stmt3;
		try {
			stmt3 = conn.createStatement();
			stmt3.executeUpdate("delete from historichighlow");
			stmt3.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into historichighlow values (?,?,?,?,?)");
		
			Iterator it1 = FinHistHL.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pair = (Map.Entry)it1.next();
		        String stocksymbol = (String) pair.getKey();
		        HistoricHighLow cp = (HistoricHighLow) pair.getValue();
		        pstmt.setString(1, stocksymbol);
		        pstmt.setDate(2, new java.sql.Date(cp.getHighdate().getTime()));
		        pstmt.setDate(3, new java.sql.Date(cp.getLowdate().getTime()));
		        pstmt.setDouble(4, cp.getHighprice());
		        pstmt.setDouble(5, cp.getLowprice());
		       // pstmt.setDate(6, new java.sql.Date(new Date().getTime()));
		        pstmt.addBatch();
		        
			}
			
			pstmt.executeBatch();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// parse and insert;
		
		
		
	}
	
	dataconn.closeconn();
	}

	private static void cleardirectory(String string) {
		// TODO Auto-generated method stub
		new File(string).mkdir();
		File file =new File (string);
		 File[] listOfFiles = file.listFiles();
		 for (int i = 0; i < listOfFiles.length; i++) 
		 {
			
			 listOfFiles[i].delete();
		 }
	}

	private static void unzipfiles(String string) throws IOException {
		byte[] buffer = new byte[1024];
		 File file = new File(string);
		 String fileName =null;
		 File[] listOfFiles = file.listFiles();
		 for (int i = 0; i < listOfFiles.length; i++) 
		 {
			 
				
				ZipInputStream zis =
				    		new ZipInputStream(new FileInputStream(listOfFiles[i].getPath()));
				ZipEntry ze = zis.getNextEntry();
				while(ze!=null){

					fileName = ze.getName();
			           File newFile = new File(string + File.separator + fileName);

			           System.out.println("file unzip : "+ newFile.getAbsoluteFile());

			            //create all non exists folders
			            //else you will hit FileNotFoundException for compressed folder
			            new File(newFile.getParent()).mkdirs();

			            FileOutputStream fos = new FileOutputStream(newFile);

			            int len;
			            while ((len = zis.read(buffer)) > 0) {
			       		fos.write(buffer, 0, len);
			            }

			            fos.close();
			            ze = zis.getNextEntry();
			    	}

			        zis.closeEntry();
			    	zis.close();

			    	System.out.println("Done");
			    	
					
				
			} 
		}
			 
			
		 
		 
		
	
}
