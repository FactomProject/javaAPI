package FactomAPI;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


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
			    System.out.println(response.toString());
				    return response.toString();
			  } catch (Exception e) {
			    e.printStackTrace();
				  System.out.println(e.getMessage());
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
		
		// EXECUTE POST
		public static String executePostWithCredentials(String targetURL, String urlParameters,String userName,String password) {
			  HttpURLConnection connection = null;  
			  try {
			    //Create connection
				  String userPassword = userName + ":" + password;
				  String encoded = Base64.getEncoder().encodeToString(userPassword.getBytes("UTF-8"));

			    URL url = new URL(targetURL);
			    connection = (HttpURLConnection)url.openConnection();
			    connection.setRequestMethod("POST");
			    connection.setRequestProperty("Content-Type", "application/json");
			    connection.setRequestProperty("Authorization", "Basic " + encoded);	
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
	

	
	
// EXECUTE GET
public static String executeGetWithCredentials(String targetURL,String userName,String password) {	
	  HttpURLConnection connection = null;  
	  try {
	    //Create connection
		  
		  String userPassword = userName + ":" + password;
		  String encoded = Base64.getEncoder().encodeToString(userPassword.getBytes("UTF-8"));
	    URL url = new URL(targetURL);
	    connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setRequestProperty("Content-Language", "en-US");  
connection.setRequestProperty("Authorization", "Basic " + encoded);
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
		  System.out.println(e.getMessage());
	    return e.getMessage();
	  } finally {
	    if(connection != null) {
	      connection.disconnect(); 
	    }
	  }
	} // execute post

public static String executeGetMashape(String targetURL) {	
	  HttpURLConnection connection = null;  
	  try {
	    //Create connection
		  

	    URL url = new URL(targetURL);
	    connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setRequestProperty("Content-Language", "en-US");  
	    connection.setRequestProperty("Accept", "application/json");
	    connection.setRequestProperty("X-Mashape-Key", "xDj9bOCj3ImshKnnLc2vILub3lJbp1oNSSkjsn9CPGmlkD7jWs");
	    connection.setUseCaches(false);
	    connection.setDoOutput(true);

	  //  curl --get --include 'https://bravenewcoin-v1.p.mashape.com/prices?coin=btc' \
	   // -H 'X-Mashape-Key: xDj9bOCj3ImshKnnLc2vILub3lJbp1oNSSkjsn9CPGmlkD7jWs' \
	  //  -H 'Accept: application/json'
	   // 
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
		  System.out.println(e.getMessage());
	    return e.getMessage();
	  } finally {
	    if(connection != null) {
	      connection.disconnect(); 
	    }
	  }
	} // execute post

public static JSONArray getJsonArray(String json,String val){

	JSONTokener jt;
	JSONObject jo;

	JSONArray ary;
	try {

	jt=new JSONTokener(json);
	jo=(JSONObject) jt.nextValue();
	ary=jo.getJSONArray(val);
	return ary;	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}


public static String getJSONValue(String JsonDoc,String FieldName) {
	String resp="";
	try {
	JSONTokener jt=new JSONTokener(JsonDoc);
	JSONObject jo=(JSONObject) jt.nextValue();

	resp=jo.getString(FieldName);
	} catch (Exception e) {
		//e.printStackTrace();
		//logAlert("Can't Read JSON value: " + FieldName + " in " + JsonDoc);
	}
	return resp;
}
	
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
	
		public static String randomString( int len ){
			String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			Random rnd = new Random();	
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
		}
	

		
		public static String readfile(String path){
			  String resp="";
			  File f;
			  FileInputStream inp;
			  try {
			          f = new File(path);
			          inp = new FileInputStream(f);
			          byte[] bf = new byte[(int)f.length()];
			          inp.read(bf);
			          resp = new String(bf, "UTF-8");
			            inp.close();
			      } catch (FileNotFoundException e) {
			          e.printStackTrace();
			      } catch (IOException e) {
			          e.printStackTrace();
			      } 
			  return resp;
			}
			
		public static String readfile(File path){
			  String resp="";
			  FileInputStream inp;
			  try {
			          inp = new FileInputStream(path);
			          byte[] bf = new byte[(int)path.length()];
			          inp.read(bf);
			          resp = new String(bf, "UTF-8");
			            inp.close();
			      } catch (FileNotFoundException e) {
			          e.printStackTrace();
			      } catch (IOException e) {
			          e.printStackTrace();
			      } 
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
		        byte[] enc=base.getBytes("UTF-8");
		        byte[] hash = digest.digest(enc);


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
		public static byte[][] appendByteArraytoByteMap(byte[][] first,byte[] second){
			byte[][] temp=new byte[first.length + 1 ][];
			for (int i =0 ;i < first.length ;i++){
				temp[i]=first[i];
			}
			temp[first.length]=second;

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
		
		   public static String hexToText(String hex)
		   {
		      StringBuilder output = new StringBuilder("");
		      for (int i = 0; i < hex.length(); i += 2)
		      {
		         String str = hex.substring(i, i + 2);
		         output.append((char) Integer.parseInt(str, 16));
		      }
		      return output.toString();
		   }
		
		public static byte[] IntToByteArray(int i){
			short s=(short)i;
			ByteBuffer dbuf = ByteBuffer.allocate(2);
			dbuf.putShort(s);
			byte[] bytes = dbuf.array(); // { 0, 1 }
			
			return bytes;
		}
		
	  public static boolean isAlphaNumeric(String check) {
	
		if (check.matches("[a-zA-Z0-9]")) {
			return true;
		} else {
			return false;
		}

	
	  }
	  
	  
	  public static String bytesToEntryCreditAddress(byte[] key){
		  String address="";
		  
		  
		  return address;
		  
	  }
	  public static byte[] bigToLittleEndian(byte[] bigendian) {
		    ByteBuffer buf = ByteBuffer.allocate(bigendian.length );
			    buf.put(bigendian);	  
		    buf.order(ByteOrder.BIG_ENDIAN);

		 
		    buf.order(ByteOrder.LITTLE_ENDIAN);
		    byte[] li=buf.array();
		    return buf.array();
		}
	  
	  
	  public static void checkpath(String directoryName){

		    File direc = new File(directoryName);
		   
		    // if the directory does not exist, create it
		    if (!direc.exists())
		    {
		      System.out.println("creating directory: " + directoryName);
		      System.out.println(direc.mkdir());
		    }
		  
	  }
	  

		public static void saveEntryToHashFile(Entry ent,String fname) {
	        BufferedWriter writer = null;
	        System.out.println("Saving entry to " + fname);
	        try {
	        	
	        
	        	 File logFile = new File(fname);
   

	            // This will output the full path where the file will be written to...
	           // System.out.println(body);

	            writer = new BufferedWriter(new FileWriter(logFile));
	            writer.write("[HASH]");
	            writer.newLine();
	            writer.write(utils.bytesToHex(ent.entryHash));
	            writer.newLine();
	            writer.write("[CHAIN]");
	            writer.newLine();
	            writer.write(utils.bytesToHex(ent.ChainID));
	            writer.newLine();
	            writer.write("[EXTERNAL IDS]");
	            writer.newLine();
	            for (int i=0;i< ent.ExtIDs.length ;i++){
	            	writer.write(utils.bytesToHex(ent.ExtIDs [i]));
	            	writer.newLine();
		            }
	            writer.write("[CONTENT]");
	            writer.newLine();
	            writer.write(utils.bytesToHex(ent.Content));

	 	        } catch (Exception e) {
	        	System.out.println(e.getMessage());
	            e.printStackTrace();
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            	
	            	System.out.println(e.getMessage());
	            }
	        }
		}
	  
}
