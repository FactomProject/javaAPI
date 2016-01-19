/**
 * 
 */
package Factom;
import java.util.Scanner;
/**
 * @author matt whittington
 * @version 0.1
 * 
 * This is a command line interface for dealing with the apiCalls class. 
 *  In a future version, it will be separated and only import the apicalls class
 *
 */
public class client {

	/**
	 *  for usage run man
	 */
	public static void main(String[] args) {

		String test=""; 

		if (args.length == 0) {
			 test= man("ALL");			
		} else {
			if (args[0].equals("addfee")) {
				test=apiCalls.AddFee(args[1],args[2]);
			}
			if (args[0].equals("addinput")) {
				test=apiCalls.AddInput(args[1],args[2],Integer.parseInt(args[3]));
			}
			else if (args[0].equals("addoutput")) {
				test=apiCalls.AddOutput(args[1],args[2],Integer.parseInt(args[3]));
			}
			else if (args[0].equals("addecoutput")) {
				test=apiCalls.AddECOutput(args[1],args[2],Integer.parseInt(args[3]));
			}
			else if (args[0].equals("buyentrycredits")) {
				test=apiCalls.BuyEntryCreditsFullTransaction(args[1],args[2],Float.parseFloat(args[3]));
			}
			else if (args[0].equals("deletetransaction")) {
				test=apiCalls.DeleteTransaction(args[1]);
			}
			else if (args[0].equals("get")) {
				if (args[1].equals("head")) {
					test=apiCalls.GetDBlockHead();
				}
				else if (args[1].equals("height")) {
					test=apiCalls.GetDBlockHeight();
				}
				else if (args[1].equals("dblock")) {
					if (args[2].equals("keymr")){
						System.out.println("Please enter a merkler root, not 'keymr'");
					} else {
						test=apiCalls.GetDBlock(args[2]);						
					}

				}
				else if (args[1].equals("chain")) {
					if (args[2].equals("chainid")){
						System.out.println("Please enter a chain id, not 'chainid'");
					} else {
						test=apiCalls.GetChainHead(args[2]);						
					}

				}
				else if (args[1].equals("eblock")) {
					if (args[2].equals("keymr")){
						System.out.println("Please enter a merkler root, not 'keymr'");
					} else {
						test=apiCalls.GetEBlock(args[2]);						
					}

				}
				else if (args[1].equals("entry")) {
					if (args[2].equals("hash")){
						System.out.println("Please enter an entry hash, not 'hash'");
					} else {
						test=apiCalls.GetEntry(args[2]);						
					}

				}
				else if (args[1].equals("firstentry")) {
					if (args[2].equals("chainid")){
						System.out.println("Please enter a chain id, not 'chainid'");
					} else {
						test=apiCalls.GetFirstEntry(args[2]);						
					}

				}
			}
			else if (args[0].equals("getaddresses")) {
				test=apiCalls.GetAddresses();
			}
			else if (args[0].equals("getexchangerate")) {
				test=apiCalls.GetExchangeRate();
			}
			else if (args[0].equals("getfee")) {
				test=apiCalls.GetFee(args[1]);
			}
			else if (args[0].equals("mkchain")) {
			    Scanner stdin = new Scanner(System.in);
			    String chainBody="";
			    System.out.println("Enter the content of your entry here.  \nEnter ctrl-Z on a blank line to end keyboard capture");
			    while(stdin.hasNext()){
			    	String line = stdin.next();
			        chainBody += " " + line;
			    }
			
				stdin.close();
			    //args[1] is the payment address
				// semantics, but chain body is really 'entry body'
				// chains don't have bodies. Entries do.  This also makes initial entry
				test=apiCalls.ComposeChainCommit(args[1],args,chainBody);
			}
			else if (args[0].equals("newaddress") || args[0].equals("generateaddress")) {
				try { // if the correct number of arguments are not sent, call man
					if (args.length == 3) {
					  if (args[1].toLowerCase().equals("ftc")){
						  test=apiCalls.GenerateFactoidAddress(args[2]);
					  } else if (args[1].toLowerCase().equals("ec")){
						  test=apiCalls.GenerateFactoidAddress(args[2]);
					  }
					} else if (args.length == 4) {
						  if (args[1].toLowerCase().equals("fct")){
							  test=apiCalls.GenerateAddressFromHumanReadablePrivateKey(args[2],args[3]);
						  } else if (args[1].toLowerCase().equals("ec")){
							  test=apiCalls.GenerateEntryCreditAddressFromHumanReadablePrivateKey(args[2],args[3]);
						  }					   
					} else {
						// not enough or too many command line arguments
						man("newaddress");
					}
			          
					} catch (Exception e) {
					  man("newaddress"); 	
					}
			}
			else if (args[0].equals("newtransaction")) {
				test=apiCalls.NewTransaction(args[1]);
			}
			else if (args[0].equals("put")) {
			    Scanner stdin = new Scanner(System.in);
			    String entryBody="";
			    System.out.println("Enter the content of your entry here.  \nEnter ctrl-Z on a blank line to end keyboard capture");
			    while(stdin.hasNext()){
			    	String line = stdin.next();
			    	entryBody += " " + line;
			    }
			
				stdin.close();
			    //args[1] is the payment address
				test=apiCalls.ComposeEntryCommit(args[1],args,entryBody);
			}
			else if (args[0].equals("sign")) {
				test=apiCalls.SignTransaction(args[1]);
			}
			else if (args[0].equals("submit")) {
				test=apiCalls.SubmitTransaction(args[1]);
			}
			else if (args[0].equals("transactions")) {
				test=apiCalls.SendFactoidsFullTransaction(args[1],args[2],Float.parseFloat(args[3]));
			}
			else if (args[0].equals("sendfactoids")) {
				test=apiCalls.SendFactoidsFullTransaction(args[1],args[2],Float.parseFloat(args[3]));
			}
			
		}

		 if (!test.equals("")){
			 System.out.println(  test);
		 } else {
			 man("ALL");
		 }
	

	}
	
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
// DIRECTIONS FOR USE
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static String man(String cmd) {
System.out.println("factom-cli	[options]	[subcommand]");		
if (cmd.equals("ALL") || cmd.equals("get")) {
System.out.println("get\t\t");			
System.out.println("\thead\tGet the keymr of the last completed directory block");					
System.out.println("\theight\tGet the currenct directory block height");					
System.out.println("\tdblock keymr\tGet dblock contents by merkle root");					
System.out.println("\tchain chainid\tGet ebhead by chainid");					
System.out.println("\teblock keymr\tGet eblock by merkle root");					
System.out.println("\tfirstentry chainid\tGet the first entry in a chain");					
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("mkchain")) {
System.out.println("mkchain\tname\tCreate a new factom chain. read the data for the");			
System.out.println("\t t\tfirst entry from stdin.  Use the entry credits");			
System.out.println("\t t\tfrom the specified name.");			
System.out.println("\t-e externalid\tExternal Id for the first entry. Can be used");			
System.out.println("\t \tmore than once");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("put")) {
System.out.println("put\tname\tRead data from stdid and write to Factom.  Use");			
System.out.println("\t t\tthe entry credits from the specified entry credit");			
System.out.println("\t t\taddress.");			
System.out.println("\t-e [externalid]\tSpecify an external id for the factom entry. -e");			
System.out.println("\t \tcan be used multiple times.");			
System.out.println("\t-c [chainid]\tSpecify the chain that the entry belongs to");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("newaddress") || cmd.equals("generateaddress")) {
System.out.println("new address or");
System.out.println("generateaddress\t\tGenerate addresses, giving them names.");
System.out.println("\tec name\tGenerate an Entry Credit address, tied to the name.");
System.out.println("\tfct name\tGenerate a factoid address, tied to the name.");
System.out.println("\t \tNames must be unique, or you will get a");
System.out.println("\t \tDuplicate Name or Invalid Name Error.");
System.out.println("\tec name Es...\tImport a secret EC key to the wallet");
System.out.println("\tftc name Es...\tImport a secret Factoid key to the wallet");
System.out.println("\tftc name \"12 words\"\tImport a secret Factoid key to the wallet");
System.out.println(" ");
}
if (cmd.equals("ALL") || cmd.equals("addinput")) {
System.out.println("addinput\t\tAdd an input to a transaction");			
System.out.println("\tkey name amount\tUse the name supplied in newaddress");			
System.out.println("\tkey address amount\tUse an address");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("addecoutput")) {
System.out.println("addecoutput\t\tAdd an ecoutput (Purchase of entry credits) to");			
System.out.println("\tt\ta transaction");			
System.out.println("\tkey name amount\tUse the name supplied in newaddress");			
System.out.println("\tkey address amount\tUse an address");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("outinput")) {
System.out.println("addoutput\t\tAdd an output to a transaction");			
System.out.println("\tkey name amount\tUse the name supplied in newaddress");			
System.out.println("\tkey address amount\tUse an address");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("deletetransaction")) {
System.out.println("deletetransaction key\tDelete the specified transaction in flight.");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("getfee")) {
System.out.println("getfee key \tGet the current fee required for this");			
System.out.println("\t \ttransaction.  If a transaction is specified,");			
System.out.println("\t \tthen getfee returns the fee due for the ");			
System.out.println("\t \ttransaction.  If no transaction is provided,");			
System.out.println("\t \tthen the cost of en Entry Credit is returned.");			
System.out.println("\t \t(for EC cost, please use getExchangeRate)");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("addfee")) {
System.out.println("addfee trans address \tAdds the needed fee to the given transaction.");			
System.out.println("\t \tThe address specified must be an input to");			
System.out.println("\t \tthe transaction, and it must have a balance");			
System.out.println("\t \table to cover the additional fee.  Also, the");			
System.out.println("\t \tinputs must exactly balance the outputs,");			
System.out.println("\t \tsince the logic to understand what to do ");			
System.out.println("\t \totherwise is quite complicated, and prone");			
System.out.println("\t \tto odd behavior.");			
System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("getExchangeRate")) {
System.out.println("getexchangerate key \tGet the current exchange rate for");			
System.out.println("\t \tfactoids to Entry Credits");			

System.out.println(" ");	
}
if (cmd.equals("ALL") || cmd.equals("newtransaction")) {
System.out.println("newtransaction key\tCreate a new transaction. The key is used to");			
System.out.println("\t\tadd inputs, outputs, and ecoutputs (to buy");			
System.out.println("\t\tentry credits).  Once the transaction is built,");			
System.out.println("\t\tcall validate, and if all is good, submit.");			
System.out.println(" ");
}
if (cmd.equals("ALL") || cmd.equals("signtransaction")) {
System.out.println("sign\tkey\tSign the transaction specified by the key.");			
System.out.println(" ");
}
if (cmd.equals("ALL") || cmd.equals("submittransaction")) {
System.out.println("submit\tkey\tSign the transaction specified by the key.");			
System.out.println(" ");
}
if (cmd.equals("ALL") || cmd.equals("sendfactoids")) {
System.out.println("sendfactoid\t \tsingle call to send factoid.");			
System.out.println(" \tfrom to amount\tfrom - address sending factoid.");			
System.out.println(" \t \tto - address receiving factoid.");			
System.out.println(" \t \tamount - in factoids");			
System.out.println(" ");
}
if (cmd.equals("ALL") || cmd.equals("buyentrycredits")) {
System.out.println("buyentrycredit\t \tsingle call to buy entry credits.");			
System.out.println(" \tfrom to amount\tfrom - address sending factoid.");			
System.out.println(" \t \tto - ec address receiving factoid.");			
System.out.println(" \t \tamount - in factoids");			
System.out.println(" ");
}

return " "; // this is just managing the test println in main.
}  //man



}
