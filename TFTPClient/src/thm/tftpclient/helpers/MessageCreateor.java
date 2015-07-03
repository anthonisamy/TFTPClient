package thm.tftpclient.helpers;

public class MessageCreateor {
	private static String mode="Octet";
	private static byte[] opcode=new byte[2];
	private static byte[] REQUEST=new byte[512];
	public static byte[] createRequestMessage(String fileName,int opcodeInt){
		int position=0;
		opcode=opcodeEncoder(opcodeInt);
		byte[] fileNameByte=fileName.getBytes();
		byte[] modeByte=mode.getBytes();
		System.arraycopy(opcode, 0, REQUEST, 0, opcode.length);
		System.arraycopy(fileNameByte, 0, REQUEST, opcode.length+1, fileNameByte.length);//check
		position=opcode.length+fileName.length()+1;
		REQUEST[position]=0;
		System.arraycopy(modeByte, 0, REQUEST, position+1, modeByte.length);
		position=position+modeByte.length+1;
		REQUEST[position]=0;
		return REQUEST;
		
		
	}
	public void processData(byte[] RCVDMSG){
		
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
		
	}
	

}
