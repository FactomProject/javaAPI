package Factom;

public class apiCalls {
	/**
	 * @author matt whittington
	 * @version 0.1
	 * 
	 * Much of the code in this project could be done in a more elegant manner.
	 * In addition to this being a usable class, this is also supposed to be an
	 * example of what needs to be done.  Elegant code can obfuscate functionality
	 * in deference to clean elegant code.   
	 * This class can be used or taken as a reference.
	 *  
	 *
	 */
	
	static String fctwalletURL="http://localhost:8089";
	static String factomdURL="http://localhost:8088";
	

	
	//AddFee  - 
	// takes transaction name
	// Address and or address to take the fees from
	// returns amount (factoshi) that will be used in addfee.  so you know
	public static String AddFee(String TransactionName,String AddressName) {
		String resp="";
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-add-fee/?key=" + TransactionName + "&name=" + AddressName,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput
	
	
	
	
	//addInput  - 
	// takes transaction name
	//		 address or name to pay from
	// 		 amount  (IN CTOSHI) ( 1/100000000 factoid)
	// this deletes a transaction that has been created but not submitted.
	public static String AddInput(String TransactionName,String AddressName,long FactoshiAmount) {
		String resp="";
		
		try {
			System.out.println(fctwalletURL + "/v1/factoid-add-input/?key=" + TransactionName + "&name=" + AddressName + "&amount=" + FactoshiAmount);
			resp=utils.executePost(fctwalletURL + "/v1/factoid-add-input/?key=" + TransactionName + "&name=" + AddressName + "&amount=" + FactoshiAmount,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // addInput
	
	
	
	
	
	//addOutput  - 
	// takes transaction name
	//		 address or name to pay from
	// 		 amount  (IN CTOSHI) ( 1/100000000 factoid)
	// this deletes a transaction that has been created but not submitted.
	public static String AddOutput(String TransactionName,String AddressName,long FactoshiAmount) {
		String resp="";
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-add-output/?key=" + TransactionName + "&name=" + AddressName + "&amount=" + FactoshiAmount,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput
	
	
	
	
	
	//addECOutput  - 
	// takes transaction name
	//		 address or name to pay from
	// 		 amount  (IN CTOSHI) ( 1/100000000 factoid)
	// this will create entry credits at the current exchange rate.
	// if you need to create a specific number of entry credits, work out the number of factoids to use in advance
	// this deletes a transaction that has been created but not submitted.
	public static String AddECOutput(String TransactionName,String AddressName,Integer Amount) {
		String resp="";
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-add-ecoutput/?key=" + TransactionName + "&name=" + AddressName + "&amount=" + Amount,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput


	
	
	
	//Delete Transaction  - 
	// takes transaction name
	// this deletes a transaction that has been created but not submitted.
	public static String DeleteTransaction(String AddressName) {
		String resp="";
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-delete-transaction/" + AddressName,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
	
	
	
	//GENERATE NEW FACTOID ADDRESS  - 
	// takes address name
	public static String GenerateFactoidAddress(String AddressName) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-generate-address/" + AddressName);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
	
	
	
	
	//GENERATE NEW ENTRY CREDIT ADDRESS  - 
	// takes address name
	public static String GenerateEntryCreditAddress(String AddressName) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-generate-ec-address/" + AddressName);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
	
	
	
	
	//GENERATE NEW ENTRY CREDIT ADDRESS FROM PRIVATE KEY  - 
	// takes address name and key to use.
	//  it is Human Readable because it wants your hex string, not the byte array
	public static String GenerateAddressFromHumanReadablePrivateKey(String AddressName, String PrivateKey) {
		String resp="";

		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-generate-address-from-human-readable-private-key/?name=" + AddressName + "&privateKey=" + PrivateKey);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
		
	
	
	//GENERATE NEW ENTRY CREDIT ADDRESS FROM  - 
	// takes address name
	public static String GenerateEntryCreditAddressFromHumanReadablePrivateKey(String AddressName, String PrivateKey) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-generate-ec-address-from-human-readable-private-key/?name=" + AddressName + "&privateKey=" + PrivateKey);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GenerateEntryCreditAddressFromHumanReadablePrivateKey
	
	
	
	
	//GetAddresses 
	// get ebhead by chainid
	public static String GetAddresses() {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-get-addresses/");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetChainHead
	

	//GetChainHead 
	// get ebhead by chainid
	public static String GetChainHead(String chainid) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(factomdURL + "/v1/chain-head/" + chainid);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetChainHead
	
	
	

	//GetDBlock 
	// Get the current directory block height	
	public static String GetDBlock(String keymr) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(factomdURL + "/v1/directory-block-by-keymr/" + keymr);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetDBlock
	
	
	
	
	//GetDBBlockHead  - 
	// Get the Key Merkle Root of the last completed directory block
	public static String GetDBlockHead() {
		String resp="";
		
		try {
			
			resp=utils.executeGet(factomdURL + "/v1/directory-block-head/");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetDBlockHead
	
	
	//GetDBlockHeight  - 
	// Get the current directory block height	
	public static String GetDBlockHeight() {
		String resp="";
		
		try {
			
			resp=utils.executeGet(factomdURL + "/v1/directory-block-height/");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetDBlockHeight
	
	
	
	//GetEBlock 
	// get eblock by merkle root
	public static String GetEBlock(String keymr) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(factomdURL + "/v1/entry-block-by-keymr/" + keymr);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetEBlock
	
	
	//GetEntry
	// get entry by hash
	public static String GetEntry(String hash) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(factomdURL + "/v1/entry-by-hash/" + hash);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetEntry
	
	

	//GetEntryCreditBalance 
	// get ebhead by chainid
	public static String GetEntryCreditBalance(String Account) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/entry-credit-balance/" + Account);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetChainHead
	


	//GetFactoidBalances 
	// get ebhead by chainid
	public static String GetFactoidBalance(String Account) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-balance/" + Account);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetChainHead
	
	
		
	//GetFee  - 
	// takes transaction name
	// returns amount (factoshi) that will be used in addfee.  so you know
	public static String GetFee(String TransactionName) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-get-fee/?key=" + TransactionName);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetFee
	
	//GetExchangeRate  - 
	// takes transaction name
	// returns amount (factoshi) that will be used in addfee.  so you know
	public static String GetExchangeRate() {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-get-fee/");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput
	
	


	//Get Transaction  - 
	// Retuens information about the current pending transactions
	//  These are transactions being constructed, not ones that have been
	//  signed and submitted

	public static String GetTransactions(String TransactionName) {
		String resp="";
		String postData="";

		try {
			resp=utils.executePost(fctwalletURL + "/v1/factoid-get-transactions/",postData );
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}	
	

	//Get Transaction  - 
	// Retuens information about the current pending transactions
	//  These are transactions being constructed, not ones that have been
	//  signed and submitted
	// if format="JSON, return format as JSON
	public static String GetProcessedTransactions(String TransactionName,String format) {
		String resp="";
		String postData="";
		
		if (TransactionName.equals("") || TransactionName.toUpperCase().equals("ALL")){
			postData="cmd=all";
		} else	{
			postData="address=" + TransactionName;
		}
		
		try {
			if (!format.equals("JSON")) {
			resp=utils.executePost(fctwalletURL + "/v1/factoid-get-processed-transactions/?" + postData,"" );		
			}
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}	
	
	
	
	//new Transaction  - 
	// takes transaction name
	// this creates a new temporary transaction for while it is being built.
	public static String NewTransaction(String AddressName) {
		String resp="";
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-new-transaction/" + AddressName,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
	
	//Sign Transaction  - 
	// takes transaction name
	// signs transaction with private key.
	public static String SignTransaction(String TransactionName) {
		String resp="";
	
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-sign-transaction/" + TransactionName,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		
		return resp;
	}
		
	//Submit Transaction  - 
	// takes transaction name
	// this submits the transaction to factom.
	public static String SubmitTransaction(String TransactionName) {
		String resp="";
	
		try {

			resp=utils.executePost(fctwalletURL + "/v1/factoid-submit/{\"Transaction\":\"" + TransactionName + "\"}","");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
		
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//COMPOSITE 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	//BuyEntryCreditsFullTransaction  - 
	// takes input address/name
	//       output address/name
	// 		 amount  IN CTOIDS
	// This is not base functionality.  It is a composite call that combines the 7 calls required for 
	//   sending factoid into one call for ease of use.  In that vein, this function handled the factoshi conversion
	public static String BuyEntryCreditsFullTransaction(String FromAddress,String ToAddress,Float Amount) {
		String resp="";
		String transactionName="Tran001";
		Integer factoshi=0;
		
	
		factoshi=(int) (Amount * 100000000);  // convert to factoshi (1 factoshi=1/100000000 factoid)
		
		try {
			resp=DeleteTransaction(transactionName);
			resp=NewTransaction(transactionName);
			resp=AddInput(transactionName,FromAddress,factoshi);
			resp=AddECOutput(transactionName,ToAddress,factoshi);
			resp=GetFee(transactionName); //getfee without a transactionname gives entry credit exchange rate.  just a warning.
			resp=AddFee(transactionName,FromAddress);
			resp=SignTransaction(transactionName);
			resp=SubmitTransaction(transactionName);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			
			System.out.println(resp);
			
		}
		return resp;
	} // buyentrycreditsfulltransaction
	
	
	
	//GetFirstEntry
	// get entry by hash
	public static String GetFirstEntry(String chainid) {
		return "NOT YET IMPLEMENTED. SHOULD BE, THIS IS A PLACE HOLDER. TREAT AS BUG";
	
	
	
	} // end of GetFirstEntry
	
	
	
	//SendFactoidsFullTransaction  - 
	// takes input address/name
	//       output address/name
	// 		 amount  IN FACTOIDS
	// This is not base functionality.  It is a composite call that combines the 7 calls required for 
	//   sending factoid into one call for ease of use.  In that vein, this function handled the factoshi conversion
	public static String SendFactoidsFullTransaction(String FromAddress,String ToAddress,Float Amount) {
		String resp="";
		String transactionName="Tran001";
		long factoshi=0;
		
	    factoshi=Amount.intValue();
		factoshi= factoshi * 100000000;  // convert to factoshi (1 factoshi=1/100000000 factoid)
		
		try {
			resp=DeleteTransaction(transactionName);
			//System.out.println(resp);	
			resp=NewTransaction(transactionName);
			//System.out.println(resp);	
			resp=AddInput(transactionName,FromAddress,factoshi);
			//System.out.println(resp);	
			resp=AddOutput(transactionName,ToAddress,factoshi);
			//System.out.println(resp);	
			resp=GetFee(transactionName); //getfee without a transactionname gives entry credit exchange rate.  just a warning.
			//System.out.println(resp);	
			resp=AddFee(transactionName,FromAddress);
			//System.out.println(resp);	
			resp=SignTransaction(transactionName);
			//System.out.println(resp);	
			resp=SubmitTransaction(transactionName);
			//System.out.println(resp);	
			} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
		
			//System.out.println(resp);
			
		}
		return resp;
	} // addInput
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// composite chain commit
	// accepts nme: paying ec address
	// extids external ids as array of strings
	//  data - data to be to blockchain entry
	
	public static String ComposeChainCommit(String name, String[] extids,String data) {
		String resp="";		
		Chain c=new Chain();	
		Entry e=new  Entry();	
		e.setChainID(extids);
		e.setExtIDs(extids);
		e.Content =data.getBytes();
		String temp="";
		byte[] postData=new byte[0];
		// put external IDs in entry


		//put content into entry		
	


		c.setFirstEntry(e);


		postData=utils.appendByteToArray(postData, (byte)0);						//version
		postData=utils.appendByteArrays(postData,utils. MilliTime());		//millitime
		postData=utils.appendByteArrays(postData, utils.sha256Bytes(utils.sha256Bytes(c.ChainID)));//chainid hash hash twice
		postData=utils.appendByteArrays(postData, utils.sha256Bytes(utils.sha256Bytes(utils.appendByteArrays(utils.sha256Bytes(e.toBytes()) , c.ChainID))));	//commit weld
		postData=utils.appendByteArrays(postData, e.toBytes());		//hash of first entry
		postData=utils.appendByteToArray(postData, (byte)11);						//version
		
		//rI could use a json marshal here, but I want the message format to be obvious.
		temp=utils.bytesToHex(postData);
		temp="{\"Message\":\"" + temp + "\"}";
		
		resp=utils.executePost(fctwalletURL + "/v1/commit-chain/" + name,temp);
		
		if ( resp.equals("200")){
			System.out.println("waiting 10 seconds before calling reveal");
			try{
			Thread.sleep(10000);
			} catch (Exception ex) {
				
			}
			resp=RevealChainOrEntry(e,"Chain");
		} else {
			
		}
		if (resp.equals("200")) {
			return utils.bytesToHex(e.ChainID) ;
		} else {	
			return resp;		
		}

	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// composite entry commit
	// accepts name: paying ec address
	// extids external ids as array of strings
	//  data - data to be to blockchain entry
		
  public static String ComposeEntryCommit(String name,String[] extids,String data)  {
	  String resp="";

		String temp="";
		byte[] postData=new byte[0];

		Entry e=new Entry();
		
		// change this.  it is client.java specific and looking for -c and -e
		e.setChainID(extids);
		e.setExtIDs(extids);
		e.Content = data.getBytes();
		postData=utils.appendByteToArray(postData, (byte)0);			//version
		postData=utils.appendByteArrays(postData, utils.MilliTime());	//millitime
		if (e.entryHash == null) {
			e.setEntryHash();
		}
		postData=utils.appendByteArrays(postData, e.entryHash);	//hash of entry
		postData=utils.appendByteToArray(postData, (byte)1);	//entry cost (entry content divided by 1k
		
		//rI could use a json marshal here, but I want the message format to be obvious.
		temp=utils.bytesToHex(postData);
		temp="{\"CommitEntryMsg\":\"" + temp + "\"}";
		resp=utils.executePost(fctwalletURL + "/v1/commit-entry/" + name,temp);

		if ( resp.equals("200")){
			System.out.println("waiting 10 seconds before calling reveal");
			try{
			Thread.sleep(10000);
			} catch (Exception ex) {
				
			}
			resp=RevealChainOrEntry(e,"Entry");
		} else {
			
		}		
		return resp;    
	   
  }

  
  
  
  //////////////////////////////////////////////////////////////////////////////////////////
  //  The reveal call is the same format for a chain or entry.
  //  the RevealType call should be "Chain" or "Entry"
  public static String RevealChainOrEntry(Entry e,String RevealType) {
		String resp="";
		int i;
		int extidlength=0;
		String temp="";
		byte[] postData=new byte[0];
		// put external IDs in entry


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
		postData=utils.appendByteArrays(postData, utils.sha256Bytes(e.Content));	//extid size (2 bytes)

		//rI could use a json marshal here, but I want the message format to be obvious.		
		temp=utils.bytesToHex(postData);
		temp="{\"Entry\":\"" + temp + "\"}";
		
		if (RevealType=="Chain"){
		resp=utils.executePost(factomdURL + "/v1/reveal-entry/",temp);
		} else if (RevealType=="Entry"){
			resp=utils.executePost(factomdURL + "/v1/reveal-entry/",temp);
				
		}
		System.out.println("resp=" + resp);
		
		
		return resp;    
	   
  }
////////////////////////////////////////////////////////////////////////////////////////////////
	//UTILITIES
////////////////////////////////////////////////////////////////////////////////////////////////



}
