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
	private byte[] RCVBUFFER = new byte[512];

	private byte[] message = new byte[512];
	MessageCreateor messageCreator=new MessageCreateor();

	public getState(TFTPClient client) {
		tftpClient = client;
	}

	@Override
	public void upload() {

	}

	@Override
	public void download() {
		this.doRequestProcessing();

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
			DatagramPacket SNDPACKET = new DatagramPacket(message,
					message.length);
			DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
					RCVBUFFER.length);
			mySockClient = new SocketClient();
			SOCKET = mySockClient.CreateConnection();
			if (SOCKET != null) {
				SOCKET.send(SNDPACKET);
				SOCKET.receive(RCVPACKET);
				if (RCVPACKET != null) {
					RCVBUFFER = RCVPACKET.getData();
					if (RCVBUFFER != null) {
						int opcode = RCVBUFFER[1];
						switch (opcode) {
						/*case 1:
							break;

						case 2:
							break;*/
						//Data
						case 3:
							System.out.println("hello");
							messageCreator.processData(RCVBUFFER);
							break;
							//ACK
						case 4:
							messageCreator.handleAck(RCVBUFFER);
							break;
							//ERROR
						case 5:
							messageCreator.handleError(RCVBUFFER);
							tftpClient.setClientState(tftpClient.getErrorState());
							break;
						default:
							break;
						}
					}
				}
			} else {
				tftpClient.setClientState(tftpClient.getErrorState());
			}
		} catch (IOException ex) {

		}

	}

}
