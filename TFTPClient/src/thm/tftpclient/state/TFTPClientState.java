package thm.tftpclient.state;

public interface TFTPClientState {

	void Upload(byte[] data);
	void Download(byte[] data);
	void handleError();
}
