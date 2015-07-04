package thm.tftpclient.helpers;

public class MessageCreateor {
	private static String mode="Octet";
	private static final int ackOpcode=4;
	private static byte[] opcode=new byte[2];
	private static byte[] REQUEST=new byte[512];
	private byte[] blockNum=null;
	private byte[] data=null;
	private byte[] errorCode=null;
	private String errorMsg=null;
	
	public static byte[] createRequestMessage(String fileName,int opcodeInt){
		int position=0;
		opcode=opcodeEncoder(opcodeInt);
		byte[] fileNameByte=fileName.getBytes();
		byte[] modeByte=mode.getBytes();
		System.arraycopy(opcode, 0, REQUEST, 0, opcode.length);
		System.arraycopy(fileNameByte, 0, REQUEST, opcode.length, fileNameByte.length);//check
		position=opcode.length+fileName.length()+1;
		REQUEST[position]=0;
		System.arraycopy(modeByte, 0, REQUEST, position, modeByte.length);
		position=position+modeByte.length+1;
		REQUEST[position]=0;
		return REQUEST;
		
		
	}
	public void processData(byte[] RCVDMSG){
		blockNum=null;
		data=null;
		System.arraycopy(RCVDMSG, 2, blockNum, 0, 2);
		System.arraycopy(RCVDMSG, 4, data, 0, RCVDMSG.length-4);
		
	}
	public void handleAck(byte[] RCVDMSG){
		
	}
	public  static  byte[] opcodeEncoder(int opcode){
		byte[] mybyte=new byte[2];
		mybyte[0]=(byte)((opcode>>8)&0xff);
		mybyte[1]=(byte)(opcode&0xff);
		return mybyte;
		
	}
	public void handleError(byte[] RCVDMSG){
		errorCode=new byte[2];
		System.arraycopy(RCVDMSG, 2, errorCode, 0, 2);
		int i=4;
		int count=0;
		while(RCVDMSG[i]!=0){
			count++;
		}
		errorMsg=new String(RCVDMSG, 4, count);
	}
	public byte[] createAck(byte[] blockNumber){
		byte[] ACKMSG=new byte[4];
		opcode=opcodeEncoder(ackOpcode);
		System.arraycopy(opcode, 0, ACKMSG, 0, 2);
		System.arraycopy(blockNumber, 0, ACKMSG, 2, 2);
		
		return ACKMSG;
		
	}
	public void writeToFile(byte[] data,String fileName){
		
	}
	public byte[] getBlockNum() {
		return blockNum;
	}
	public byte[] getData() {
		return data;
	}
	public byte[] getErrorCode() {
		return errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	

}
