package thm.tftpclient.states;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
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
	private static final int opcodeInt = 2;
	private byte[] RCVBUFFER = new byte[512];
	private byte[] SNDBUFFER = null;
	boolean firstTime = true;
	boolean lastpack = false;
	private int serverPort = 0;
	private byte[] currentBlockNumber = new byte[2];
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
		currentBlockNumber[0] = 0;
		currentBlockNumber[0] = 0;
		System.out.println("Enter a file Name to upload:");
		fileName = scanner.nextLine();
		message = MessageCreateor.createRequestMessage(fileName, opcodeInt);
		mySockClient = new SocketClient();
		mySockClient.sendToServer(message, 69);
		System.out.println("WRQ msg Created");
		try {
			while (!lastpack) {

				SOCKET = mySockClient.getSOCKET2();//2034
				DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER, RCVBUFFER.length);
				// SOCKET.receive(RCVPACKET);
				for(int i=0;i<3;i++){
					SOCKET.setSoTimeout(5000);
					
						try {
							
							SOCKET.receive(RCVPACKET);
							break;
							
						} catch (SocketTimeoutException ex) {
							System.out.println("Timeout occur!");
							if (messageCreator.byteToInt(currentBlockNumber) == 0) {
								mySockClient.sendToServer(message, 69);
								mySockClient.getSOCKET2().close();
							} else {
								SOCKET.close();
								SNDBUFFER = messageCreator.createDataPacket(fileName, currentBlockNumber);
								mySockClient.sendToServer(SNDBUFFER, serverPort);
								System.out.println("Packet resent!");
								SOCKET=mySockClient.getSOCKET2();
								System.out.println("Connection open again!");
							}

							if(i==2){
								System.out.println("Timeout exceeded. Program terminated.");
								System.exit(0);
							}
						}
					
				}
				
				SOCKET.close();
				if (RCVPACKET != null) {
					serverPort = RCVPACKET.getPort();
					/*if(serverPort==-1){
						tftpClient.setCurrentState(tftpClient.getErrorState());
						break;
					}*/
					System.out.println("ACK received from port " + serverPort);
					RCVBUFFER = RCVPACKET.getData();
					if (RCVBUFFER != null) {
						int opcode = RCVBUFFER[1];
						switch (opcode) {
						case 1:
							break;

						case 2:
							break;
						
						// ACK

						case 4:
							String input = "yes";
							byte[] SNDBUFFER = null;
							if (messageCreator.byteToInt(currentBlockNumber) == 0 && firstTime) {
								firstTime = false;
								System.out.println("The ACK is Received and first block of data is created:");
								System.out.println("ACK" + RCVBUFFER);
								System.out.println("Data Block 1"
										+ messageCreator.createDataPacket(fileName, MessageCreateor.opcodeEncoder(1)));
								System.out.println("Do you want to continue? yes/no");

								input = scanner.nextLine();
							}
							if (input.equalsIgnoreCase("yes")) {

								currentBlockNumber = messageCreator.increment(currentBlockNumber);
								SNDBUFFER = messageCreator.createDataPacket(fileName, currentBlockNumber);

								mySockClient.sendToServer(SNDBUFFER, serverPort);
								if (SNDBUFFER.length < 516) {
									lastpack = true;
									SOCKET.close();
								}
							}
							break;

						// ERROR
						case 5:
							messageCreator.handleError(RCVBUFFER);
							tftpClient.setCurrentState(tftpClient.getErrorState());
							lastpack = true;
							break;

						default:
							break;
						}
					}
				}

			}
		} catch (Exception ex) {

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

	

}
