package thm.tftpclient.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import thm.tftpclient.context.TFTPClient;

public class TFTPClientMain {

	public static void main(String[] args) {
		BufferedReader bufferread = new BufferedReader(new InputStreamReader(
				System.in));
		boolean choice = true;
		while (choice) {
			TFTPClient tftpClient = new TFTPClient();

			System.out.println("What you want to do?");
			try {
				String input = bufferread.readLine();
				if (input.equalsIgnoreCase("get")) {
					tftpClient.download(); 
					if (tftpClient.getCurrentState() == tftpClient
							.getErrorState()) {
						tftpClient.handleError();
					}

					else {

						System.out
								.println("download Sucessfule! Do you want to Take Another Action?yes/no");
						input = bufferread.readLine();
						choice = Boolean.getBoolean(input);
					}

				} else if (input.equalsIgnoreCase("put")) {
					tftpClient.upload();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
