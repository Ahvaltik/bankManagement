package bankManagementServer;

import java.util.HashMap;
public class CurrencyMap {
	public static HashMap<String, Float> interestRate = new HashMap<String, Float>();
	public static HashMap<String, Float> exchangeRate = new HashMap<String, Float>();
	
	static {
		interestRate.put("PLN", 1f);
		interestRate.put("USD", 1f);
		interestRate.put("EUR", 1f);
		interestRate.put("CHF", 1f);
		
		exchangeRate.put("PLN", 1f);
		exchangeRate.put("USD", 1f);
		exchangeRate.put("EUR", 1f);
		exchangeRate.put("CHF", 1f);
	}
}
