package Factom;

public  class Chain {
		public byte[] ChainID;
		public Entry FirstEntry;
		

		
		public void setFirstEntry(Entry e){
			
			if (e.ChainID == null) {
				e.createChainID();
			}
			if (ChainID == null){
				ChainID=e.ChainID ;
			}
			FirstEntry=e;
		}
		
		public byte[] toBytes(){
			byte[] cID=ChainID;
			byte[] eBytes;
			
			eBytes=FirstEntry.toBytes();
			byte[] resp=new byte[cID.length + eBytes.length ];
			System.arraycopy(cID, 0, resp, 0, cID.length);
			System.arraycopy(eBytes, 0, resp, cID.length, eBytes.length);			
			
			return resp;
		}
		
	}
	


