package thm.tftpclient.context;

import thm.tftpclient.state.TFTPClientState;
import thm.tftpclient.states.errorState;
import thm.tftpclient.states.getState;
import thm.tftpclient.states.putState;
import thm.tftpclient.states.readyState;

public class TFTPClient {
	TFTPClientState readyState;
	TFTPClientState getState;
	TFTPClientState putState;
	TFTPClientState errorState;

	TFTPClientState clientState;

	public TFTPClient() {
		readyState = new readyState(this);
		getState = new getState(this);
		putState = new putState(this);
		errorState=new errorState(this);
		
		clientState=readyState;

	}
	public void download(){
		clientState.download();
	}
	public void upload(){
		clientState.upload();
	}
	
	public void setCurrentState(TFTPClientState clientState) {
		this.clientState = clientState;
	}
	public TFTPClientState getCurrentState(){
		return clientState;
	}

	public TFTPClientState getReadyState() {
		return readyState;
	}

	public TFTPClientState getGetState() {
		return getState;
	}

	public TFTPClientState getPutState() {
		return putState;
	}

	public TFTPClientState getErrorState() {
		return errorState;
	}
	

	
}
