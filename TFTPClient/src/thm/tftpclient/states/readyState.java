package thm.tftpclient.states;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.state.TFTPClientState;

public class readyState implements TFTPClientState {
	TFTPClient tftpClient;
	public readyState(TFTPClient client) {
		tftpClient=client;
	}

	@Override
	public void upload() {
		tftpClient.setClientState(tftpClient.getPutState());

	}

	@Override
	public void download() {
		tftpClient.setClientState(tftpClient.getGetState());

	}

	@Override
	public void handleError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doRequestProcessing() {
		// TODO Auto-generated method stub
		
	}

}
