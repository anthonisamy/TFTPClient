package thm.tftpclient.helpers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketClient {
	DatagramSocket SOCKET = null;
	int PORT = 69;
	String IP = "212.201.29.100";
	String msg = "test message";
	byte[] BUFFER = msg.getBytes();

	DatagramSocket CreateConnection() {
		try {
			InetAddress IPADDRESS = InetAddress.getByName(IP);
			SOCKET = new DatagramSocket(PORT, IPADDRESS);
			DatagramPacket PACKET = new DatagramPacket(BUFFER, BUFFER.length);
			// DatagramPacket PACKET=new DatagramPacket(BUFFER, BUFFER.length,
			// IPADDRESS, PORT);
			if (SOCKET.isConnected()) {
				SOCKET.send(PACKET);
				return SOCKET;
			}

			return null;

		} catch (Exception ex) {
			return null;
		}
		// return SOCKET;

	}

}
