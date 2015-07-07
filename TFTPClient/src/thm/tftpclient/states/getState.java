package thm.tftpclient.states;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.helpers.MessageCreateor;
import thm.tftpclient.helpers.SocketClient;
import thm.tftpclient.state.TFTPClientState;

public class getState implements TFTPClientState {
	TFTPClient tftpClient;
	private String fileName = null;
	private static final int opcode = 1;
	DatagramSocket SOCKET;
	SocketClient mySockClient;
	private byte[] RCVBUFFER = new byte[516];
	private int serverPort = 0;
	Scanner scanner = new Scanner(System.in);

	private byte[] message = new byte[512];
	MessageCreateor messageCreator = new MessageCreateor();

	public getState(TFTPClient client) {
		tftpClient = client;
	}

	@Override
	public void upload() {

	}

	@Override
	public void download() {
		// this.createRequestPacket();
		boolean firstTime = true;
		boolean lastpack = false;
		System.out.println("Please enter the file Name:");
		fileName = scanner.nextLine();
		
		message = MessageCreateor.createRequestMessage(fileName, opcode);
		mySockClient = new SocketClient();
		mySockClient.sendToServer(message, 69);
		System.out.println("RRQ Message Sent!");
		try {

			while (!lastpack) {

				SOCKET = mySockClient.getSOCKET2();
			
				DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
						RCVBUFFER.length);
				for (int i = 0; i < 6; i++) {
					SOCKET.setSoTimeout(3000);

					try {

						SOCKET.receive(RCVPACKET);
						break;

					} catch (SocketTimeoutException ex) {
						System.out.println("Timeout occur!");
						if (messageCreator.getBlockNum()[1] == 1 && firstTime) {
							mySockClient.sendToServer(message, 69);
							mySockClient.getSOCKET2().close();
						} else {
							SOCKET.close();
							byte[] ack = null;
							ack = messageCreator.createAck(messageCreator
									.getBlockNum());
							mySockClient.sendToServer(ack, serverPort);
							System.out.println("Packet resent!");
							SOCKET = mySockClient.getSOCKET2();
						}
						if (i == 5) {
							System.err.println("Timeout Exceeded!");
							System.err
									.println("The program is terminated. Bye!");
							System.exit(0);
						}

					}

				}
				SOCKET.close();
				if (RCVPACKET != null) {
					serverPort = RCVPACKET.getPort();
					System.out.println("Packet received on port " + serverPort);
					RCVBUFFER = RCVPACKET.getData();
					if (RCVBUFFER != null) {
						int opcode = RCVBUFFER[1];
						switch (opcode) {
						case 1:
							break;

						case 2:
							break;
						// Data
						case 3:

							byte[] ack = null;
							messageCreator.processData(RCVBUFFER);
							ack = messageCreator.createAck(messageCreator
									.getBlockNum());

							String input = "yes";

							if ((messageCreator.getBlockNum()[1] == 1)
									&& firstTime) {
								firstTime = false;
								System.out
										.println("The first block of data is received and ACK is created:");
								System.out.println("Read Request Message: "+message);
								System.out.println("Received packet (1): "+RCVBUFFER);
								System.out.println("Created ACK packet(1): "+ack);
								System.out
										.println("Do you want to continue?(YES/NO):");

								input = scanner.nextLine();
								if(input.equalsIgnoreCase("yes")){
									MessageCreateor.checkFileExistsToWrite(fileName);	
								}
								
								// message=ack;
							}
							if (input.equalsIgnoreCase("yes")) {

								mySockClient.sendToServer(ack, serverPort);
								messageCreator.writeToFile(
										messageCreator.getData(), fileName);
								if (RCVPACKET.getLength() < 512) {
									lastpack = true;
									RCVPACKET = null;
									messageCreator.getOut().close();
									SOCKET.close();
								}

								break;

								// continue;
							} else {
								System.err.println("File Transfer Cancelled!!");
								System.err.println("Program Terminated!!!");
								System.exit(0);
							}
							
						case 5:
							System.err.println(RCVBUFFER+":Packet is recived with Following:");
							messageCreator.handleError(RCVBUFFER);
							tftpClient.setCurrentState(tftpClient
									.getErrorState());
							lastpack = true;
							break;

						default:
							break;
						}
					}
				}

			}

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			System.err.println("Program Terminated!");
			System.exit(0);
		}

	}

	@Override
	public void handleError() {

	}
}
