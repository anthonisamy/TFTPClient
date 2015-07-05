package thm.tftpclient.states;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.helpers.MessageCreateor;
import thm.tftpclient.state.TFTPClientState;


public class errorState implements TFTPClientState {
	TFTPClient tftpClient;
	MessageCreateor messageCreateor=new MessageCreateor();
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
		System.out.println("Error code: "+messageCreateor.getErrorCode()[0]+""+messageCreateor.getErrorCode()[1]);
		//System.out.println("Error code: "+messageCreateor.byteToInt(messageCreateor.getErrorCode()));
		System.out.println("Error message: "+messageCreateor.getErrorMsg());
		System.exit(0);

	}

	

}
