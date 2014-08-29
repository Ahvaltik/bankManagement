package bankManagementServer;

import FinancialNews.Currency;
import FinancialNews._NewsReceiverDisp;
import Ice.Current;

public class NewsReceiverI extends _NewsReceiverDisp {

	@Override
	public void exchangeRate(Currency curr1, Currency curr2, float rate,
			Current __current) {
		// TODO Auto-generated method stub
		CurrencyMap.exchangeRate.put(curr1.toString(), rate);
	}

	@Override
	public void interestRate(Currency curr, float rate, Current __current) {
		// TODO Auto-generated method stub
		CurrencyMap.interestRate.put(curr.toString(), rate);
	}

}
