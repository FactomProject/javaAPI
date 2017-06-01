package FactomAPI;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Signature;



import net.i2p.crypto.eddsa.*;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class api {
	
	public api(){
		// constructor
	};

	public static String factomdURL="http://localhost:8088";
	public static String walletdURL="http://localhost:8089";	
	public static byte[] privateKeyBytes;
	public static byte[] publicKeyBytes;
	public static String ecAddress="";
	public static String ecPrivateAddress="";
	public static EdDSAPublicKey pubKey;
	public static EdDSAPrivateKey priKey;
	
	public static String hashLogFilePath;
	


	
	
	


/**buyEC  - 
	* runs the multiple calls to build and send a factoid to ec transaction
	*   uses local factom-walletd
	* @param FCTFromAddress    FA formatted from address
	* @param ECToAddress       EC formatted to address
	* @param ECAmount          How Many Entry Credits to Buy
	
	* @return
	* transaction ID (txid).  <p>
	* 
	* responseText=getBlockHeight(); 
**/	

public static String buyEC(String FCTFromAddress,String ECToAddress,long ECAmount) {
	String transactionID="";
	String temp="";

	long eRate=0;
	long fctCost=0;

	String params;
	
	temp=getExchangeRate();
	eRate=Long.parseLong(temp);
	
	fctCost=(long)Math.round((ECAmount * eRate) + .49) ;
	//delete transaction	delete-transaction
	
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","delete-transaction",params,1);
	
	//new transaction	"new-transaction"
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","new-transaction",params,1);
		
	
	
	//add input		add-input
	params="{\"tx-name\":\"tran1\",\"address\":\"" + FCTFromAddress + "\",\"amount\":" + fctCost + "}";
	temp=sendRPCRequest(walletdURL,"2.0","add-input",params,1);
		
	//add  ec output	add-output/add-ec-output
	params="{\"tx-name\":\"tran1\",\"address\":\"" + ECToAddress + "\",\"amount\":" + fctCost + "}";
	temp=sendRPCRequest(walletdURL,"2.0","add-ec-output",params,1);
		
		
	
	//add fee	add-fee
	params="{\"tx-name\":\"tran1\",\"address\":\"" + FCTFromAddress + "\"}";
	temp=sendRPCRequest(walletdURL,"2.0","add-fee",params,1);
		
	
	//sign	sign-transaction
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","sign-transaction",params,1);
		
	
	//compose gives you what to send to factomd
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","compose-transaction",params,1);
	
	// send it to factomd
	params=utils.getJSONValue(temp, "result");
	temp=utils.executePost(factomdURL + "/v2", params);
			
	transactionID=utils.getJSONValue(temp, "txid");
	
	return transactionID;
	
}

/**isStalled  - 
* checks for leader and blockheights to be greater than 2 blocks.

* @return
* true if stalled.  <p>
* 
* responseText=getBlockHeight(); 
**/	
  
public static boolean isStalled() {

	String heights=getBlockHeights();
	
	String dBlockHeight=utils.getJSONValue(heights, "DirectoryBlockHeight");
	String LeaderHeight=utils.getJSONValue(heights, "LeaderHeight");
	
	if (Integer.parseInt(dBlockHeight) + 2 < Integer.parseInt(LeaderHeight)) {
		return true;
	} else {
		return false;
	}

	
}
	
	/**ComposeChainCommit  - 
	 *This is not base functionality.  It builds the first entry of a chain
	* This does the transaction the hard way.  if your version of fctwallet has compose functionality, use that
	* If you are handling your own keys, this signs without fctwallet
	* 
	* @param name paying ec address
	* @param extids external ids as array of strings
	* @param data - data to be to blockchain entry
	* @param force  - boolean. dont check for Acknowledged status.  assume it is going in or if you are syncing so status will be incorrect'
	
	* @return
	* JSON document containing chainid.  Formatted for terminal<p>
	* {"Response":"11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856","Success":true}
	* or error message as Response
	* <p>
	* Example:<p>
	* String [] extids={"123","456"}
	* 
	* responseText=ComposeChainCommit("EC2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX",extids,"here is the body of your entry",true); 
	**/		
	
	public static String ComposeChainCommit(String name, String[] extids,String data,boolean force) {
		String resp="";		
		Chain c=new Chain();	
		Entry e=new  Entry();	
		int datasize=0;
		//e.setChainID(chainID);
		e.setExtIDs(extids);
		e.Content =data.getBytes();
		byte[] postData=new byte[0];
		// put external IDs in entry
		byte[] weld;
		byte[] double256Chain;
		//put content into entry		
		c.setFirstEntry(e);
		e.setEntryHash();
		
		datasize=getEntrySize(e);
		datasize=(int) datasize / 1024 + 11;
		System.out.println("cid:" + utils.bytesToHex(c.ChainID));
		double256Chain=utils.sha256Bytes(utils.sha256Bytes(c.ChainID));
		weld=utils.sha256Bytes(utils.sha256Bytes(utils.appendByteArrays(e.entryHash , c.ChainID)));

		postData=utils.appendByteToArray(postData, (byte)0);						//version
		postData=utils.appendByteArrays(postData,utils. MilliTime());		//millitime
		postData=utils.appendByteArrays(postData,double256Chain );//chainid hash hash twice
		postData=utils.appendByteArrays(postData, weld);	//commit weld
		postData=utils.appendByteArrays(postData,e.entryHash );		//hash of first entry
		postData=utils.appendByteToArray(postData, (byte)datasize);						//cost
		byte[] Sig=signData(postData);
		verifyData(postData,Sig);
		postData=utils.appendByteArrays(postData, publicKeyBytes);
		postData=utils.appendByteArrays(postData,Sig);
		
		//rI could use a json marshal here, but I want the message format to be obvious.
		String params="{\"Message\":\"" + utils.bytesToHex(postData).toLowerCase() + "\"}";
		
		resp=sendRPCRequest(factomdURL,"2.0","commit-chain",params,1);
		

		String cTranID=utils.getJSONValue(resp, "result");
		cTranID=utils.getJSONValue(cTranID, "txid");
		
		params="{\"txid\":\"" + cTranID + "\"}";
		Boolean inflag=false;
		Boolean committed=false;
		int timeout=20;
		while ((!inflag) && (timeout > 0)) {
			resp=sendRPCRequest(factomdURL,"2.0","factoid-ack",params,1);
			resp=utils.getJSONValue(resp, "result");
			resp=utils.getJSONValue(resp, "commitdata");
			resp=utils.getJSONValue(resp, "status");
			if (resp.toUpperCase().equals("TRANSACTIONACK") || resp.toUpperCase().equals("DBLOCKCONFIRMED") || force) {
				inflag=true;
				committed=true;
			} else {
				try {
					Thread.sleep(500);
				} catch (Exception Ex) {
					System.out.println("can't sleep in chain commit ack request");
					
				}
			}
			if (inflag && !committed) {
				System.out.println("Chain Not Acknowledged");
			} else {
				resp=RevealChainOrEntry(e,"Chain",true,force);
			}
		
		}
		

return resp;
	}
	

	
	
	/**ComposeEntryCommit  - 
	 *This is not base functionality.  It adds an entry to a known chain
	* This does the transaction the hard way.  if your version of fctwallet has compose functionality, use that
	* It does leave signing to the fctwallet.  
	* If you are handling your own keys, use this
	* 
	* @param name paying ec address
	* @param extids external ids as array of strings
	* @param chainID - chain id
	* @param data - data to be to blockchain entry
	* @param force  - boolean. dont check for Acknowledged status.  assume it is going in or if you are syncing so status will be incorrect'
	
	* @return
	* JSON document containing entry hash.  Formatted for terminal<p>
	* {"Response":"75668b338e44cb45998594c9cf5f36b52929d85bb616a4052b043da30319956d","Success":true}
	* or error message as Response
	* <p>
	* Example:<p>
	* String [] extids={"123","456"}
	* 
	* responseText=ComposeEntryCommit("EC2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX",extids,"11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856","here is the body of your entry"); 
	**/	
  public static String ComposeEntryCommit(String name,String[] extids,String chainID,String data,boolean force)  {
	  	String resp="";

		byte[] postData=new byte[0];

		Entry e=new Entry();
		System.out.println(chainID.toLowerCase());
		// change this.  it is client.java specific and looking for -c and -e
		e.setChainID(chainID);
		e.setExtIDs(extids);
		if (data.length() < 4) {
			e.Content = data.getBytes();
		} else {
			if (data.substring(0,4).equals("HEX:")) {
			e.Content = utils.hexToBytes(data.substring(4));
			} else {
				e.Content = data.getBytes();
			}
		}
		//now that everything is is, hash it
		e.setEntryHash();
		System.out.println(utils.bytesToHex(e.toBytes()));
		int datasize=getEntrySize(e);
		System.out.println("DATASIZE=" + datasize);
		datasize=(int) datasize / 1024 + 1;
		// build commit
		postData=utils.appendByteToArray(postData, (byte)0);			//version
		postData=utils.appendByteArrays(postData, utils.MilliTime());	//millitime
		if (e.entryHash == null) {
			e.setEntryHash();
		}
		postData=utils.appendByteArrays(postData, e.entryHash);	//hash of entry
		postData=utils.appendByteToArray(postData, (byte)datasize);	//entry cost (entry content divided by 1k
		byte[] Sig=signData(postData);
		Boolean committed=false;
		verifyData(postData,Sig);
		postData=utils.appendByteArrays(postData, publicKeyBytes);
		postData=utils.appendByteArrays(postData,Sig);
		//   send this to fctwallet for signing

		String params="{\"Message\":\"" + utils.bytesToHex(postData) + "\"}";
		//rI could use a json marshal here, but I want the message format to be obvious.
		resp=sendRPCRequest(factomdURL,"2.0","commit-entry",params,1);

		String cTranID=utils.getJSONValue(resp, "result");
		cTranID=utils.getJSONValue(cTranID, "txid");
		
		params="{\"txid\":\"" + cTranID + "\"}";
		Boolean inflag=false;
		
		int timeout=20;
		System.out.println(utils.bytesToHex(e.Content));
		while ((!inflag) && (timeout > 0)) {
			resp=sendRPCRequest(factomdURL,"2.0","factoid-ack",params,1);
			resp=utils.getJSONValue(resp, "result");
			resp=utils.getJSONValue(resp, "commitdata");
			resp=utils.getJSONValue(resp, "status");
			if (resp.toUpperCase().equals("TRANSACTIONACK") || resp.toUpperCase().equals("DBLOCKCONFIRMED") || force) {
				inflag=true;
				committed=true;
			} else {
				try {
					Thread.sleep(500);
					
				} catch (Exception Ex) {
					System.out.println("can't sleep in entry commit ack request");
					
				}
			}
			

		
		}
		if (inflag && !committed) {
			System.out.println("Chain Not Acknowledged");
		} else {
			resp=RevealChainOrEntry(e,"Entry",true,force);
			
		}	
		return resp;    
	   
  }
  

  
	/**getBlockHeight  - 
	 * returns current height of the blockchain
	* 


	* @return
	* JSON document containing height.  <p>
	* 
	* responseText=getBlockHeight(); 
	**/		
	
	public static String getBlockHeight() {
		try {

		String params="\"\"";
	
		String resp=sendRPCRequest(factomdURL,"2.0","heights",params,1);
		resp=utils.getJSONValue(resp, "result");
		resp=utils.getJSONValue(resp, "directoryblockheight");
		return resp;		
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	/**getBlockHeights  - 
	 * returns current height of the blockchain


	* @return
	* JSON document containing height.  <p>
	* 
	* responseText=getBlockHeight(); 
	**/		
	
	public static String getBlockHeights() {
		try {

		String params="\"\"";
		String resp=sendRPCRequest(factomdURL,"2.0","heights",params,1);
		resp=utils.getJSONValue(resp, "result");
		
		return resp;		
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	

	
	/**getChainHead  - 
	 * Get the last entry (hash) in this chain
	* 
	* @param ChainID  hex value of chain id

	* @return
	* JSON document containing entry.  Formatted for terminal<p>
	* 
	* responseText=getChainHead("chainID"); 
	**/		
	
	public static String getChainHead(String ChainID) {

		
		try {
		String params="{\"chainid\":\"" + ChainID + "\"}";
		String resp=sendRPCRequest(factomdURL,"2.0","chain-head",params,1);
		resp=utils.getJSONValue(resp, "result");
		
		
		return resp;		
		} catch (Exception e) {
			return e.getMessage();
		}

	}
  
	
	/**getECAddressFromPublicKey  - 
	 * get the Entry Credit address for this key
	* 
	* @param key 
	* 32 byte[] private key

	* @return
	* JSON document containing entry.  Formatted for terminal<p>
	* 
	* responseText=getEntryCreditBalance(bytes[]); 
	**/
	  public static String getECAddressFromPublicKey(byte[] key){
          String address = "";
          byte[] appendPrefix = new byte[34];
          byte[] appendSuffix = new byte[38];
          byte[] firstHash;
          appendPrefix[0] =0x59;
          appendPrefix[1] =0x2a;
          System.arraycopy(key, 0, appendPrefix, 2, 32);
          firstHash = utils.sha256Bytes(utils.sha256Bytes(appendPrefix));
          System.arraycopy(appendPrefix, 0, appendSuffix, 0, 34);
          appendSuffix[34] = firstHash[0];
          appendSuffix[35] = firstHash[1];
          appendSuffix[36] = firstHash[2];
          appendSuffix[37] = firstHash[3];
          address = Encode256to58(appendSuffix);
          ecAddress=address;
          return address;		  
		
	  } 
	  
	
		/**getECPrivateAddressFromKey  - 
		 * get the Factom Entry Credit Private address for this key
		* 
		* @param key 
		* 32 byte[] private key

		* @return
		* JSON document containing entry.  Formatted for terminal<p>
		* 
		* responseText=getEntryCreditBalance(bytes[]); 
		**/		  
	  public static String getECPrivateAddressFromKey(byte[] key){
          String address = "";
          byte[] appendPrefix = new byte[34];
          byte[] appendSuffix = new byte[38];
          byte[] firstHash;
          appendPrefix[0] =0x5d;
          appendPrefix[1] =(byte) 0xb6;
          System.arraycopy(key, 0, appendPrefix, 2, 32);
          firstHash = utils.sha256Bytes(utils.sha256Bytes(appendPrefix));
          System.arraycopy(appendPrefix, 0, appendSuffix, 0, 34);
          appendSuffix[34] = firstHash[0];
          appendSuffix[35] = firstHash[1];
          appendSuffix[36] = firstHash[2];
          appendSuffix[37] = firstHash[3];
          address = Encode256to58(appendSuffix);
          ecPrivateAddress=address;
          return address;		  
		
	  } 
	  
	  
	  
		/**getFactoidBalance  - 
		 * returns the number of entry credits in the entry credit address
		* @param Address 
		*   This is the entry hash.  see the entry class or you got it from an entry block
		*   Response is denominated in factoshi (1/100000000 factoid)
		* @return
		* JSON document as string.  <p>
		* {"Response":"25352498353","Success":true}
		* <p>
		* Example:<p>
		* responseText=getFactoidBalance("FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q"); 
		**/
		public static String getFactoidBalance(String Address) {
			String resp="";
			
			try {

				String params="{\"address\":\"" + Address + "\"}";
				String temp=sendRPCRequest(factomdURL,"2.0","factoid-balance",params,1);
				resp=utils.getJSONValue(temp, "result");
				resp=utils.getJSONValue(resp, "balance");

			} catch (Exception e) {
				// this is only going to return an error on connectivity or some other communication error
				e.printStackTrace();
				resp="Error";
			}
			return resp;
		} // end of getFactoidBalance
		
	  
	  
	  
		/**getPrivateIdentityAddressFromKey  - 
		 * get the Factom Identity Address for this key
		* 
		* @param key 
		* 32 byte[] private key
		* @param keynumber 
		* Identity uses 4 keys.  which is it

		* @return
		* JSON document containing entry.  Formatted for terminal<p>
		* 
		* responseText=getPrivateIdentityAddressFromKey(bytes[],1); 
		**/		  
	  public static String getPrivateIdentityAddressFromKey(byte[] key,int keynumber){
      String address = "";
      byte[] appendPrefix = new byte[35];
      byte[] appendSuffix = new byte[39];
      byte[] firstHash;
      if (keynumber==1){
			appendPrefix[0] =(byte) 0x4d;
			appendPrefix[1] =(byte) 0xb6;
			appendPrefix[2] =(byte) 0xc9;
		} else if (keynumber==2){
			appendPrefix[0] =(byte) 0x4d;
			appendPrefix[1] =(byte) 0xb6;
			appendPrefix[2] =(byte) 0xe7;
		} else if (keynumber==3){
			appendPrefix[0] =(byte) 0x4d;
			appendPrefix[1] =(byte) 0xb7;
			appendPrefix[2] =(byte) 0x05;
		} else if (keynumber==4){
			appendPrefix[0] =(byte) 0x4d;
			appendPrefix[1] =(byte) 0xb7;
			appendPrefix[2] =(byte) 0x23;
		} 
      System.arraycopy(key, 0, appendPrefix, 3, 32);
      firstHash = utils.sha256Bytes(utils.sha256Bytes(appendPrefix));
      System.arraycopy(appendPrefix, 0, appendSuffix, 0, 35);
      appendSuffix[35] = firstHash[0];
      appendSuffix[36] = firstHash[1];
      appendSuffix[37] = firstHash[2];
      appendSuffix[38] = firstHash[3];
      address = Encode256to58(appendSuffix);
      ecPrivateAddress=address;
      return address;		  
		
	  } 
	  
		/**getPublicIdentityAddressFromKey  - 
		 * get the Factom Identity Address for this key
		 * This is Factom specific.  If you would like to use another method, you can.  But you do not have to.
		*   This obscures the public identity keys be prepending a one a sha256d (sha256 twice) to have a provable but not exposed value
		* @param key 
		* 32 byte[] private key
		* @param keynumber 
		* Identity uses 4 keys.  which is it

		* @return
		* JSON document containing entry.  Formatted for terminal<p>
		* 
		* responseText=getPrivateIdentityAddressFromKey(bytes[],1); 
		**/		  
	  public static String getPublicIdentityAddressFromKey(byte[] key,int keynumber){
      String address = "";
      byte[] prep=new byte[33];
      byte[] rcd=new byte[32];
      //prepend 0x01      
      prep[0]=0x01;
      System.arraycopy(key, 0, prep, 1, 32); 
      // then sha256d
      rcd=utils.sha256Bytes(utils.sha256Bytes(prep));
      
      
      byte[] appendPrefix = new byte[35];
      byte[] appendSuffix = new byte[39];
      byte[] firstHash;
      if (keynumber==1){
			appendPrefix[0] =(byte) 0x3f;
			appendPrefix[1] =(byte) 0xbe;
			appendPrefix[2] =(byte) 0xba;
		} else if (keynumber==2){
			appendPrefix[0] =(byte) 0x3f;
			appendPrefix[1] =(byte) 0xbe;
			appendPrefix[2] =(byte) 0xd8;
		} else if (keynumber==3){
			appendPrefix[0] =(byte) 0x3f;
			appendPrefix[1] =(byte) 0xbe;
			appendPrefix[2] =(byte) 0xf6;
		} else if (keynumber==4){
			appendPrefix[0] =(byte) 0x3f;
			appendPrefix[1] =(byte) 0xbf;
			appendPrefix[2] =(byte) 0x14;
		} 
      System.arraycopy(rcd, 0, appendPrefix, 3, 32);
      firstHash = utils.sha256Bytes(utils.sha256Bytes(appendPrefix));
      System.arraycopy(appendPrefix, 0, appendSuffix, 0, 35);
      appendSuffix[35] = firstHash[0];
      appendSuffix[36] = firstHash[1];
      appendSuffix[37] = firstHash[2];
      appendSuffix[38] = firstHash[3];
      address = Encode256to58(appendSuffix);
      ecPrivateAddress=address;
      return address;		  
		
	  } 
	  
	  
	  


		/**getDirectoryBlock  - 
		 * hit Factomd for the merkle root of the 
		* 
		* @param blockmr - Merkle Root of block sought
		* 
		* @return
		* JSON document containing Directory Block.  <p>
		 
		* responseText=getDirectoryBlockHead("b4b7edb9f944d245a5f676bdebcc46536b2620aeed13cabc3dc2772fead6c7cf"); 
		**/		
		
		public static String getDirectoryBlock(String blockmr) {
			try {

			String params="{\"keymr\":\"" + blockmr + "\"}";
			String resp=sendRPCRequest(factomdURL,"2.0","directory-block",params,1);
			resp=utils.getJSONValue(resp, "result");
			return resp;		
			} catch (Exception e) {
				return e.getMessage();
			}
		}
		
		
		
		
		/**getDirectoryBlockHead  - 
		 * hit Factomd for the merkle root of the 
		* 
		* @return
		* JSON document containing the Merkle Root of the latest Directory Block.  <p>
		* Use this root to get the directory Block itself <p>
		* responseText=getDirectoryBlockHead(); 
		**/		
		
		public static String getDirectoryBlockHead() {
			try {

			String params="\"\"";
			String resp=sendRPCRequest(factomdURL,"2.0","directory-block-head",params,1);
			resp=utils.getJSONValue(resp, "result");
			return resp;		
			} catch (Exception e) {
				return e.getMessage();
			}
		}
		

	  
	/**getEBlock  - 
	 * hit Factomd for the entry for this hash
	* 
	* @param keyMR key MR of entry block

	* @return
	* JSON document containing entry.  Formatted for terminal<p>
	* 
	* responseText=getEBlock("EC2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX"); 
	**/		
	
	public static String getEBlock(String keyMR) {

		
		try {

		String params="{\"keymr\":\"" + keyMR + "\"}";
		String resp=sendRPCRequest(factomdURL,"2.0","entry-block",params,1);
		resp=utils.getJSONValue(resp, "result");
		return resp;		
		} catch (Exception e) {
			return e.getMessage();
		}

	}
  
  
	/**getEntry  - 
	 * hit Factomd for the entry for this hash
	* 
	* @param hash entry Hash

	* @return
	* JSON document containing entry.  Formatted for terminal<p>
	* 
	* responseText=getEntryCreditBalance("EC2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX"); 
	**/		
	
	public static String getEntry(String hash) {

		
		try {

		String params="{\"hash\":\"" + hash + "\"}";
		String resp=sendRPCRequest(factomdURL,"2.0","entry",params,1);
		resp=utils.getJSONValue(resp, "result");
		return resp;		
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	
	/**getEntryCreditBalance  - 
	 * hit Factomd for the balance on this address
	* 
	* @param Address ec address

	* @return
	* JSON document containing balance.  Formatted for terminal<p>
	* 
	* responseText=getEntryCreditBalance("EC2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX"); 
	**/		
	
	public static String getEntryCreditBalance(String Address) {
		try {
			String params="{\"address\":\"" + Address + "\"}";
			String temp=sendRPCRequest(factomdURL,"2.0","entry-credit-balance",params,1);
			String resp=utils.getJSONValue(temp, "result");
			resp=utils.getJSONValue(resp, "balance");

		return resp;		
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	/**getExchangeRate  - 
	 * current Exchange rate for EC


	* @return
	* JSON document as string.  <p>
	* {"Response":"0.000002","Success":true}
	* <p>
	* Example:<p>
	* responseText=getFee("testtran"); 
	**/
	public static String getExchangeRate() {
		String resp="";
		
		try {
			String params="\"\"";
			resp=sendRPCRequest(factomdURL,"2.0","entry-credit-rate",params,1);
			resp=utils.getJSONValue(resp, "result");
			resp=utils.getJSONValue(resp, "rate");
			} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetFee
	
	
	
	/**getFee  - 
	 * returns the number of entry credits in the entry credit address
	* @param TransactionName 
	*   This is for a transaction you are building
	*     - An alternate functionality is with no transaction name.  that returns current 
	*     - entry credit exchange rate.  This will be deprecated.  please use GetExchangeRate for this.
	* @return
	* JSON document as string.  <p>
	* {"Response":"0.000002","Success":true}
	* <p>
	* Example:<p>
	* responseText=getFee("testtran"); 
	* 
	* 
	* DEPRECATED with v2
	**/
	public static String getFee(String TransactionName) {
		String resp="";
		
		try {
			if (TransactionName.equals("")) {
				resp=utils.executeGet(factomdURL + "/v1/factoid-get-fee/");	
				
			} else {
				//resp=utils.executeGet(fctwalletURL + "/v1/factoid-get-fee/?key=" + TransactionName);				
			}

		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetFee
	
	
	/**getEntrySize  - 
	 * returns the size of this entry to determine how many entry credits are needed for entry
	*  Entry credit length is made of content and external IDs.  block structure information is not counted
	* @param Entry e

	* @return
	* JSON document containing height.  <p>
	* 
	* responseText=getBlockHeight(); 
	**/		 
private static int getEntrySize(Entry e){
	int resp=0;
	
	for (int i=0;i<e.ExtIDs .length ;i++){
	resp=resp + 2; // accounts for extid counter in commit message.		
	resp=resp + e.ExtIDs [i].length ;
	}
	resp=resp + e.Content .length ;

	
	return resp;
}



/**getProperties  - 
 * returns first entry in provided chain
* @return
* JSON document as string.  <p>
* {"Response":"Protocol Version:   0.1.5\nfactomd Version:    0.3.4\nfctwallet Version:  0.1.4\n","Success":true}
* <p>
* Example:<p>
* responseText=getProperties() ; 
**/	
public static String getProperties() {
	String resp="";
	
	try {
		String params="\"\"";
		resp=sendRPCRequest(walletdURL,"2.0","properties",params,1);
		resp=utils.getJSONValue(resp, "result");


		
	} catch (Exception e) {
		// this is only going to return an error on connectivity or some other communication error
		e.printStackTrace();
		resp="Error";
	}
	return resp;
} // end of addOutput


/**getEntryStatus  - 
 * @param entryHash id or entry hash
 * 
* @return
* JSON document as string. 

**/	
public static String getEntryStatus(String entryHash) {
	String resp="";
	
	try {
		String params="{\"txid\":\"" + entryHash + "\",\"force\":false}";
		String temp=sendRPCRequest(factomdURL,"2.0","entry-ack",params,1);
		
	} catch (Exception e) {
		// this is only going to return an error on connectivity or some other communication error
		e.printStackTrace();
		resp="Error";
	}
	return resp;
} // end of addOutput

/**getFactoidStatus  - 
 * @param txid id or entry hash
 * 
* @return
* JSON document as string. 

**/	
public static String getFactoidStatus(String txid) {
	String resp="";
	
	try {
		String params="{\"txid\":\"" + txid + "\",\"force\":false}";
		String temp=sendRPCRequest(factomdURL,"2.0","factoid-ack",params,1);
		
	} catch (Exception e) {
		// this is only going to return an error on connectivity or some other communication error
		e.printStackTrace();
		resp="Error";
	}
	return resp;
} // end of addOutput


/**getRawData  - 
 * This is a generic search call.  It should work for Merkle Roots for various types of blocks  (or an entry hash)
	* @param searchKey - Merkle Root of block sought
	* 
* @return
* Byte Data
* responseText=getRawData(); 
**/		

public static String getRawData(String searchKey) {
	try {

	String params="{\"hash\":\"" + searchKey + "\"}";
	String resp=sendRPCRequest(factomdURL,"2.0","raw-data",params,1);
	resp=utils.getJSONValue(resp, "result");

	return resp;		
	} catch (Exception e) {
		return e.getMessage();
	}
}


/**importPrivateKey  - 
 * takes a private ec address and sets the api to use it
	* @param key -string
	* 

**/		
public static void importPrivateKey(String key){
	  if (key.toUpperCase().substring(0, 2).equals("ES")) {
		  byte[] pk=Encode58addressto256(key);
		  byte[] seed=new byte[32];
		  for (int i=0;i<32;i++){
			  seed[i]=pk[i+2];
		  }
		  setAddressFromSeed(seed);
		
	  }else if (key.toUpperCase().substring(0, 2).equals("FS")) {
		  // not yet implemented
	  } else {
		  System.out.println("invalid key type");
	  }
}




// called by compose
	/**RevealChainOrEntry  - 
	 * Adding chains and entries is a two step process.  first commit.  This is Factom committing to post your entry
	 * Then you reveal what that entry is
	* 
	* @param  e entry structure (minus chainid) for this first entry 
	* @param RevealType - String - 'Chain' or 'Entry'
	* @param retry  - boolean.  retry the reveal if it fails the first time'
	* @param force  - boolean. dont check for Acknowledged status.  assume it is going in or if you are syncing so status will be incorrect'
	* @return
	* JSON document containing entry hash.  Formatted for terminal<p>
	* {"Response":"75668b338e44cb45998594c9cf5f36b52929d85bb616a4052b043da30319956d","Success":true}
	* or error message as Response
	* <p>
	* Example:<p>

	* responseText=RevealChainOrEntry(e,"Chain"); 
	**/	
public static String RevealChainOrEntry(Entry e,String RevealType,boolean retry,boolean force) {
		String resp="";
		int i;
		int extidlength=0;
		String temp="";
		byte[] postData=new byte[0];
		// put external IDs in entry
System.out.println(utils.bytesToHex(e.Content));

		postData=utils.appendByteToArray(postData, (byte)0);						//version
		postData=utils.appendByteArrays(postData, e.ChainID);						//ChainID

		for (i=0;i<e.ExtIDs .length ;i++){
			extidlength=extidlength + e.ExtIDs[i].length + 2;
		}
		
		postData=utils.appendByteArrays(postData,  utils.IntToByteArray(extidlength ));	//extid size (2 bytes) length of all extid data + item counts
		
		for ( i=0;i< e.ExtIDs .length ;i++){  //add each ext id
				postData=utils.appendByteArrays(postData, utils.IntToByteArray(e.ExtIDs[i].length ) );				//extid size (2 bytes)
				postData=utils.appendByteArrays(postData, e.ExtIDs [i]);
		}
		postData=utils.appendByteArrays(postData, e.Content);	//extid size (2 bytes)
		
		String params="{\"Entry\":\"" + utils.bytesToHex(postData) + "\"}";
		
		resp=sendRPCRequest(factomdURL,"2.0","reveal-entry",params,1);

		String cTranID=utils.getJSONValue(resp, "result");
		cTranID=utils.getJSONValue(cTranID, "entryhash");
		
		params="{\"txid\":\"" + cTranID + "\"}";
		Boolean inflag=false;
		Boolean committed=false;
		int timeout=10;
		while ((!inflag) && (timeout > 0)) {
			resp=sendRPCRequest(factomdURL,"2.0","entry-ack",params,1);
			resp=utils.getJSONValue(resp, "result");
			resp=utils.getJSONValue(resp, "entrydata");
			resp=utils.getJSONValue(resp, "status");
			if (resp.toUpperCase().equals("TRANSACTIONACK") || resp.toUpperCase().equals("DBLOCKCONFIRMED") || force) {
				inflag=true;
				committed=true;
			} else {
				try {
					Thread.sleep(500);
				} catch (Exception Ex) {
					System.out.println("can't sleep in entry commit ack request");
					
				}
			}
			if (inflag && !committed) {
				if (RevealType.equals("Entry")) {
					resp="Entry Not Acknowledged";
				} else if (RevealType.equals("Chain")) {
					resp="Chain Not Acknowledged";
				}				
				System.out.println(resp);
			} else {
				if (RevealType.equals("Entry")) {
					resp=utils.bytesToHex(e.entryHash);
				} else if (RevealType.equals("Chain")) {
					resp=utils.bytesToHex(e.ChainID);
				}
			}
		 timeout --;
		}
		if ((timeout==0) && (retry == true)) {
			//recursive without retry
			RevealChainOrEntry(e,RevealType,false,force);
		}
		
		
		return resp;    
	   
}



/**sendFactoid  - 
	* runs the multiple calls to build and send a factoid to ec transaction
	*   uses local factom-walletd
	* @param FromAddress - Factoid address
	* @param ToAddress - factoid address
	* @param Amount - factoshi
	
	* @return
	* transaction ID (txid).  <p>
	* 
	* responseText=getBlockHeight(); 
**/	

public static String sendFactoid(String FromAddress,String ToAddress,long Amount) {
	String transactionID="";
	String temp="";

	String params;

	//delete transaction just in case it is in wallet already.  an error is OK here	delete-transaction
	
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","delete-transaction",params,1);
	
	//new transaction	"new-transaction"
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","new-transaction",params,1);
		
	// IF YOU ARE ADDING MULTIPLE INPUTS OR OUTPUTS, THAT HAPPENS HERE
	
	//add input		add-input
	params="{\"tx-name\":\"tran1\",\"address\":\"" + FromAddress + "\",\"amount\":" + Amount + "}";
	temp=sendRPCRequest(walletdURL,"2.0","add-input",params,1);
		
	//add  ec output	add-output/add-ec-output
	params="{\"tx-name\":\"tran1\",\"address\":\"" + ToAddress + "\",\"amount\":" + Amount + "}";
	temp=sendRPCRequest(walletdURL,"2.0","add-ec-output",params,1);
		
		
	
	//add fee	add-fee
	params="{\"tx-name\":\"tran1\",\"address\":\"" + FromAddress + "\"}";
	temp=sendRPCRequest(walletdURL,"2.0","add-fee",params,1);
		
	
	//sign	sign-transaction
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","sign-transaction",params,1);
		
	
	//compose gives you what to send to factomd
	params="{\"tx-name\":\"tran1\",\"force\":false}";
	temp=sendRPCRequest(walletdURL,"2.0","compose-transaction",params,1);
	
	// send it to factomd
	params=utils.getJSONValue(temp, "result");
	temp=utils.executePost(factomdURL + "/v2", params);
			
	transactionID=utils.getJSONValue(temp, "txid");
	
	return transactionID;
	
}
  



/**sendRPCRequest  - 
  	* @param ipAddress - IP address for factomd or factom-walletd
 	* @param version - rpc version 2.0 for now
 	* @param method - this is the factom action you are trying to execute
 	* @param params - this is usually a json document 
 	* @param nonce - Merkle Root of block sought
	* 
	* @return response from factomd

**/	
public static String sendRPCRequest(String ipAddress,String version,String method,String params, long nonce ){
	String rpc = "{\"jsonrpc\":\"" + version + "\",";
	rpc += "\"method\":\"" + method + "\",";
	rpc += "\"params\":" + params + ",";
	rpc += "\"id\":" + nonce + "}";

	
	String resp=utils.executePost(ipAddress + "/v2", rpc);

	
	return resp;
}
/**setAddressFromSeed  - 
 * takes a 32 byte private key and sets the api to use it
 *  (also called by importprivatekey after key is converted to 32 bytes)
	* @param seed byte[32]
	* 

**/	
public static void setAddressFromSeed(byte[] seed){
	
	  try{

		  EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("ed25519-sha-512"); 
		  EdDSAPrivateKeySpec privateKeySpec = new EdDSAPrivateKeySpec(seed, spec); 
		  EdDSAPublicKeySpec pubKeySpec = new EdDSAPublicKeySpec(privateKeySpec.getA(), spec); 
		  EdDSAPublicKey publicKey = new EdDSAPublicKey(pubKeySpec); 
		  EdDSAPrivateKey privateKey = new EdDSAPrivateKey(privateKeySpec); 
		  pubKey=publicKey;
		  priKey=privateKey;

 
		 privateKeyBytes=seed;
		 publicKeyBytes=publicKey.getAbyte();
		 getECPrivateAddressFromKey(seed);
		 ecAddress=getECAddressFromPublicKey(publicKeyBytes);
	
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  
	  
	  

}

/**signData  - 
 * takes data as byte string to be signed by the private key that is already loaded into api
	* @param data byte[]
	* 
* @return
* 32 byte signature
* 
**/	

  public static byte[] signData(byte[] data){
	  byte[] resp=new byte[0];
	  try{
		/*  EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("ed25519-sha-512"); 
		  EdDSAPrivateKeySpec privateKeySpec = new EdDSAPrivateKeySpec(signingKey, spec);
	      PrivateKey pk=new EdDSAPrivateKey(privateKeySpec);
	      */	      
	      Signature sgr = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));

		  sgr.initSign(priKey);
		  sgr.update(data);
		return sgr.sign();
	  } catch (Exception e) {
	  return resp;		  
	  }
	  
  }
  
  

  /**verifyData  - 
   * verifies that the data was signed by the private key
   * sig is probably found in api.publickeybytes if you are checking against your private key
  	* @param data byte[]
  	* @param sig byte[]
  	* 
  * @return
  * true or false
  * 
  **/	 
  
  public static Boolean verifyData(byte[] data,byte[] sig){
	  
	  try{
	/*	  EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("ed25519-sha-512"); 
		  EdDSAPublicKeySpec publicKeySpec = new EdDSAPublicKeySpec(signingKey, spec);
	  PublicKey pk=new EdDSAPublicKey(publicKeySpec);
	  */
	  Signature sgr = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
		sgr.initVerify(pubKey);
		sgr.update(data);
		if (sgr.verify(sig)) {
			return true;
		} else {
			return false;
		}
		
	  } catch (Exception e) {
	  return false;		  
	  }
	  
  }
 	  

  /**Encode256to58  - 
   * 256 to 58 base conversion for use in handling byt to or from human readable addresses
  	* @param data byte[]

  	* 
  * @return
  	*  converted byte[]
  * 
  **/		  
      public static String Encode256to58(byte[] data)
      {
    	  // you are doing signed integer math here.  it may be unsigned in the rest of the world.  watch it
          String code_string = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
          String strResponse = "";
          int unsig=0;
          byte[] response=new byte[52];
          // move bytestring to integer
          BigInteger intData = BigInteger.valueOf(0);
          for (int i = 0; i < data.length; i++)
          {
              intData = intData .multiply(BigInteger.valueOf(256));
              unsig=data[i] & 0xff;
              intData= intData.add((BigInteger.valueOf(unsig))) ;
          }

          // Encode BigInteger to Base58 string

          int j = 0;
          while (intData.compareTo(BigInteger.valueOf(0)) > 0)
          {
              byte remainder = (intData.mod(BigInteger.valueOf(58))).byteValue();
              intData = intData.divide(BigInteger.valueOf(58));
              strResponse =  code_string.substring(remainder, remainder+1) + strResponse ;
              response[j] = remainder ;
              j = j + 1;
          }	 
          return strResponse;
      }
      

      /**Encode58addressto256  - 
       * 58 to 256 base conversion for use in handling byt to or from human readable addresses
      	* @param data byte[]

      	* 
      * @return
      	*  converted byte[]
      * 
      **/
      private static byte[] Encode58addressto256(String data)
      {
    	  // you are doing signed integer math here.  it may be unsigned in the rest of the world.  watch it
          String code_string = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
          int pos=0;
          byte[] response=new byte[39];
          // move bytestring to integer
          BigInteger intData = BigInteger.valueOf(0);
          for (int i = 0; i < data.length(); i++)
          {
        	  pos=code_string.indexOf(data.substring(i,i+1));
              intData = intData .multiply(BigInteger.valueOf(58));
         	  intData=intData.add(BigInteger.valueOf(pos));             
             // unsig=data[i] ;
          }

          // Encode BigInteger to Base256 


          byte[] tmp=new byte[1];
          while (intData.compareTo(BigInteger.valueOf(0)) > 0)
          {
              int remainder = intData.mod(BigInteger.valueOf(256)).intValueExact();
              intData = intData.subtract(BigInteger.valueOf(remainder));
              intData = intData.divide(BigInteger.valueOf(256));
              tmp[0]=(byte)remainder;
              response=utils.appendByteArrays( tmp,response);
              //response[j] = remainder ;
          }	 
          return response;
      }
}