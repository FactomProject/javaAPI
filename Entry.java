package Factom;

public  class Entry {
	public  byte[] ChainID;
	public  byte[][] ExtIDs;
	public  byte[] Content;
	public byte[] entryHash;

	
	public void setExtIDs(String[] extIDs) {
		Integer eidlen=extIDs.length;
		 byte[][] ba=new byte[eidlen][];
		 eidlen=0;
		 byte[] item;
		 boolean flg=false;
		 for (int i=0 ; i < extIDs.length ;i++) {
			 if (flg) { //only use items after a -e
				 item=extIDs[i].getBytes();
				 ba[eidlen]=item;
				
				 eidlen=eidlen+1;
			 }
			 if (extIDs[i].equals("-e")) {
				 flg=true;
			 } else {
				 flg=false;
			 }
		 }
		 //all ba array spots weren't used.  trim it to only have valid extid
		 byte[][] resp=new byte[eidlen][];
		 for (int i=0 ; i < eidlen ;i++) {
			 resp[i]=ba[i];
		 }	 
		 ExtIDs=resp;
		
	}
	
	
	
	public void setChainID(String[] args) {
		 byte[] item;
		 boolean flg=false;
		 for (int i=0 ; i < args.length ;i++) {
			 if (flg) { //only use items after a -e
				 item=utils.hexToBytes(args[i]);
				 
				 ChainID=item;
				 System.out.println("Chain=" + utils.bytesToHex(ChainID));
			 }
			 if (args[i].equals("-c")) {
				 flg=true;
			 } else {
				 flg=false;
			 }
		 }
	}
	
	public void createChainID() {
		byte[] buildHash=new byte[0];
		
		if (ExtIDs.length == 0) {
			//System.out.println("That Chain ID exists already.  Please adjust External IDs to create unique initial Chain ID");
			//System.exit(1);
		}
		 
		 for (int i=0 ; i < ExtIDs.length ;i++) {
			buildHash= utils.appendByteArrays(buildHash,  utils.sha256Bytes(ExtIDs[i]));				 
		 }			
		ChainID=utils.sha256Bytes(buildHash);
	}
	
	public byte[] toBytes(){
		byte[] resp=new byte[0];
		int i=0;
		 int extidlength=0;
		if (ChainID == null) {
			createChainID();
		}

			resp=utils.appendByteToArray(resp, (byte)0);						//version
			resp=utils.appendByteArrays(resp, ChainID);						//ChainID
			
			if (ExtIDs == null) {  							
				resp=utils.appendByteToArray(resp, (byte)0); // no external ID
			} else {
				// get length of ext ids (=2 each for next sections individual extid length)
				for (i=0;i<ExtIDs .length ;i++){  
					extidlength=extidlength + ExtIDs[i].length + 2;
				}
				//extid size (2 bytes) length of all extid data + item counts
				resp=utils.appendByteArrays(resp,  utils.IntToByteArray(extidlength ));	
				for ( i=0;i< ExtIDs .length ;i++){  //add each ext id
					resp=utils.appendByteArrays(resp, utils.IntToByteArray(ExtIDs[i].length ) );				//extid size (2 bytes)
					resp=utils.appendByteArrays(resp, ExtIDs [i]);
				}				
			}

			if (Content == null) {
				// skip content
			} else {
				resp=utils.appendByteArrays(resp, Content);	//add content				
			}
				
			return resp;			
	}
	
	public void setEntryHash() {
		byte[] resp;
		
		byte[] entrybytes=toBytes();
		resp=utils.sha256Bytes(utils.appendByteArrays(utils.sha512Bytes(entrybytes),entrybytes));
		
		entryHash= resp;
		
		  try {
			  System.out.println("content:" + new String(Content , "UTF-8") );
			  } catch (Exception exc){
				  
			  }
	}

}