package thm.tftpclient.states;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.state.TFTPClientState;


public class errorState implements TFTPClientState {
	TFTPClient tftpClient;
	public errorState(TFTPClient client) {
		tftpClient=client;
	}

	@Override
	public void upload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void download() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleError() {
		// TODO Auto-generated method stub

	}

	

}
