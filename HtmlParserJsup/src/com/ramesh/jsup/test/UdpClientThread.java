package com.ramesh.jsup.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClientThread implements Runnable {

	@Override
	public void run() {
		DatagramSocket SOCK = null;
		String Message = null;
		int PORT = 976;
		/*
		 * BufferedReader bufferread = new BufferedReader(new InputStreamReader(
		 * System.in));
		 */
		try {
			InetAddress IPADDRESS = InetAddress.getByName("localhost");
			while (true) {

				SOCK = new DatagramSocket();
				System.out.println("UDP Client Thread Started");
				Message = JsumImplement.blockingQueue.take();
				byte[] BUFFER = Message.getBytes();

				// Send Message to Server
				DatagramPacket SENDPACKET = new DatagramPacket(BUFFER,
						BUFFER.length, IPADDRESS, PORT);
				SOCK.send(SENDPACKET);

				// Receiving Reply From the server
				byte[] RCVBUFFER = new byte[1024];
				DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
						RCVBUFFER.length);
				SOCK.receive(RCVPACKET);
				String receivemsg = new String(RCVPACKET.getData(), 0,
						RCVPACKET.getData().length);

				System.err.println(receivemsg.trim());
				// close socket
				SOCK.close();
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage() + "ramesh basnet");

		}

	}

}
