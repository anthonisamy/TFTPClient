package thm.tftpclient.state;


public interface TFTPClientState {
	void doRequestProcessing();

	void upload();

	void download();

	void handleError();
}
