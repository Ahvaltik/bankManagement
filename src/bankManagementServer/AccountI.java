package bankManagementServer;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import Bank._AccountDisp;
import Ice.Current;

public class AccountI extends _AccountDisp {

	String accountID;
	AccountInformation info;

	public AccountI(String accountID) {
		this.info = HandleFiles.deserialize(accountID);
		this.accountID = accountID;
	}

	@Override
	public int getBalance(Current __current) {
		System.out.println("Sending balance state of "+ this.accountID);
		return info.getBalance();
	}

	@Override
	public String getAccountNumber(Current __current) {
		System.out.println("Sending account number of "+ this.accountID);
		return info.getAccountID();
	}

	@Override
	public void transfer(String accountNumber, int amount, Current __current)
			throws IncorrectAccountNumber, IncorrectAmount {

		System.out.println("Starting a transfer");
		int currentBalance = getBalance();
		if (amount > currentBalance || amount < 0)
			throw new IncorrectAmount();
		if (!BankManagerI.accountAlreadyExists(accountNumber))
			throw new IncorrectAccountNumber();
		System.out.println("Transfer in progress");
		info.changeBalance(-amount);
		System.out.println("Transfer in progress");
		HandleFiles.serialize(info);
		System.out.println("Transfer in progress");
		AccountInformation recipient = HandleFiles.deserialize(accountNumber);
		System.out.println("Transfer in progress");
		recipient.changeBalance(amount);
		System.out.println("Transfer in progress");
		HandleFiles.serialize(recipient);
		System.out.println("Transfer successfully finished");
	}
}
