package thm.tftpclient.state;




public interface TFTPClientState {

	void upload();

	void download();

	void handleError();
}
