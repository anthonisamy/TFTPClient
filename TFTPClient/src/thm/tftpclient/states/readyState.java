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
		System.out.println("Your state is changed to PUT state and you can make PUT request now.");
		tftpClient.setCurrentState(tftpClient.getPutState());
		tftpClient.getCurrentState().upload();
	}

	@Override
	public void download() {
		System.out.println("Your state is changed to get state and you can make get request now.");
		tftpClient.setCurrentState(tftpClient.getGetState());
		tftpClient.getCurrentState().download();

	}

	@Override
	public void handleError() {
		
	}
}
