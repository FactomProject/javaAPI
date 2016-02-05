/**
 * Copyright 2016 Factom Foundation
 * Use of this source code is governed by the MIT
 * license that can be found in the LICENSE file.
 */
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
	
	
	public static String fctwalletURL="http://localhost:8089";
	public static String factomdURL="http://localhost:8088";
	

	
	/**AddFee  - 
	 * Adds fee amount to TransactionName from the provided address
	* @param TransactionName 
	* String - Transaction name only exists while building a Factoid send or entry credit conversion
	* 	     - Not useful persistent information
	* @param AddressName
	* Address and or address name to take the fees from
	* @return
	* JSON document as string.  <p>
	* {"Response":"Added             0.000012 to [AddressName]","Success":true}
	* <p>
	* Example:<p>
	* responseText=AddFee("TranCode1","AddressLabel"); <p>
	* responseText=AddFee("TranCode1","FAxxxxxxxxxxxxxxxxxxxxxx");
	**/
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
	
	
	
	/**addInput  - 
	 * Adds input value and source for Entry Credit purchase or factoid transaction
	* @param TransactionName 
	* String - Transaction name only exists while building a Factoid send or entry credit conversion
	* 	     - Not useful persistent information
	* @param AddressName
	* Address and or address name to take the fees from
	* @param FactoshiAmount
	* amounts are denominated in Factoshi (1/100000000 factoid)
	* @return
	* JSON document as string.  <p>
	* {"Response":"Success adding Input","Success":true}
	* <p>
	* Example:<p>
	* responseText=AddInput("TranCode1","sendingAddress",50000); <p>
	* responseText=AddInput("TranCode1","FAxxxxxxxxxxxxxxxxxxxxxx",50000);
	**/	

	public static String AddInput(String TransactionName,String AddressName,long FactoshiAmount) {
		String resp="";
		if (FactoshiAmount < 0) {
			return "{\"Response\":\"Negative Values Not Allowed.\",\"Success\":false}";
		}

		try {
			resp=utils.executePost(fctwalletURL + "/v1/factoid-add-input/?key=" + TransactionName + "&name=" + AddressName + "&amount=" + FactoshiAmount,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // addInput
	
	
	
	
	
	/**addOutput  - 
	 * Adds output value and destination for Entry Credit purchase or factoid transaction
	* @param TransactionName 
	* String - Transaction name only exists while building a Factoid send or entry credit conversion
	* 	     - Not useful persistent information
	* @param AddressName
	* Address and or address name to send factoid  (Not entry credits) to
	* total of outputs ave to equal inputs
	* @param FactoshiAmount
	* amounts are denominated in Factoshi (1/100000000 factoid)
	* @return
	* JSON document as string.  <p>
	* {"Response":"Success adding Out","Success":true}
	* <p>
	* Example:<p>
	* responseText=AddOutput("TranCode1","sendingAddress",50000); <p>
	* responseText=AddOutput("TranCode1","FAxxxxxxxxxxxxxxxxxxxxxx",50000);
	**/	
 
	public static String AddOutput(String TransactionName,String AddressName,long FactoshiAmount) {
		String resp="";
		if (FactoshiAmount < 0) {
			return "{\"Response\":\"Negative Values Not Allowed.\",\"Success\":false}";
		}
		if (AddressName.length() > 32) {
			return "{\"Response\":\"Addresses cannot be over 32 bytes\",\"Success\":false}";			
		}
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-add-output/?key=" + TransactionName + "&name=" + AddressName + "&amount=" + FactoshiAmount,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput
	
	
	
	
	
	
	/**addECOutput  - 
	 * Adds output value and destination for an Entry Credit (not factoid) transaction
	 * this will create entry credits at the current exchange rate.
	 * if you need to create a specific number of entry credits, work out the number of 
	 * factoshi to use in advance
	* @param TransactionName 
	* String - Transaction name only exists while building a Factoid send or entry credit conversion
	* 	     - Not useful persistent information
	* @param AddressName
	* Address and or address name to send entry credits  (Not factoid) to
	* total of outputs ave to equal inputs
	* @param FactoshiAmount
	* amounts are denominated in Factoshi (1/100000000 factoid)
	* @return
	* JSON document as string.  <p>
	* {"Response":"Success adding EC Out","Success":true}
	* <p>
	* Example:<p>
	* responseText=AddECOutput("TranCode1","sendingAddress",50000); <p>
	* responseText=AddECOutput("TranCode1","FAxxxxxxxxxxxxxxxxxxxxxx",50000);
	**/	
 
	public static String AddECOutput(String TransactionName,String AddressName,long FactoshiAmount) {
		String resp="";
		if (FactoshiAmount < 0) {
			return "{\"Response\":\"Negative Values Not Allowed.\",\"Success\":false}";
		}
		if (AddressName.length() > 32) {
			return "{\"Response\":\"Addresses cannot be over 32 bytes\",\"Success\":false}";			
		}
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-add-ecoutput/?key=" + TransactionName + "&name=" + AddressName + "&amount=" + FactoshiAmount,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput


	
	
	/**DeleteTransaction  - 
	 * clears the transaction being built under that name
	* @param TransactionName 
	* String - Transaction name only exists while building a Factoid send or entry credit conversion
	* 	     - Not useful persistent information
	* @return
	* JSON document as string.  <p>
	* {"Response":"Success deleting transaction","Success":true}
	* <p>
	* Example:<p>
	* responseText=DeleteTransaction("TranCode1"); 
	**/	
 
	public static String DeleteTransaction(String TransactionName) {
		String resp="";
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-delete-transaction/" + TransactionName,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
	
	
	
	/**GenerateFactoidAddress  - 
	 * create new factoid address in the fctwallet.  this takes a label that can be used in place of the long ugly address.  
	* @param AddressName 
	* String - This is the name that will be used in the local fctwallet.  This name can be used locally in the place of the actual address.
	 * Only known locally
	* @return
	* JSON document as string.  <p>
	* {"Response":"FA2xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx","Success":true}
	* <p>
	* Example:<p>
	* responseText=GenerateFactoidAddress("PrettyName1"); 
	**/	
 
	public static String GenerateFactoidAddress(String AddressName) {
		String resp="";
		if (AddressName.length() > 32) {
			return "{\"Response\":\"Addresses cannot be over 32 bytes\",\"Success\":false}";			
		}
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-generate-address/" + AddressName);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
	
	
	
	
	/**GenerateEntryCreditAddress  - 
	 * create new entry credit address in the fctwallet.  this takes a label that can be used in place of the long ugly address.  
	* @param AddressName 
	* String - This is the name that will be used in the local fctwallet.  This name can be used locally in the place of the actual address.
	 * Only known locally
	* @return
	* JSON document as string.  <p>
	* {"Response":"ECxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx","Success":true}
	* <p>
	* Example:<p>
	* responseText=GenerateEntryCreditAddress("PrettyECName1"); 
	**/	
	public static String GenerateEntryCreditAddress(String AddressName) {
		if (AddressName.length() > 32) {
			return "{\"Response\":\"Addresses cannot be over 32 bytes\",\"Success\":false}";			
		}
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
	
	
	
	/**GenerateAddressFromHumanReadablePrivateKey  - 
	 * create new factoid address using the supplied private key.  
	* @param AddressName 
	* String - This is the name that will be used in the local fctwallet.  This name can be used locally in the place of the actual address.
	 * Only known locally
	* @param PrivateKey
	//  it is Human Readable because it wants your hex string, not the byte array
	* @return
	* JSON document as string.  <p>
	* {"Response":"FAxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx","Success":true}
	* <p>
	* Example:<p>
	* responseText=GenerateEntryCreditAddress("PrettyName1","FSxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); 
	**/		

	public static String GenerateAddressFromHumanReadablePrivateKey(String AddressName, String PrivateKey) {
		String resp="";
		if (AddressName.length() > 32) {
			return "{\"Response\":\"Addresses cannot be over 32 bytes\",\"Success\":false}";			
		}
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-generate-address-from-human-readable-private-key/?name=" + AddressName + "&privateKey=" + PrivateKey);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
		
	
	
	/**GenerateEntryCreditAddressFromHumanReadablePrivateKey  - 
	 * create new Enry Credit address using the supplied private key.  
	* @param AddressName 
	* String - This is the name that will be used in the local fctwallet.  This name can be used locally in the place of the actual address.
	* Only known locally
	* @param PrivateKey
	//  it is Human Readable because it wants your hex string, not the byte array
	* @return
	* JSON document as string.  <p>
	* {"Response":"ECxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx","Success":true}
	* <p>
	* Example:<p>
	* responseText=GenerateEntryCreditAddressFromHumanReadablePrivateKey("PrettyName1","FSxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); 
	**/
	public static String GenerateEntryCreditAddressFromHumanReadablePrivateKey(String AddressName, String PrivateKey) {
		String resp="";
		if (AddressName.length() > 32) {
			return "{\"Response\":\"Addresses cannot be over 32 bytes\",\"Success\":false}";			
		}
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-generate-ec-address-from-human-readable-private-key/?name=" + AddressName + "&privateKey=" + PrivateKey);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GenerateEntryCreditAddressFromHumanReadablePrivateKey
	
	
	
	
	
	/**GetAddresses  - 
	 * Gets list of addresses and balances in local fctwallet
	* @return
	* JSON document as string.  <p>
	* the text inside is formatted for readability in command terminal
	* {"Response":"\n  Factoid Addresses\n\n     PrettyName    FAxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx             0.0\n               sand    FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q           254.52499553\n\n  Entry Credit Addresses\n\n          zeros    EC2DKSYyRcNWf7RS963VFYgMExoHRYLHVeCfQ9PGPmNzwrcmgm2r       35474642\n","Success":true}
	* <p>
	* Example:<p>
	* responseText=GetAddresses(); 
	**/	
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
	} // end of GetAddresses
	

	
	
	/**GetChainHead  - 
	 * Get the keymr of the newest entry block in the chain
	 * The entry block will have keymr of the entry before so you can link back to the beginning of the chain  
	* @param chainID 
	* String - This is the name that will be used in the local fctwallet.  This name can be used locally in the place of the actual address.
	* @return
	* JSON document as string.  <p>
	* {"ChainHead":"6309f8486033aff752e45694b9ae8c98ef675b153040bcf8635add609f1e4a8c"}
	* <p>
	* Example:<p>
	* responseText=GetChainHead("11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856"); 
	**/	

	public static String GetChainHead(String chainID) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(factomdURL + "/v1/chain-head/" + chainID);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetChainHead
	
	
	
	/**GetDBlock  - 
	 * Get the Directory Block contents by merkle root  
	* @param keymr 
	* String - This is the name that will be used in the local fctwallet.  This name can be used locally in the place of the actual address.
	* @return
	* JSON document as string.  <p>
	* {"Header":{"PrevBlockKeyMR":"25919d41349c9e14d89a0866ac734ba84fb1a22bd284e9f715af34961d07b95e",
	* 								"SequenceNumber":4594,
	* 								"Timestamp":1454522100},
	* 								"EntryBlockList":[{"ChainID":"000000000000000000000000000000000000000000000000000000000000000a",
	* 													"KeyMR":"dff8587d06e7d41ed7851c225e3f2158bc502ea8a8acc93cf6c23038a3d05957"},
	* 												{"ChainID":"000000000000000000000000000000000000000000000000000000000000000c",
	* 													"KeyMR":"51592969bd7673c21f9f79af85a271265dd1fd3cc45f2ded1970c9893103c3eb"},
	* 												{"ChainID":"000000000000000000000000000000000000000000000000000000000000000f",
	* 													"KeyMR":"5a03e7a4def8d22bf508396ee8f1780aa25de39d88aa0819b3122a8837942f28"}]}
	* <p>
	* Example:<p>
	* responseText=GetDBlock("9204d12f66604a83942a308781098e8405df7551eb4173c5b8817ad53c74d999"); 
	**/	

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
	
	
	
	/**GetDBlock  - 
	 * Get the keymr (key merkle root) of the last published directory block  
	* @return
	* JSON document as string.  <p>
	* {"KeyMR":"d04b79feb87a3320ac0ad2e40d1e46037372a3276b4904c2961053dd772a1ad8"}
	* <p>
	* Example:<p>
	* responseText=GetDBlock("9204d12f66604a83942a308781098e8405df7551eb4173c5b8817ad53c74d999"); 
	**/		
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
	
	/**GetDBlockHeight  - 
	 * Get the current directory block height 
	* @return
	* JSON document as string.  <p>
	* {"Height":4712}
	* <p>
	* Example:<p>
	* responseText=GetDBlockHeight(); 
	**/	
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
	
	
	/**GetEBlock  - 
	 * get Entry Block by merkle root
	* @param keymr 
	*   Merkle root. probably found it in the directory block
	* @return
	* JSON document as string.  <p>
	* {"Header":{"BlockSequenceNumber":7,
	* 			"ChainID":"11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856",
	* 			"PrevKeyMR":"6bb7518e2a26c63cc18c7f40484f60851c8f1c433c039744c9542096c4bdc527",
	* 			"Timestamp":1454520420},
	* 			"EntryList":[{"EntryHash":"75668b338e44cb45998594c9cf5f36b52929d85bb616a4052b043da30319956d",
	* 						"Timestamp":1454520900}]}
	* <p>
	* Example:<p>
	* responseText=GetEBlock("6309f8486033aff752e45694b9ae8c98ef675b153040bcf8635add609f1e4a8c"); 
	**/		
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
	
	
	/**GetEntry  - 
	 * get Entry entry with entry hash
	* @param hash 
	*   This is the entry hash.  see the entry class or you got it from an entry block
	*   
	* @return
	* JSON document as string.  <p>
	* {"ChainID":"11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856",
	* "Content":"446966666572656e7420746869732074696d6520746f6f",
	* "ExtIDs":["313233","323334"]}
	* <p>
	* Example:<p>
	* responseText=GetEntry("75668b338e44cb45998594c9cf5f36b52929d85bb616a4052b043da30319956d"); 
	**/
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
	
	
	/**GetEntryCreditBalance  - 
	 * returns the number of entry credits in the entry credit address
	* @param Address 
	*   This is the entry hash.  see the entry class or you got it from an entry block
	*   
	* @return
	* JSON document as string.  <p>
	* {"Response":"3547","Success":true}
	* <p>
	* Example:<p>
	* responseText=GetEntryCreditBalance("zeros"); 
	* responseText=GetEntryCreditBalance("EC2DKSYyRcNWf7RS963VFYgMExoHRYLHVeCfQ9PGPmNzwrcmgm2r"); 
	**/

	public static String GetEntryCreditBalance(String Address) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/entry-credit-balance/" + Address);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetEntryCreditBalance
	


	/**GetFactoidBalance  - 
	 * returns the number of entry credits in the entry credit address
	* @param Address 
	*   This is the entry hash.  see the entry class or you got it from an entry block
	*   Response is denominated in factoshi (1/100000000 factoid)
	* @return
	* JSON document as string.  <p>
	* {"Response":"25352498353","Success":true}
	* <p>
	* Example:<p>
	* responseText=GetFactoidBalance("sand"); 
	* responseText=GetFactoidBalance("FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q"); 
	**/
	public static String GetFactoidBalance(String Address) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-balance/" + Address);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetFactoidBalance
	
	
		
	/**GetFee  - 
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
	* responseText=GetFee("testtran"); 
	**/
	public static String GetFee(String TransactionName) {
		String resp="";
		
		try {
			if (TransactionName.equals("")) {
				resp=utils.executeGet(fctwalletURL + "/v1/factoid-get-fee");				
			} else {
				resp=utils.executeGet(fctwalletURL + "/v1/factoid-get-fee/?key=" + TransactionName);				
			}

		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of GetFee
	
	
	
	/**{"Response":"0.000001","Success":true}  - 
	 * returns the exchange rate of factoids to entry credits.
	* @return
	* JSON document as string.  <p>
	* {"Response":"0.009","Success":true}
	* <p>
	* Example:<p>
	* responseText={"Response":"0.000001","Success":true}("testtran"); 
	**/
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
	
	
	
	
	/**GetFirstEntry  - 
	 * returns first entry in provided chain
	* @param chainid chain ID
	*   
	* @return
	* JSON document as string.  <p>
	* {"ChainID":"11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856",
	* 	"Content":"446966666572656e7420746869732074696d6520746f6f",
	* 	"ExtIDs":["313233","323334"]}
	* <p>
	* Example:<p>
	* responseText=GetFirstEntry("11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856"); 
	**/	
	public static String GetFirstEntry(String chainid) {
		String resp="";
		String eblockmr=GetChainHead(chainid);
		// not using json to be clear.  just stripping extra text from responses
		
		eblockmr=eblockmr.replace("{\"ChainHead\":\"","");
		eblockmr=eblockmr.replace("\"}","");
		
		String eblock=GetEBlock(eblockmr);

		
		String EntryHash=eblock.substring(eblock.indexOf("EntryHash\":\"") + 12);
		EntryHash=EntryHash.substring(0,EntryHash.indexOf("\""));
		
		
		String entry=GetEntry(EntryHash);
		resp= entry;	

	
	
		return resp;
	} // end of GetFirstEntry
	
	

	/**GetProperties  - 
	 * returns first entry in provided chain
	* @return
	* JSON document as string.  <p>
	* {"Response":"Protocol Version:   0.1.5\nfactomd Version:    0.3.4\nfctwallet Version:  0.1.4\n","Success":true}
	* <p>
	* Example:<p>
	* responseText=GetProperties() ; 
	**/	
	public static String GetProperties() {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/properties/");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput
	
	/**GetTransactions  - 
	 * information about the current pending transactions
	 * These are transactions being constructed, not ones that have been
	*  signed and submitted
	*  Formatted for terminal
	* @return
	* JSON document as string.  <p>
	* {"Response":"\ntesttran\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000:  Fee Due: 0.000002   Currently will pay: 1.0\n\nTransaction (size 143):\n                 Version: 0000000000000002\n          Transaction ID: 6403186927b1a4adde036984f81d2a384d857cff21394f385e9cd80031619434\n          MilliTimestamp: 00000152a8ca65e7 Wed, 03 Feb 2016 20:19:42 +0000\n                # Inputs: 0001\n               # Outputs: 0000\n   # EntryCredit Outputs: 0000\n      input:       1.0        FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n\n","Success":true}
	* <p>
	* Example:<p>
	* responseText=GetTransactions(); 
	**/	
	//Get Transaction  - 
	// Retuens information about the current pending transactions
	//  These are transactions being constructed, not ones that have been
	//  signed and submitted

	public static String GetTransactions() {
		String resp="";

		try {
			resp=utils.executeGet(fctwalletURL + "/v1/factoid-get-transactions/");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}	
	

	/**GetProcessedTransactions  - 
	 * returns first entry in provided chain
	* @param Address 
	*   Address, address label, or 'all'
	* @return
	* JSON document as string.  Formatted for terminal<p>
	* {"Response":"
	* Skipped blocks 0-1579\n\nBlock Height 1580 total transactions 2\n	* Transaction 11 Block Height 1580\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 2c32754969c8600cff33f893b9a6486063dfcf36715c5acade992f3ef467d203\n          MilliTimestamp: 000001528e1002f8 Fri, 29 Jan 2016 15:46:00 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       2.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       2.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: dd43900bf5b288f8ad1ab1be74c1d8cdd85ec833d8ce96ef430a6e6204b19bee\n            301e9c4f4f91c3acd5bc8254c3e0144209ea6bac60a1966f8f4c287fdd9de102\n\n\nSkipped blocks 1581-1584\n\nBlock Height 1585 total transactions 2\nTransaction 12 Block Height 1585\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 21d2940692001b99fdf8ed0bbdbc17d3e60e032edeea23dd6b3e2122a9620126\n          MilliTimestamp: 000001528e13eb24 Fri, 29 Jan 2016 15:50:16 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       2.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       2.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 745b691ae4fbf265c79ab5ffbde664162820b14f9917a2160a3113779bf8ce5a\n            2fa5bbd86764246e1bc158addb735e95d4bdabbe87d79fc6c51bc38e8f99ae08\n\n\nSkipped blocks 1586-1689\n\nBlock Height 1690 total transactions 2\nTransaction 18 Block Height 1690\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 643e7c6d5373ca8c59d8b9f67e39bf458a2ee6e8efc29e60831c63039cfa0246\n          MilliTimestamp: 000001528e6a7aa9 Fri, 29 Jan 2016 17:24:48 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 91ffbd27dfb029f6589783eb74a40d9f05204f1a53e88aa6ac96d99818c07806\n            69e664c7f8afa4f21e6a5caa5c1ca4e770223dbd8cb3ed5586369ad180807e00\n\n\nBlock Height 1691 total transactions 2\nTransaction 19 Block Height 1691\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 31f1581fcc321d708a1b3cce9558a355ca3bf9601beed0003876ce69d868db78\n          MilliTimestamp: 000001528e6afa3c Fri, 29 Jan 2016 17:25:21 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: f271f61256d6c32adf557a22f80b9f4d011c8bbe72ed6f7b57cfaad20aff7fbc\n            1900075d8cbb25b0b65ad968c19065f7bf133ff6f2412b38d734debc849d720f\n\n\nSkipped blocks 1692-1695\n\nBlock Height 1696 total transactions 2\nTransaction 20 Block Height 1696\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 3b6b503d0039c2380deec5116218cf13297595817a87fcd01efe8cc8b97c86f8\n          MilliTimestamp: 000001528e6fbc95 Fri, 29 Jan 2016 17:30:33 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: fcc7e7f02e5a4cb39467005f23c7502d4da371f9fe8ed33c5015f3e9b380335a\n            f46259ff67388cf2465051ad7d5a35a4be8d685f80cdc7d7a54b4594a40b660f\n\n\nSkipped blocks 1697-1714\n\nBlock Height 1715 total transactions 2\nTransaction 21 Block Height 1715\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: ca349c64d0d123a6bfc5b4f79b7f4cab5c2a3941c3ad65f12f79edbaafa9a858\n          MilliTimestamp: 000001528e7ecfd4 Fri, 29 Jan 2016 17:47:01 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 74690b74d6a0844b6c1ae6b9ce7c10f2023ead8f6eed169c367297d91e92656c\n            b0bfacfad63b6878277ce524c1d2b4f54d7ccbb1d845fade5a6e4235904d5e01\n\n\nSkipped blocks 1716-4403\n\nBlock Height 4404 total transactions 2\nTransaction 22 Block Height 4404\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 09e47feb9a7139883bda2d63decafdaf51d7605467dc51077e65a967f051dea9\n          MilliTimestamp: 00000152a7a9a776 Wed, 03 Feb 2016 15:04:19 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 65e8dc301e1b398caddeeea29b80ebb40c0d6d7986f33a58a25f3f3280b8cdc5\n            b75f6ab3081453dd77efa9411bdaf02509afae4c9751070653af03b222a96f05\n\n\nSkipped blocks 4405-4499\n\nBlock Height 4500 total transactions 2\nTransaction 23 Block Height 4500\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 2084b94b2b06f2e58b71039be4da031465bb331118f17e7a882033b6869825ff\n          MilliTimestamp: 00000152a7f9893f Wed, 03 Feb 2016 16:31:34 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 642e463142fb35b23077cd7ef0b3f3e743d2b0d03aa5e60ff56ee336e52134bd\n            e261b8745c69c6e75845a8192db95a9f173cdf5d61aeec2bf04b301b28f14e0f\n\n\nSkipped blocks 4501-4710\n\nBlock Height 4711 total transactions 2\nTransaction 24 Block Height 4711\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: d885729815becbc026efbeb79f89be6f8456ffefbc140bc274f583fe4a589479\n          MilliTimestamp: 00000152a8ab9b62 Wed, 03 Feb 2016 19:46:04 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 4fa66676d7dda385cbf3274ccbb9db3023f0b6ad54827a69ce9c5b46543fcedd\n            786597d25fe01e4493c475ce0e361f882886cf410bdf75592e124ac8529a9008\n\n\nSkipped blocks 4712-4753\n\nBlock Height 4754 total transactions 2\nTransaction 25 Block Height 4754\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: 4413b1b908e91a79ae45f90dce416846d6555603a47ff761117550122be1523a\n          MilliTimestamp: 00000152a8cf7e97 Wed, 03 Feb 2016 20:25:16 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 90107c006aa190715415fc83313247a3ce443bef1f1a27225e017b4aadf6996f\n            809ecc5a59810b676016776946e47b21c976702a5b6300ddee67d4f3f629aa04\n\n\nSkipped blocks 4755-4760\n\nBlock Height 4761 total transactions 2\nTransaction 26 Block Height 4761\nTransaction (size 179):\n                 Version: 0000000000000002\n          Transaction ID: dd8a27104a7dda6381de976149a8b64920e04b437bf54bfdf202d1ebccb4483c\n          MilliTimestamp: 00000152a8d4e2a1 Wed, 03 Feb 2016 20:31:09 +0000\n                # Inputs: 0001\n               # Outputs: 0001\n   # EntryCredit Outputs: 0000\n      input:       1.000012   FA2jK2HcLnRdS94dEcU27rF3meoJfpUcZPSinpb7AwQvPRY6RL1Q\n     output:       1.0        FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX\n RCD 1: 01  718b5edd2914acc2e4677f336c1a32736e5e9bde13663e6413894f57ec272e28\n signature: 1419debfbdbe9a787664a4df62fced84180bfe6d011e2e80ba6561a3e848a2de\n            86bf2cce0f382f4b60283fac66485d675199da159c506f2b27b35cbd11e72900\n\n\nSkipped blocks 4762-4779\n\n",
	* "Success":true}

	* <p>
	* Example:<p>
	* responseText=GetProcessedTransactions("sand2"); 
	* responseText=GetProcessedTransactions("FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX"); 
	**/	
	public static String GetProcessedTransactions(String Address) {
		String resp="";
		String postData="";
		
		if (Address.equals("") || Address.toUpperCase().equals("ALL")){
			postData="cmd=all";
		} else	{
			postData="address=" + Address;
		}
		
		try {

			resp=utils.executePost(fctwalletURL + "/v1/factoid-get-processed-transactions/?" + postData,"" );		

		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}	
	
	
	/**NewTransaction  - 
	 * this creates a new temporary transaction for while it is being built.
	* @param TransactionName temporary name assigned by user for this transaction
	* @return
	* JSON document as string.  Formatted for terminal<p>
	* {"Response":"Success building a transaction","Success":true}
	* <p>
	* Example:<p>
	* responseText=NewTransaction("testtran"); 
	**/	
	public static String NewTransaction(String TransactionName) {
		String resp="";
		
		try {
			
			resp=utils.executePost(fctwalletURL + "/v1/factoid-new-transaction/" + TransactionName,"");
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	}
	
	
	/**SignTransaction  - 
	 * Signs transaction with private key in fctwallet
	* @param TransactionName  temporary name assigned by user for this transaction
	* @return
	* JSON document as string.  Formatted for terminal<p>
	* {"Response":"Success signing transaction","Success":true}
	* <p>
	* Example:<p>
	* responseText=SignTransaction("testtran"); 
	**/	

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
		
	/**SubmitTransaction  - 
	 * Submits transaction to Factom
	* @param TransactionName  temporary name assigned by user for this transaction
	* @return
	* JSON document as string.  Formatted for terminal<p>
	* {"Response":"Success Submitting transaction","Success":true}
	* or
	* error message as "Response"
	* <p>
	* Example:<p>
	* responseText=SubmitTransaction("testtran"); 
	**/	
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
	
	
	/**verifyAddress  - 
	 * Determines whether th eentered address is valid and what type it is.  This is by format, not existence
	* @param Address address to be validated
	* @return
	* JSON document as string.  Formatted for terminal<p>
	* {"Response":"Factoid - Public","Success":true}
	* <p>
	* Example:<p>
	* responseText=verifyAddress("FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX"); 
	**/	
	public static String verifyAddress(String Address) {
		String resp="";
		
		try {
			
			resp=utils.executeGet(fctwalletURL + "/v1/verify-address-type/?address=" + Address);
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			resp="Error";
		}
		return resp;
	} // end of addOutput
	
		
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//COMPOSITE 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**BuyEntryCreditsFullTransaction  - 
	 *This is not base functionality.  It is a composite call that combines the 7 calls required for 
	 * sending factoid into one call for ease of use.  In that vein, this function handled the factoshi conversion
	* @param FromAddress - address sending factoid.  address or local label 
	* @param ToAddress - Address receiving factoid
	* @param Amount  - As Factoid, not entry credits.  if you need to work out factoid cost for a specific
	* number of entry credits, use getExchangeRate 
	* @return
	* JSON document as string.  Formatted for terminal<p>
	* {"Response":"Success Submitting transaction","Success":true}
	* or error message as Response
	* <p>
	* Example:<p>
	* responseText=BuyEntryCreditsFullTransaction("FA35Ky2jEMwhFqXhvARBKGryUzRmxGoXt8RM2fDsVXws2W2tXm5J","EC2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX",10.5); 
	**/	

	public static String BuyEntryCreditsFullTransaction(String FromAddress,String ToAddress,Double Amount) {
		if (Amount < 0) {
			return "{\"Response\":\"Negative Values Not Allowed.\",\"Success\":false}";
		}
		String resp="";
		String transactionName="Tran001";
		Double dTemp=0.0;
		long factoshi=0;
		
		dTemp=(Amount * 100000000);
		factoshi= dTemp.longValue();  // convert to factoshi (1 factoshi=1/100000000 factoid)
		
	

		
		try {
			resp=DeleteTransaction(transactionName);
			System.out.println(resp);
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=NewTransaction(transactionName);
			if (resp.indexOf("Success\":true") == -1) { return resp; }			
			resp=AddInput(transactionName,FromAddress,factoshi);
			if (resp.indexOf("Success\":true") == -1) { return resp; }			
			resp=AddECOutput(transactionName,ToAddress,factoshi);
			if (resp.indexOf("Success\":true") == -1) { return resp; }			
			resp=GetFee(transactionName); //getfee without a transactionname gives entry credit exchange rate.  just a warning.
			resp=AddFee(transactionName,FromAddress);
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=SignTransaction(transactionName);
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=SubmitTransaction(transactionName);
			
		} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
			
			System.out.println(resp);
			
		}
		return resp;
	} // buyentrycreditsfulltransaction
	
	
	/**SendFactoidsFullTransaction  - 
	 *This is not base functionality.  It is a composite call that combines the 7 calls required for 
	 * sending factoid into one call for ease of use.  In that vein, this function handled the factoshi conversion
	* @param FromAddress - address sending factoid.  address or local label 
	* @param ToAddress - Address receiving factoid
	* @param Amount  - As Factoid, not factoshi 
	* @return
	* JSON document as string.  Formatted for terminal<p>
	* {"Response":"Success Submitting transaction","Success":true}
	* or error message as Response
	* <p>
	* Example:<p>
	* responseText=SendFactoidsFullTransaction("FA35Ky2jEMwhFqXhvARBKGryUzRmxGoXt8RM2fDsVXws2W2tXm5J","FA2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX",10.5); 
	**/	
	public static String SendFactoidsFullTransaction(String FromAddress,String ToAddress,Double Amount) {
		if (Amount < 0) {
			return "{\"Response\":\"Negative Values Not Allowed.\",\"Success\":false}";
		}
		String resp="";
		String transactionName="Tran001"; // if you are running more than one process at a time, you may want to change this
		Double dTemp=0.0;
		long factoshi=0;
		
		dTemp=(Amount * 100000000);
		factoshi= dTemp.longValue();  // convert to factoshi (1 factoshi=1/100000000 factoid)
		
		try {
			resp=DeleteTransaction(transactionName);
			
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=NewTransaction(transactionName);
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=AddInput(transactionName,FromAddress,factoshi);
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=AddOutput(transactionName,ToAddress,factoshi);
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=GetFee(transactionName); //getfee without a transactionname gives entry credit exchange rate.  just a warning.
			resp=AddFee(transactionName,FromAddress);	
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=SignTransaction(transactionName);
			if (resp.indexOf("Success\":true") == -1) { return resp; }
			resp=SubmitTransaction(transactionName);
			} catch (Exception e) {
			// this is only going to return an error on connectivity or some other communication error
			e.printStackTrace();
		
				
		}
		return resp;
	} // addInput
	
	
	
	/**ComposeChainCommit  - 
	 *This is not base functionality.  It builds the first entry of a chain
	* This does the transaction the hard way.  if your version of fctwallet has compose functionality, use that
	* It does leave signing to the fctwallet
	* 
	* @param name paying ec address
	* @param extids external ids as array of strings
	* @param data - data to be to blockchain entry
	* @return
	* JSON document containing chainid.  Formatted for terminal<p>
	* {"Response":"11914c19a28a98c57d12f3cce6c32b7944784f4b4781a706c24eb1dc284e2856","Success":true}
	* or error message as Response
	* <p>
	* Example:<p>
	* String [] extids={"123","456"}
	* 
	* responseText=ComposeChainCommit("EC2RYZzZxJvu2xT6JdKrVLjCMjXX5pmYyNMJG4tLoSyihvTemwyX",extids,"here is the body of your entry"); 
	**/		
	
	public static String ComposeChainCommit(String name, String[] extids,String data) {
		String resp="";		
		Chain c=new Chain();	
		Entry e=new  Entry();	
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
		
		
		double256Chain=utils.sha256Bytes(utils.sha256Bytes(c.ChainID));
		weld=utils.sha256Bytes(utils.sha256Bytes(utils.appendByteArrays(e.entryHash , c.ChainID)));

		postData=utils.appendByteToArray(postData, (byte)0);						//version
		postData=utils.appendByteArrays(postData,utils. MilliTime());		//millitime
		postData=utils.appendByteArrays(postData,double256Chain );//chainid hash hash twice
		postData=utils.appendByteArrays(postData, weld);	//commit weld
		postData=utils.appendByteArrays(postData,e.entryHash );		//hash of first entry
		postData=utils.appendByteToArray(postData, (byte)11);						//cost
		
		
		//rI could use a json marshal here, but I want the message format to be obvious.
		String jsonPost="{\"Message\":\"" + utils.bytesToHex(postData).toLowerCase() + "\"}";
		
		
		resp=utils.executePost(fctwalletURL + "/v1/commit-chain/" + name,jsonPost);

		if ( resp.equals("200")){
			System.out.println("waiting 10 seconds before calling reveal.");
			System.out.println("Milestone 2 will have receipts making this redundant.");
			try{
			Thread.sleep(10000);
			} catch (Exception ex) {
				ex.printStackTrace();
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
	
	
	/**ComposeEntryCommit  - 
	 *This is not base functionality.  It adds an entry to a known chain
	* This does the transaction the hard way.  if your version of fctwallet has compose functionality, use that
	* It does leave signing to the fctwallet
	* 
	* @param name paying ec address
	* @param extids external ids as array of strings
	* @param chainID - chain id
	* @param data - data to be to blockchain entry
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
  public static String ComposeEntryCommit(String name,String[] extids,String chainID,String data)  {
	  	String resp="";

		byte[] postData=new byte[0];

		Entry e=new Entry();
		
		// change this.  it is client.java specific and looking for -c and -e
		e.setChainID(chainID);
		e.setExtIDs(extids);
		e.Content = data.getBytes();
		
		//now that everything is is, hash it
		e.setEntryHash();
		
		// build commit
		postData=utils.appendByteToArray(postData, (byte)0);			//version
		postData=utils.appendByteArrays(postData, utils.MilliTime());	//millitime
		if (e.entryHash == null) {
			e.setEntryHash();
		}
		postData=utils.appendByteArrays(postData, e.entryHash);	//hash of entry
		postData=utils.appendByteToArray(postData, (byte)1);	//entry cost (entry content divided by 1k
		//   send this to fctwallet for signing

		String temp="{\"Message\":\"" + utils.bytesToHex(postData) + "\"}";
		//rI could use a json marshal here, but I want the message format to be obvious.

		resp=utils.executePost(fctwalletURL + "/v1/commit-entry/" + name,temp);

		if ( resp.equals("200")){
			System.out.println("waiting 10 seconds before calling reveal");
			System.out.println("Milestone 2 will have receipts making this redundant.");
			try{
				Thread.sleep(10000);
			} catch (Exception ex) {
			// this should never happen
				ex.printStackTrace();
				
			}
			resp=RevealChainOrEntry(e,"Entry");
		} else {
			
		}		
		return resp;    
	   
  }
  
  // called by compose
	/**RevealChainOrEntry  - 
	 * Adding chains and entries is a two step process.  first commit.  This is Factom committing to post your entry
	 * Then you reveal what that entry is
	* 
	* @param  {@link Factom.Entry} 
	* @param RevealType - String - 'Chain' or 'Entry'
	* @return
	* JSON document containing entry hash.  Formatted for terminal<p>
	* {"Response":"75668b338e44cb45998594c9cf5f36b52929d85bb616a4052b043da30319956d","Success":true}
	* or error message as Response
	* <p>
	* Example:<p>

	* responseText=RevealChainOrEntry(e,"Chain"); 
	**/	
  private static String RevealChainOrEntry(Entry e,String RevealType) {
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
		postData=utils.appendByteArrays(postData, e.Content);	//extid size (2 bytes)
		
		temp="{\"Entry\":\"" + utils.bytesToHex(postData) + "\"}";
		
		//rI could use a json marshal here, but I want the message format to be obvious.		
		postData=utils.appendByteArrays("{\"Entry\":\"".getBytes(), postData);
		postData=utils.appendByteArrays( postData,"\"}".getBytes());


		if (RevealType=="Chain"){
			resp=utils.executePost(factomdURL + "/v1/reveal-chain/",temp);
			    System.out.println("Chain ID: " + utils.bytesToHex(e.ChainID));
		} else if (RevealType=="Entry"){
			resp=utils.executePost(factomdURL + "/v1/reveal-entry/",temp);
				System.out.println("Entry Hash: " + utils.bytesToHex(e.entryHash));
		}

		
		
		return resp;    
	   
  }



}
