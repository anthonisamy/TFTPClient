package com.ramesh.jsup.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

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
				InetSocketAddress address=new InetSocketAddress(IPADDRESS,PORT );
				// Send Message to Server
				try {
					SOCK.bind(address);
					
					if(SOCK.isConnected()){
						DatagramPacket SENDPACKET = new DatagramPacket(BUFFER,
								BUFFER.length, IPADDRESS, PORT);
						SOCK.send(SENDPACKET);
						byte[] RCVBUFFER = new byte[1024];
						DatagramPacket RCVPACKET = new DatagramPacket(RCVBUFFER,
								RCVBUFFER.length);
						SOCK.receive(RCVPACKET);
						String receivemsg = new String(RCVPACKET.getData(), 0,
								RCVPACKET.getData().length);

						System.err.println(receivemsg.trim());
					}
					else{
						System.out.println("Socket Connection Failed!!");
					}

					// Receiving Reply From the server
					
				} catch (Exception ex) {

				}

				// close socket
				SOCK.close();
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage() + "ramesh basnet");

		}

	}

}
