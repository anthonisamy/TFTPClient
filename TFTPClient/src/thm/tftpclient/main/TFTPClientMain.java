package thm.tftpclient.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import thm.tftpclient.context.TFTPClient;
import thm.tftpclient.helpers.SocketClient;

public class TFTPClientMain {

	public static void main(String[] args) {
		System.out
				.println("=========================================================================================================");
		System.out
				.println("*********************************************************************************************************");
		System.out.println("||\t\t\t\t\t\t\t\t\t\t\t\t\t||");
		System.out.println("||\t\t\t\t\t\tTFTP CLIENT\t\t\t\t\t\t||");
		System.out.println("||\t\t\t\t\t\t\t\t\t\t\t\t\t||");
		System.out.println("||\t\t\t\t\t\t\t\t\t\t\t\t\t||");
		System.out
				.println("*********************************************************************************************************");
		System.out
				.println("=========================================================================================================");

		BufferedReader bufferread = new BufferedReader(new InputStreamReader(
				System.in));
		SocketClient SOCK = new SocketClient();
		String choice = "yes";
		while (choice.equalsIgnoreCase("yes")) {
			TFTPClient tftpClient = new TFTPClient();
			System.out.println(tftpClient.getCurrentState());
			System.out.println("Choose Your Action:(GET/PUT):");
			try {
				String input = bufferread.readLine();
				if (input.equalsIgnoreCase("get")) {
					{
						tftpClient.download();

					}
					if (tftpClient.getCurrentState() == tftpClient
							.getErrorState()) {
						tftpClient.handleError();
					}

					else {
						System.out.println("download Sucessfull!");

						System.out
								.println("Do you want to Take Another Action?(YES/NO):");
						choice = bufferread.readLine();
						if(choice.equalsIgnoreCase("no")){
							System.err.println("Program Terminated With Your Choice[NO]");
							System.exit(0);
						}
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
								.println("Upload Sucessfull! Do you want to Take Another Action?(YES/NO):");
						choice = bufferread.readLine();
						if(choice.equalsIgnoreCase("no")){
							System.err.println("Program Terminated With Your Choice[NO]");
							System.exit(0);
						}
						SOCK.getSOCKET2().close();

					}
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
