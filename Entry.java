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
		 for (int i=0 ; i < extIDs.length ;i++) {
				 item=extIDs[i].getBytes();
				 ba[eidlen]=item;				
				 eidlen=eidlen+1;		 
		 }
		 ExtIDs=ba;	
	}
	
	
	
	public void setChainID(String chain) {
				 ChainID=utils.hexToBytes(chain);
	}
	
	public void createChainID() {
		byte[] buildHash=new byte[0];
		
		if (ExtIDs.length == 0) {
			// you have to have external IDs
			System.out.println("That Chain ID exists already.  Please adjust External IDs to create unique initial Chain ID");
			System.exit(1);
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
	
	// and entry has is the whole entry as a byte string.
	// hash that with sha512
	// append the entrybytes to that 512 hash
	// hash that new byte array with sha256
	
	public void setEntryHash() {
		byte[] resp;
		
		byte[] entrybytes=toBytes();
		resp=utils.sha256Bytes(utils.appendByteArrays(utils.sha512Bytes(entrybytes),entrybytes));
		
		entryHash= resp;

	}

}