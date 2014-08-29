package bankManagementServer;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import Bank.IncorrectData;
import Bank._PremiumAccountDisp;
import Bank.currency;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;

public class PremiumAccountI extends _PremiumAccountDisp {

	String accountID;
	AccountInformation info;
	
	public PremiumAccountI(String accountID){
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


	@Override
	public void calculateLoan(int amount, currency curr, int period,
			IntHolder totalCost, FloatHolder interestRate, Current __current)
			throws IncorrectData {
		System.out.println("Calculating a loan");
		interestRate.value = CurrencyMap.interestRate.get(curr.toString());
		totalCost.value = (int)((amount * CurrencyMap.exchangeRate.get(curr.toString())) *(1 + interestRate.value));
		totalCost.value -= totalCost.value *  this.info.getRateDecrease();
		System.out.println("Loan calculated");
	}
}
