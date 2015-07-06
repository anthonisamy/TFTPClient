package thm.tftpclient.helpers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketClient {
	private static DatagramSocket SOCKET2 = null;

	public void sendToServer(byte[] BUFFER, int port) {

		try {
			InetAddress IP = InetAddress.getByName("tk-labor.iem.thm.de");
			SOCKET2 = new DatagramSocket(2034);
			DatagramPacket SNDPACKET = new DatagramPacket(BUFFER,
					BUFFER.length, IP, port);
			SOCKET2.send(SNDPACKET);

		} catch (IOException e) {
			System.err.println("Following Error Occured!!!");
			System.err.println("Program Terminated!");
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}

	public DatagramSocket getSOCKET2() {
		return SOCKET2;
	}

}
