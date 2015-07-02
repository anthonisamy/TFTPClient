package thm.tftpclient.context;

import thm.tftpclient.state.TFTPClientState;
import thm.tftpclient.states.errorState;
import thm.tftpclient.states.getState;
import thm.tftpclient.states.putState;
import thm.tftpclient.states.readyState;

public class TFTPClient {
	TFTPClientState ready;
	TFTPClientState get;
	TFTPClientState put;
	TFTPClientState error;

	public TFTPClient() {
		ready = new readyState(this);
		get = new getState(this);
		put = new putState(this);
		error=new errorState(this);

	}
}
