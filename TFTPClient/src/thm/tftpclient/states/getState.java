package thm.tftpclient.states;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

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
	Scanner scanner=new Scanner(System.in);
	

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
		this.createRequestPacket();

	}

	@Override
	public void handleError() {
		System.out.println("I AM HRISTIJAN");
	}

	
	public byte[] createRequestPacket() {
		System.out.println("Please enter the file to Download:");
		fileName=scanner.nextLine();
		System.out.println("hey baby");
		message = MessageCreateor.createRequestMessage(fileName, opcode);

		try {
			mySockClient = new SocketClient();
			SOCKET = mySockClient.CreateConnection();
			DatagramPacket SNDPACKET = new DatagramPacket(message,
					message.length);
				
				SOCKET.send(SNDPACKET);
				DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
						RCVBUFFER.length);
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
							tftpClient.setCurrentState(tftpClient.getErrorState());
							break;
						default:
							break;
						}
					}
				}
			
		} catch (IOException ex) {
System.err.println(ex.getMessage());
		}
		return RCVBUFFER;

	}

}
