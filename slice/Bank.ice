
module Bank {
	
	exception IncorrectData { string reason; };
	exception RequestRejected { string reason; };
	exception IncorrectAccountNumber {};
	exception IncorrectAmount {};
	exception NoSuchAccount {};
	
	enum currency {PLN, USD, EUR, CHF};
	enum accountType {SILVER, PREMIUM};
	
	// amount: kwota w walucie krajowej (PLN) przemnożona przez 100 - 11,50 zł to 1150
	interface Account {
		int getBalance ();
		string getAccountNumber();
		void transfer(string accountNumber, int amount) throws IncorrectAccountNumber, IncorrectAmount;
	};
	
	// amount: kwota w walucie krajowej (PLN) przemnożona przez 100 - 11,50 zł to 1150
	// curr: waluta, w której ma być wzięty kredyt, włączając PLN
	// period: czas w miesiącach
	// totalCost: całkowity koszt kredytu w walucie narodowej w chwili bieżącej
	// interestRate: aktualne łączne oprocentowanie
	// zwracane wielkości powinny brać pod uwagę uzyskiwane z notyfikacji kursy waluty obcej i wysokość jej oprocentowania na rynku bankowym, prowizję banku oraz ewentualne zniżki dla wybranych klientów
	interface PremiumAccount extends Account {
		void calculateLoan(int amount, currency curr, int period, out int totalCost, out float interestRate) throws IncorrectData;
	};
	
	struct PersonalData {
		string firstName;
		string lastName;
		string nationalIDNumber;
	};
	
	//accountID: powinno być używane jako name w Identity identyfikującym konto.
	//System musi wiedzieć, które konta aktualnie (od założenia do usunięcia) istnieją.
	interface BankManager{
		void createAccount(PersonalData data, accountType type, out string accountID) throws IncorrectData, RequestRejected;
		void removeAccount(string accountID) throws IncorrectData, NoSuchAccount;
	};
};

