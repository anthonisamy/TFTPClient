package thm.tftpclient.states;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.helpers.MessageCreateor;
import thm.tftpclient.state.TFTPClientState;

public class errorState implements TFTPClientState {
	TFTPClient tftpClient;
	MessageCreateor messageCreateor = new MessageCreateor();

	public errorState(TFTPClient client) {
		tftpClient = client;
	}

	@Override
	public void upload() {

	}

	@Override
	public void download() {

	}

	@Override
	public void handleError() {
		System.out.println("Current State:" + tftpClient.getCurrentState());
		System.err.println("***********************************");
		System.err.println("*\t\tERROR\t\t\t");
		System.err.println("***********************************");
		System.err.println("* \tFollowing Error Occured!!!!\t ");
		System.err.println("*\tError code: "
				+ messageCreateor.getErrorCode()[0] + ""
				+ messageCreateor.getErrorCode()[1] + "\t");
		System.err.println("*\tError message: " + messageCreateor.getErrorMsg()
				+ "\t");
		System.err.println("*\t Program Terminated!!!!" + "\t ");
		System.err.println("***********************************");
		System.exit(0);

	}

}
