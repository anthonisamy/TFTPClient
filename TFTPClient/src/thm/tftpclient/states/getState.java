package thm.tftpclient.states;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.state.TFTPClientState;

public class getState implements TFTPClientState {
	TFTPClient tftpClient;

	public getState(TFTPClient client) {
		tftpClient = client;
	}

	@Override
	public void upload() {
		
		this.doRequestProcessing();
	}

	@Override
	public void download() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doRequestProcessing() {
		System.out.println("Please enter the file to Download:");
	}

}
