package thm.tftpclient.state;




public interface TFTPClientState {
	byte[] createRequestPacket();

	void upload();

	void download();

	void handleError();
}
