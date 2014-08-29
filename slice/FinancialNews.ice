
module FinancialNews {

  enum Currency {PLN, USD, EUR, CHF};


  interface NewsReceiver
  {
    void exchangeRate(Currency curr1, Currency curr2, float rate); 
    void interestRate(Currency curr, float rate); 
  };


  interface NewsServer {
    void registerForNews (NewsReceiver* subscriber);
  };


};