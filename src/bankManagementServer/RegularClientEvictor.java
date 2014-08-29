package bankManagementServer;


public class RegularClientEvictor extends EvictorBase {
	public RegularClientEvictor(int i) {
		super(i);
	}

	public Ice.Object add(Ice.Current c, Ice.LocalObjectHolder cookie) {
		Ice.Object servant = new AccountI(c.id.name);
		return servant;
	}

	public void evict(Ice.Object servant, java.lang.Object cookie) {
	}
}
