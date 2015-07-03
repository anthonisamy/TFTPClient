package thm.tftpclient.helpers;


import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketClient {
	private static DatagramSocket SOCKET = null;
	private static int PORT = 69;
	private  static String IP = "212.201.29.100";
	String msg = "test message";
	byte[] BUFFER = msg.getBytes();

	public  DatagramSocket CreateConnection() {
		try {
			InetAddress IPADDRESS = InetAddress.getByName(IP);
			SOCKET = new DatagramSocket(PORT, IPADDRESS);
			
			// DatagramPacket PACKET=new DatagramPacket(BUFFER, BUFFER.length,
			// IPADDRESS, PORT);
			if (SOCKET.isConnected()) {
				//SOCKET.send(PACKET);
				return SOCKET;
			}

			return null;

		} catch (Exception ex) {
			return null;
		}
		// return SOCKET;

	}

}
