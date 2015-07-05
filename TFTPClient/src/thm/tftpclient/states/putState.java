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
	private byte[] SNDBUFFER=null;
	boolean firstTime=true;
	boolean lastpack = false;
	private int serverPort=0;

	private byte[] currentBlockNumber=new byte[2];
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
		currentBlockNumber[0]=0;
		currentBlockNumber[0]=0;
		System.out.println("Enter a file Name to upload:");
		fileName = scanner.nextLine();
		message=MessageCreateor.createRequestMessage(fileName, opcodeInt);
		mySockClient=new SocketClient();
		mySockClient.sendToServer(message,69);
		System.out.println("WRQ msg Created");
		try{
			while (!lastpack) {
				
				SOCKET=mySockClient.getSOCKET2();
				DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
						RCVBUFFER.length);
				SOCKET.receive(RCVPACKET);
				SOCKET.close();
				if (RCVPACKET != null) {
					serverPort=RCVPACKET.getPort();
					System.out.println("ACK received on port "+ serverPort);
					RCVBUFFER = RCVPACKET.getData();
					if (RCVBUFFER != null) {
						int opcode = RCVBUFFER[1];
						switch (opcode) {
						case 1:
							break;

						case 2:
							break;
						// Data
				/*		case 3:
							
							byte[] ack = null;
							messageCreator.processData(RCVBUFFER);
							ack = messageCreator.createAck(messageCreator.getBlockNum());

							
							String input = "yes";
							
							if ((messageCreator.getBlockNum()[1] == 1) && firstTime) {
								firstTime=false;
								System.out.println("The First Block of data is Received and ack is created:");
								System.out.println(RCVBUFFER);
								System.out.println(ack);
								System.out.println("Do you want to continue? yes/no");
								
								input = scanner.nextLine();
								//message=ack;
							}
							if (input.equalsIgnoreCase("yes")) {
								
								mySockClient.sendToServer(ack, serverPort);
								messageCreator.writeToFile(messageCreator.getData(),fileName);
								if(RCVPACKET.getLength()<512){
									lastpack=true;
									messageCreator.getOut().close();
								SOCKET.close();
								}
								
								break;
								
								//continue;
							} 
							else {
								lastpack=true;
								System.exit(0);
							}*/
							// ask user whether u want to continue

							// loop now for all the remaining data packets
							//break;

						// ACK
						
						  case 4:
							 if( currentBlockNumber==messageCreator.handleAck(RCVBUFFER))
							 {
								 
								 System.out.println("Data block"+currentBlockNumber+" acknowledged.");
								currentBlockNumber= messageCreator.increment(currentBlockNumber);
								SNDBUFFER=messageCreator.createDataPacket(fileName, currentBlockNumber);	
								mySockClient.sendToServer(SNDBUFFER, serverPort);
							 }
							  break;
						
						  
						  //ERROR
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
