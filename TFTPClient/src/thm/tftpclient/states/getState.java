package thm.tftpclient.states;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.helpers.MessageCreateor;
import thm.tftpclient.helpers.SocketClient;
import thm.tftpclient.state.TFTPClientState;

public class getState implements TFTPClientState {
	TFTPClient tftpClient;
	private String fileName = null;
	private static int opcode = 1;
	DatagramSocket SOCKET;
	SocketClient mySockClient;

	private byte[] message = new byte[512];

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

		message = MessageCreateor.createRequestMessage(fileName, opcode);

		try {
			DatagramPacket PACKET = new DatagramPacket(message, message.length);
			mySockClient = new SocketClient();
			SOCKET = mySockClient.CreateConnection();
			if (SOCKET != null) {
				SOCKET.send(PACKET);
			}
			else{
				tftpClient.setClientState(tftpClient.getErrorState());
			}
		} catch (IOException ex) {

		}

	}

}
