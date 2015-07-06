package thm.tftpclient.states;

import java.io.IOException;
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
		System.out.println("RRQ msg Created");
		try {

			while (!lastpack) {

				SOCKET = mySockClient.getSOCKET2();
				/*
				 * if(SOCKET.isConnected()) {
				 * System.out.println("Socket again connected"+
				 * SOCKET.getPort()); } //SOCKET.setSoTimeout(300*10);
				 */
				DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
						RCVBUFFER.length);
				for (int i = 0; i < 3; i++) {
					SOCKET.setSoTimeout(5000);

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
						if (i == 2) {
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
					System.out.println("packet received on port " + serverPort);
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
										.println("The First Block of data is Received and ack is created:");
								System.out.println(RCVBUFFER);
								System.out.println(ack);
								System.out
										.println("Do you want to continue?(YES/NO):");

								input = scanner.nextLine();
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
								System.exit(0);
							}
							// ask user whether u want to continue

							// loop now for all the remaining data packets
							// break;

							// ACK
							/*
							 * case 4: messageCreator.handleAck(RCVBUFFER);
							 * break; //ERROR
							 */
						case 5:
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

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Program Terminated!");
			System.exit(0);
		}

	}

	@Override
	public void handleError() {

	}

	/*
	 * public byte[] createRequestPacket() {
	 * System.out.println("Please enter the file Name:");
	 * fileName=scanner.nextLine(); System.out.println("hey baby"); message =
	 * MessageCreateor.createRequestMessage(fileName, opcode);
	 * 
	 * try { mySockClient = new SocketClient(); SOCKET =
	 * mySockClient.CreateConnection(); // DatagramPacket SNDPACKET = new
	 * DatagramPacket(message, message.length);
	 * 
	 * SOCKET.send(SNDPACKET); //SOCKET.setSoTimeout(3000); DatagramPacket
	 * RCVPACKET = new DatagramPacket(RCVBUFFER, RCVBUFFER.length);
	 * SOCKET.receive(RCVPACKET); if (RCVPACKET != null) { RCVBUFFER =
	 * RCVPACKET.getData(); if (RCVBUFFER != null) { int opcode = RCVBUFFER[1];
	 * switch (opcode) { case 1: break;
	 * 
	 * case 2: break; //Data case 3: System.out.println("hello");
	 * messageCreator.processData(RCVBUFFER); break; //ACK case 4:
	 * messageCreator.handleAck(RCVBUFFER); break; //ERROR case 5:
	 * messageCreator.handleError(RCVBUFFER);
	 * tftpClient.setCurrentState(tftpClient.getErrorState()); break; default:
	 * break; } } }
	 * 
	 * } catch (IOException ex) { System.err.println(ex.getMessage()); } return
	 * RCVBUFFER;
	 * 
	 * }
	 */

}
