package thm.tftpclient.state;

public interface TFTPClientState {
	void doRequestProcessing();

	void upload(byte[] data);

	void download(byte[] data);

	void handleError();
}
