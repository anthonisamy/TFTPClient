package thm.tftpclient.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import thm.tftpclient.context.TFTPClient;


public class TFTPClientMain {
	
	public static void main(String[] args) {
		BufferedReader bufferread = new BufferedReader(new InputStreamReader(
				System.in));
		TFTPClient tftpClient=new TFTPClient();
		System.out.println("What you want to do?");
		try {
			String input=bufferread.readLine();
			if(input.equalsIgnoreCase("get")){
				tftpClient.download(); //Called from current state: readyState
				tftpClient.download();	//Called from current state: getState
				
			
			}
			else if(input.equalsIgnoreCase("put")){
				tftpClient.upload();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
