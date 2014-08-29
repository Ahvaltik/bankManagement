package bankManagementServer;

import java.io.File;
import java.util.ArrayList;

import Bank.IncorrectData;
import Bank.NoSuchAccount;
import Bank.PersonalData;
import Bank.RequestRejected;
import Bank._BankManagerDisp;
import Bank.accountType;
import Ice.Current;
import Ice.Identity;
import Ice.StringHolder;

public class BankManagerI extends _BankManagerDisp {
	Ice.Communicator ic;
	int n = 0;
	static ArrayList<String> accounts = new ArrayList<String>();

	public BankManagerI(Ice.Communicator ic) {
		this.ic = ic;
		File folder = new File(
				"/home/eithne/new_workspace/bankManagement/serializedAccounts");
		File[] listOfFiles = folder.listFiles();
		int maxAccountNumber = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				accounts.add(listOfFiles[i].getName());
				if (Integer.parseInt(listOfFiles[i].getName())>= maxAccountNumber){
					maxAccountNumber = Integer.parseInt(listOfFiles[i].getName());
					maxAccountNumber++;
				}
			}
		}
		n = maxAccountNumber;
	}

	public static boolean accountAlreadyExists(String accountID) {
//		if (accounts.contains(accountID)) {
//			System.out.println("Account exists");
//			System.out.println(accounts.toString());
//		} else {
//			System.out.println("Account doesn't exist");
//			System.out.println(accounts.toString());
//		}
		return accounts.contains(accountID);
	}

	@Override
	public void createAccount(PersonalData data, accountType type,
			StringHolder accountID, Current __current) throws IncorrectData,
			RequestRejected {

		System.out.println("Creating an account");
		accountID.value = String.valueOf(n);
		n++;
		AccountInformation information = new AccountInformation(data, type,
				accountID.value);

		HandleFiles.serialize(information);
		if (type == accountType.PREMIUM) {
			PremiumAccountI account = new PremiumAccountI(accountID.value);
			__current.adapter.add(account, new Identity(accountID.value,
					"account"));
		}
		accounts.add(accountID.value);
		System.out.println("Account successfully created");
	}

	@Override
	public void removeAccount(String accountID, Current __current)
			throws IncorrectData, NoSuchAccount {
		System.out.println("Removing an account");
		if (__current.adapter.find(ic.stringToIdentity(accountID)) != null)
			__current.adapter.remove(ic.stringToIdentity(accountID));
		HandleFiles.remove(accountID);
		accounts.remove(accountID);
		System.out.println("Account successfully removed");
	}

}
