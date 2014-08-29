package bankManagementServer;

import FinancialNews.NewsReceiver;
import FinancialNews.NewsReceiverPrxHelper;
import FinancialNews.NewsServerPrx;
import FinancialNews.NewsServerPrxHelper;

public class Server {

	public void t1(String[] args) {
		int status = 0;
		Ice.Communicator ic = null;

		try {
			ic = Ice.Util.initialize(args);
			Ice.ObjectAdapter adapter = ic.createObjectAdapter("Adapter11");
			
			NewsServerPrx server = NewsServerPrxHelper.checkedCast(ic
					.propertyToProxy("FinancialNews").ice_twoway()
					.ice_timeout(-1).ice_secure(false));
			if (server == null) {
				System.err.println("invalid proxy");
				return;
			}
			
//
//			NewsReceiver nr = new NewsReceiverI();
//			
//			server.registerForNews(nr);

			// HelloI hello = new HelloI(11, ic);
			// Ice.Object obj = hello;

			Ice.ServantLocator evictor = new RegularClientEvictor(2);
			adapter.addServantLocator(evictor, "account");

			////////////////////////////////BankManager//////////////////////////////////
			BankManagerI manager = new BankManagerI(ic);
			Ice.Identity bankManagerIdentity = new Ice.Identity();
			bankManagerIdentity.category = "bankManager";
			bankManagerIdentity.name = "bankManager";
			adapter.add(manager, bankManagerIdentity);
			/////////////////////////////////////////////////////////////////////////////


			adapter.activate();

			System.out.println("Entering event processing loop...");
			ic.waitForShutdown();
		} catch (Exception e) {
			System.err.println(e);
			status = 1;
		}
		if (ic != null) {
			// Clean up
			//
			try {
				ic.destroy();
			} catch (Exception e) {
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}

	public static void main(String[] args) {
		Server app = new Server();
		app.t1(args);
	}
}
