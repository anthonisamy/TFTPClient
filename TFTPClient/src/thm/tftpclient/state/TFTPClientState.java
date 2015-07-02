package thm.tftpclient.state;

public interface TFTPClientState {

	void Upload();
	void Download();
	void handleError();
}
