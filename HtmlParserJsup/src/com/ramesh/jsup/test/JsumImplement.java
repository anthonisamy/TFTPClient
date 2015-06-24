package com.ramesh.jsup.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;




public class JsumImplement {
	public static BlockingQueue<String> blockingQueue=new ArrayBlockingQueue<String>(100);

	public static void main(String args[]) {
		
	
		
	ThreadedClass threadedclass=new ThreadedClass();
	Thread thread=new Thread(threadedclass);
	//threadedclass.run();
	thread.start();
	/*UdpClientThread udpClientThread=new UdpClientThread();
	Thread updThread=new Thread(udpClientThread);
	updThread.start();*/

	}
}
