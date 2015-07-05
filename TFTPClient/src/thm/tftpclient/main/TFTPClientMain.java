package thm.tftpclient.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.helpers.SocketClient;

public class TFTPClientMain {

	public static void main(String[] args) {
		BufferedReader bufferread = new BufferedReader(new InputStreamReader(
				System.in));
		SocketClient SOCK=new SocketClient();
		String choice="yes";
			while (choice.equalsIgnoreCase("yes")) {
			TFTPClient tftpClient = new TFTPClient();

			System.out.println("What you want to do? get/put");
			try {
				String input = bufferread.readLine();
				if (input.equalsIgnoreCase("get")) {
					{tftpClient.download();
					
					}
					if (tftpClient.getCurrentState() == tftpClient
							.getErrorState()) {
						tftpClient.handleError();
					}

					else {
						System.out
						.println("download Sucessfull!");

						System.out
								.println("Do you want to Take Another Action?yes/no");
						choice= bufferread.readLine();
						SOCK.getSOCKET2().close();
					}

				} else if (input.equalsIgnoreCase("put")) {
					tftpClient.upload();
					if (tftpClient.getCurrentState() == tftpClient
							.getErrorState()) {
						tftpClient.handleError();
					}

					else {

						System.out
								.println("Upload Sucessfull! Do you want to Take Another Action?yes/no");
						choice = bufferread.readLine();
						SOCK.getSOCKET2().close();
						
					}
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
