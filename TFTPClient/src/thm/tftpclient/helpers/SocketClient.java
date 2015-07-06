package thm.tftpclient.helpers;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketClient {
	private static DatagramSocket SOCKET2 = null;

	String msg = "test message";
	byte[] BUFFER = msg.getBytes();


	public void sendToServer(byte[] BUFFER,int port){
		
		try {
			InetAddress IP=InetAddress.getByName("localhost");
			SOCKET2 = new DatagramSocket(2034);
			DatagramPacket SNDPACKET = new DatagramPacket(BUFFER,BUFFER.length,IP,port);
			SOCKET2.send(SNDPACKET);
			//return SOCKET2;
			//SOCKET2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.exit(0);
		}	//RRQ sent
		//SOCKET2.close();
		//return null;
	}

	public  DatagramSocket getSOCKET2() {
		return SOCKET2;
	}

}
