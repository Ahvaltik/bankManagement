package bankManagementServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Bank.NoSuchAccount;

public class HandleFiles {
	public static synchronized void serialize(AccountInformation information) {
		FileOutputStream fileOut;
		ObjectOutputStream out;
		try {
			fileOut = new FileOutputStream("serializedAccounts/"
					+ information.getAccountID().toString());
			out = new ObjectOutputStream(fileOut);
			out.writeObject(information);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized AccountInformation deserialize (String accountID){
		try
	      {
	         FileInputStream fileIn = new FileInputStream("serializedAccounts/" + accountID);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         AccountInformation info = (AccountInformation) in.readObject();
	         in.close();
	         fileIn.close();
	         return info;
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("class not found");
	         c.printStackTrace();
	      }
		return null;
	}
	
	public static synchronized void remove(String accountID) throws NoSuchAccount{
		File file = new File("serializedAccounts/" + accountID);
		if (file.delete()) {
			System.out.println("Deleting file "+ file.getName());
		} else {
			throw new NoSuchAccount();
		}
	}
}
