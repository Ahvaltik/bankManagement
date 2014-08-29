import Ice
import sys
Ice.loadSlice("../../slice/Bank.ice")
import Bank

class Client(Ice.Application):
    def run(self, args):
        connectionString = ":tcp -h 127.0.0.1 -p 11000:ssl -h 172.7.7.1 -p 10001"
        manager = Bank.BankManagerPrx.checkedCast(self.communicator().stringToProxy("bankManager/bankManager"+connectionString))
        ans=0
        while ans != '7' :
            print "1 - createAccount, 2- removeAccount, 3 - get balance, 4- get account number, 5 - transfer , 6 - calculate loan, 7 - leave"
            ans=raw_input("What would you like to do? ") 
            if ans == '1':
                firstName=raw_input("first name ")
                lastName=raw_input("last name ")
                PESEL=raw_input("PESEL ")
                accountType=raw_input("type [SILVER/PREMIUM] ")
                personalData = Bank.PersonalData()
                personalData.firstName = firstName
                personalData.lastName = lastName
                personalData.NationalIDNumber = PESEL
                accountID = 0
                if accountType == "PREMIUM":
                    accType =Bank.accountType.PREMIUM
                else:
                    accType = Bank.accountType.SILVER
                    
                print "Creating an account"
                accountID = manager.createAccount(personalData, accType, None)
            elif ans == '2':
                accountID=raw_input("account ID ")
                manager.removeAccount(accountID)
                print "Deleted" 
            elif ans == '3':
                accountID=raw_input("account ID ")
                account = Bank.AccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connectionString))
                print account.getBalance()
            elif ans == '4':
                accountID=raw_input("account ID ")
                account = Bank.AccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connectionString))
                print account.getAccountNumber()
            elif ans == '5':
                accountID=raw_input("account ID ")
                account = Bank.AccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connectionString))
                recipient=raw_input("Recipient ")
                amount=raw_input("Amount ")
                account.transfer(recipient, int(amount))
            elif ans == '6':
                accountID=raw_input("account ID ")
                account = Bank.PremiumAccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connectionString))
                amount = raw_input("Amount ")
                currency = raw_input("Currency ")
                if currency == "PLN":
                    curr = Bank.currency.PLN
                elif currency == "USD":
                    curr = Bank.currency.USD
                elif currency == "EUR":
                    curr = Bank.currency.EUR
                elif currency == "CHF":
                    curr = Bank.currency.CHF
                period = raw_input("Period ")
                (totalCost, interestRate) = account.calculateLoan(int(amount), curr, int(period), None)
                print "totalCost: "+str(totalCost)+" interestRate: "+str(interestRate)
        return 0

app = Client()
sys.exit(app.main(sys.argv, ""))
