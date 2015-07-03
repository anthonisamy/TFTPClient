package thm.tftpclient.helpers;


import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketClient {
	private static DatagramSocket SOCKET = null;
	private static final int PORT = 976;
	
	//private  static String IP = "212.201.29.100";
	String msg = "test message";
	byte[] BUFFER = msg.getBytes();

	public  DatagramSocket CreateConnection() {
		try {
			InetAddress IP=InetAddress.getByName("localhost");
		
			SOCKET=new DatagramSocket();
			SOCKET.connect(IP, PORT);
			
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

}
