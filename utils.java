package Factom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class utils {



	// EXECUTE POST
		public static String executePost(String targetURL, String urlParameters) {
			  HttpURLConnection connection = null;  
			  try {
			    //Create connection
				
			    URL url = new URL(targetURL);
			    connection = (HttpURLConnection)url.openConnection();
			    connection.setRequestMethod("POST");
			    connection.setRequestProperty("Content-Type", "application/json");

			    connection.setRequestProperty("Content-Length", 
			        Integer.toString(urlParameters.getBytes().length));
			    connection.setRequestProperty("Content-Language", "en-US");  

			    connection.setUseCaches(false);
			    connection.setDoOutput(true);

			    //Send request
			    DataOutputStream wr = new DataOutputStream (
			        connection.getOutputStream());
			    wr.writeBytes(urlParameters);
			    wr.close();

			    //Get Response  
			    InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
			    String line;
			    while((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			    }
			    rd.close();
			   
	
			    if (response.length() == 0 ){
			    	// if nothing is coming back, at least send the response code.
			    	// a correct commit only returns 200-ok
			    	response.append(connection.getResponseCode());
			    }
				    return response.toString();
			  } catch (Exception e) {
			    e.printStackTrace();
			    return null;
			  } finally {
			    if(connection != null) {
			      connection.disconnect(); 
			    }
			  }
			} // execute post
		// EXECUTE POST
		public static String executePostBytes(String targetURL, byte[] urlParameters) {
			  HttpURLConnection connection = null;  
			  try {
			    //Create connection
			    URL url = new URL(targetURL);
			    connection = (HttpURLConnection)url.openConnection();
			    connection.setRequestMethod("POST");
			    connection.setRequestProperty("Content-Type", "application/json");

			    connection.setRequestProperty("Content-Length", 
			        Integer.toString(urlParameters.length));
			    connection.setRequestProperty("Content-Language", "en-US");  

			    connection.setUseCaches(false);
			    connection.setDoOutput(true);

			    //Send request
			    DataOutputStream wr = new DataOutputStream (
			        connection.getOutputStream());
			    wr.write(urlParameters);;
			    wr.close();

			    //Get Response  
			    InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
			    String line;
			    while((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			    }
			    rd.close();
			    
			    if (response.length() == 0 ){
			    	// if nothing is coming back, at least send the response code.
			    	// a correct commit only returns 200-ok
			    	response.append(connection.getResponseCode());
			    }
			    return response.toString();
			  } catch (Exception e) {
			    e.printStackTrace();
			    return null;
			  } finally {
			    if(connection != null) {
			      connection.disconnect(); 
			    }
			  }
			} // execute post
		
	// EXECUTE GET
	public static String executeGet(String targetURL) {	
		  HttpURLConnection connection = null;  
		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setRequestProperty("Content-Language", "en-US");  

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);


		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
		    String line;
		    while((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    return e.getMessage();
		  } finally {
		    if(connection != null) {
		      connection.disconnect(); 
		    }
		  }
		} // execute post
	

	public static byte[] MilliTime(){
		byte[] holder;
		long ml=System.currentTimeMillis();
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(ml);
		holder= buffer.array();				
	

		//This gives you 8 bytes.  we want 6
		byte[] resp=new byte[6];
		resp[0]=holder[2];
		resp[1]=holder[3];
		resp[2]=holder[4];
		resp[3]=holder[5];
		resp[4]=holder[6];
		resp[5]=holder[7];
		
		return resp;
	}

		public static byte[] sha256Bytes(byte[] base) {
		    try{
		        MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        byte[] hash = digest.digest(base);

		        return hash;
		    } catch(Exception ex){
		       throw new RuntimeException(ex);
		    }
		}
		
		public static byte[] sha256String(String base) {
		    try{
		        MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        byte[] hash = digest.digest(base.getBytes("UTF-8"));

		        return hash;
		    } catch(Exception ex){
		       throw new RuntimeException(ex);
		    }
		}
		
		public static byte[] sha512Bytes(byte[] base) {
		    try{
		        MessageDigest digest = MessageDigest.getInstance("SHA-512");
		        byte[] hash = digest.digest(base);

		        return hash;
		    } catch(Exception ex){
		       throw new RuntimeException(ex);
		    }
		}
		
		public static byte[] sha512String(String base) {
		    try{
		        MessageDigest digest = MessageDigest.getInstance("SHA-512");
		        byte[] hash = digest.digest(base.getBytes("UTF-8"));


		        return hash;
		    } catch(Exception ex){
		       throw new RuntimeException(ex);
		    }
		}
		
		public static byte[] appendByteArrays(byte[] first,byte[] second){
			byte[] temp=new byte[first.length + second.length ];
			System.arraycopy(first, 0, temp, 0, first.length);
			System.arraycopy(second, 0, temp, first.length, second.length);	
			return temp;
		}
		public static byte[] appendByteToArray(byte[] ary,byte bt){
			byte[] temp=new byte[ary.length + 1];
			System.arraycopy(ary, 0, temp, 0, ary.length);
			temp[ary.length]=bt;
			return temp;
		}		
		
		public static String[] appendStringToArray(String[] ary,String bt){
			String[] temp=new String[ary.length + 1];
			System.arraycopy(ary, 0, temp, 0, ary.length);
			temp[ary.length]=bt;
			return temp;
		}	
		
		public static String bytesToHex( byte [] raw ) {
			String HexCharacters = "0123456789ABCDEF";
		    if ( raw == null ) {
		        return null;
		    }
		    final StringBuilder hex = new StringBuilder( 2 * raw.length );
		    for ( final byte b : raw ) {
		        hex.append(HexCharacters.charAt((b & 0xF0) >> 4))
		            .append(HexCharacters.charAt((b & 0x0F)));
		    }
		    return hex.toString();
		}	
		
		public static byte[] hexToBytes(String s) {
		    int len = s.length();
		    byte[] data = new byte[len / 2];
		    for (int i = 0; i < len; i += 2) {
		        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
		                             + Character.digit(s.charAt(i+1), 16));
		    }
		    return data;
		}
		
		
		public static byte[] IntToByteArray(int i){
			short s=(short)i;
			ByteBuffer dbuf = ByteBuffer.allocate(2);
			dbuf.putShort(s);
			byte[] bytes = dbuf.array(); // { 0, 1 }
			
			return bytes;
		}
		
	
}

