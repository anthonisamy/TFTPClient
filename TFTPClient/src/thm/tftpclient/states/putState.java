package thm.tftpclient.states;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.state.TFTPClientState;

public class putState implements TFTPClientState {
	TFTPClient tftpClient;
	public putState(TFTPClient client) {
		tftpClient=client;
	}

	@Override
	public void Upload(byte[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Download(byte[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleError() {
		// TODO Auto-generated method stub

	}

}
