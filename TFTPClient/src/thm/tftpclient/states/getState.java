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
		boolean lastpack = false;
		System.out.println("Please enter the file Name:");
		fileName = scanner.nextLine();
		message = MessageCreateor.createRequestMessage(fileName, opcode);
		try {
			mySockClient = new SocketClient();
			SOCKET = mySockClient.CreateConnection();

			while (!lastpack) {
				DatagramPacket SNDPACKET = new DatagramPacket(message,message.length);
				SOCKET.send(SNDPACKET);	//RRQ sent
				// SOCKET.setSoTimeout(3000);

				DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
						RCVBUFFER.length);
				SOCKET.receive(RCVPACKET);
				if (RCVPACKET != null) {
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
							ack = messageCreator.createAck(messageCreator.getBlockNum());

							String input = null;

							if ((messageCreator.getBlockNum()[1] == 1)) {
								System.out.println("The First Block of data is Received and ack is created:");
								System.out.println(RCVBUFFER);
								System.out.println(ack);
								System.out.println("Do you want to continue? yes/no");
								input = scanner.nextLine();
							}
							if (input.equalsIgnoreCase("yes")) {
								message=ack;
								messageCreator.writeToFile(messageCreator.getData(),fileName);
								if(RCVPACKET.getLength()<512){
									lastpack=true;
								}
								
								
								//continue;
							} 
							else {
								lastpack=true;
								System.exit(0);
							}
							// ask user whether u want to continue

							// loop now for all the remaining data packets
							//break;

						// ACK
						/*
						 * case 4: messageCreator.handleAck(RCVBUFFER); break;
						 * //ERROR
						 */
						case 5:
							messageCreator.handleError(RCVBUFFER);
							tftpClient.setCurrentState(tftpClient
									.getErrorState());
							lastpack=true;
							break;
							
							
						default:
							break;
						}
					}
				}
				
				
			}

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

	}

	@Override
	public void handleError() {
		System.out.println("I AM HRISTIJAN");
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
