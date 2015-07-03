package thm.tftpclient.state;

import java.io.IOException;


public interface TFTPClientState {
	void doRequestProcessing();

	void upload();

	void download();

	void handleError();
}
