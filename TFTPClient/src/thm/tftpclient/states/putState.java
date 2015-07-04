package thm.tftpclient.states;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.helpers.MessageCreateor;
import thm.tftpclient.helpers.SocketClient;
import thm.tftpclient.state.TFTPClientState;

public class putState implements TFTPClientState {
	TFTPClient tftpClient;
	// variables
	private String fileName = null;
	private byte[] message = new byte[512];
	private static final int opcodeInt=2;
	private byte[] RCVBUFFER = new byte[512];
	// objects
	Scanner scanner = new Scanner(System.in);
	MessageCreateor messageCreator = new MessageCreateor();
	SocketClient mySockClient;
	DatagramSocket SOCKET;

	public putState(TFTPClient client) {
		tftpClient = client;
	}

	@Override
	public void upload() {
		System.out.println("Enter a file Name to upload:");
		fileName = scanner.nextLine();
		message=MessageCreateor.createRequestMessage(fileName, opcodeInt);
		mySockClient=new SocketClient();
		mySockClient.sendToServer(message,69);
		System.out.println("WRQ msg Created");
		try{
			SOCKET=mySockClient.getSOCKET2();
			DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
					RCVBUFFER.length);
			SOCKET.receive(RCVPACKET);
			SOCKET.close();
		}
		catch(Exception ex){
			
		}
	}

	@Override
	public void download() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleError() {
		// TODO Auto-generated method stub

	}

	public byte[] createRequestPacket() {
		return null;
		// TODO Auto-generated method stub

	}

}
