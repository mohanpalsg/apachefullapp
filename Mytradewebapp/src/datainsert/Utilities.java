package datainsert;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Utilities {
	
	private String strFileName = null; 
	
	public String[] createDownloadURL(Date dtDate){
		String strDownloadURL = "https://www.nseindia.com/content/historical/EQUITIES/";
		Calendar dtToday = Calendar.getInstance();
		dtToday.setTime(dtDate);
		strDownloadURL = strDownloadURL + new String(new SimpleDateFormat("yyyy").format(dtToday.getTime())).toUpperCase() + "/";
		strDownloadURL = strDownloadURL + new String(new SimpleDateFormat("MMM").format(dtToday.getTime())).toUpperCase() + "/";
		strFileName = "cm" + new String(new SimpleDateFormat("ddMMMyyyy").format(dtToday.getTime())).toUpperCase() + "bhav.csv.zip";
		strDownloadURL = strDownloadURL + strFileName ;
		System.out.println(strDownloadURL);
		
		return new String[]{ strDownloadURL, strFileName };
	}

	public String saveFile(final String[] strings) throws MalformedURLException, IOException{
		downloadFile(strings[0],System.getProperty("user.home")+"\\AppData\\Local\\Temp\\EODData\\",strings[1]);
		return(strings[1]);
		}

	private void downloadFile(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		try {
			downloadFile(string, string2, string3, prepareRequestPropertyMap());
			
			//System.out.println(string + string2 + string3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void downloadFile(String link,String outputDir,String fileNameWithExtension,HashMap<String,String> requestProperty) {
		//System.out.println(link);
		HttpURLConnection httpConnection=null;
		int data=-1;
		File tempFile=new File(outputDir+fileNameWithExtension);
		if(!new File(outputDir).exists())
			new File(outputDir).mkdirs();
		//Writer which will write the file to the client site
		BufferedOutputStream localFileWriter=null;
		//Reader which will read the file from the remote site
		BufferedInputStream remoteFileReader=null;
		URL url=null;
		try{
		localFileWriter = new BufferedOutputStream(new FileOutputStream(tempFile));
		url = new URL(link);
		httpConnection = (HttpURLConnection)url.openConnection();
		 
		
		//Set the request properties
		Set<String> requestPropertyKeys=requestProperty.keySet();
		Iterator<String> iteratorKeys=requestPropertyKeys.iterator();
		while(iteratorKeys.hasNext()){
			String keyValue=iteratorKeys.next();
			httpConnection.setRequestProperty(keyValue, requestProperty.get(keyValue));	
		}
		httpConnection.setDoInput(true);
		remoteFileReader=new BufferedInputStream(httpConnection.getInputStream());
		
		while((data=remoteFileReader.read())!=-1){
			localFileWriter.write(data);
		}
		}
		catch(IOException e){
			remoteFileReader=new BufferedInputStream(httpConnection.getErrorStream());
			if(remoteFileReader!=null)
				try {
					remoteFileReader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			try {
				throw e;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		catch(Exception e){
			throw e;
		}
		finally{
			//Release resources
			if(remoteFileReader!=null)
				try {
					remoteFileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			if(localFileWriter!=null)
				try {
					localFileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			if(httpConnection !=null)
				httpConnection.disconnect();
		}
	}

	private HashMap<String,String> prepareRequestPropertyMap() {
		// TODO Auto-generated method stub
		HashMap<String,String> requestPropertyMap= new HashMap<String,String>();
		requestPropertyMap.put("user-agent", "User Agent: Java Client");
		return requestPropertyMap;
	}

	
	}

