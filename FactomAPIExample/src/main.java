
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			FactomAPI.api.factomdURL="http://localhost:8088";

			
			//put head chain in blockchain'
			byte[] seed=FactomAPI.utils.hexToBytes("0000000000000000000000000000000000000000000000000000000000000000");
			FactomAPI.api.setAddressFromSeed(seed);
			//FactomAPI.api.importPrivateKey("Es...");
			System.out.println(FactomAPI.api.ecAddress);
			System.out.println(FactomAPI.api.ecPrivateAddress);

			
			// the HEX beginning says this is byte data not a string, but since commit takes strings......'
			// extid[X]="HEX:000000000000"
			// the 'HEX:' at the beginning is stripped and hex converted to bytes in the Entry.setExtIDs call
			
			String[] extids=new String[5];
		    extids[0]="FactomAPI Test Chain";
		    extids[1]="2nd External ID";
		    extids[2]="This is the third External ID";
		    extids[3]="You should sign the content in here somewhere.";
		    extids[4]="Having a time value in the content you are signing is also more secure.";
		    String content="This is the content of the first entry which is also the chain creation.";
			String ChainID=FactomAPI.api.ComposeChainCommit(FactomAPI.api.ecAddress, extids, content,true);
			
			extids=new String[5];
		    extids[0]="FactomAPI Test Chain Entry";
		    extids[1]="These do not need to match the chain extids";
		    extids[2]="But can.  It is application specific";
		    extids[3]="You should still sign the content in here somewhere.";
		    extids[4]="Having a time value in the content you are signing is also more secure.";	
		    content="This is the content of the first entry in the chain. You also have a chainID to attach it to";
			ChainID=FactomAPI.api.ComposeEntryCommit(FactomAPI.api.ecAddress, extids,ChainID, content,true);		    
	}

}
