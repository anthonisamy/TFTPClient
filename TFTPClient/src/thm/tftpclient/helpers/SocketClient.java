package thm.tftpclient.helpers;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketClient {
	private static DatagramSocket SOCKET = null;
	private static DatagramSocket SOCKET2 = null;
	//private static final int PORT = 69;
	
	//private  static String IP = "212.201.29.100";
	String msg = "test message";
	byte[] BUFFER = msg.getBytes();

	public  DatagramSocket CreateConnection(int port) {
		try {
			
		
			//SOCKET2=new DatagramSocket(2034,IP);
			SOCKET=new DatagramSocket(2034);
			//SOCKET.connect(IP, port);
			
			if (SOCKET!=null) {
				
				return SOCKET;
			}

			return null;

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return null;
		}
		// return SOCKET;

	}
	public void sendToServer(byte[] BUFFER,int port){
		
		try {
			InetAddress IP=InetAddress.getByName("10.10.10.1");
			SOCKET2 = new DatagramSocket(2034);
			DatagramPacket SNDPACKET = new DatagramPacket(BUFFER,BUFFER.length,IP,port);
			SOCKET2.send(SNDPACKET);
			//return SOCKET2;
			//SOCKET2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	//RRQ sent
		//SOCKET2.close();
		//return null;
	}

	public  DatagramSocket getSOCKET2() {
		return SOCKET2;
	}

}
