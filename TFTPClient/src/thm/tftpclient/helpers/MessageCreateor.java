package thm.tftpclient.helpers;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import thm.tftpclient.context.TFTPClient;

public class MessageCreateor {
	private static String mode="Octet";
	private static final int ackOpcode=4;
	private static final int dataOpcode=3;
	private static byte[] opcode=new byte[2];
	
	private byte[] blockNum=null;
	private   byte[] data=null;
	private static byte[] errorCode=null;
	private static String errorMsg=null;
	private FileOutputStream out=null;
	private FileInputStream input=null;
	private byte[] ackNumber = null;
	
	
	public static byte[] createRequestMessage(String fileName,int opcodeInt){
		byte[] REQUEST=new byte[512];
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
		blockNum=new byte[2];
		data=new byte[RCVDMSG.length-4];
		System.arraycopy(RCVDMSG, 2, blockNum, 0, 2);
		System.arraycopy(RCVDMSG, 4, data, 0, RCVDMSG.length-4);
		
	}
	public byte[] handleAck(byte[] RCVDMSG){
		ackNumber = new byte[2];
		System.arraycopy(RCVDMSG, 2, ackNumber, 0, 2);
		return ackNumber;
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
			i++;
		}
		errorMsg=new String(RCVDMSG, 4, count);
		System.out.println("yes");
	}
	public byte[] createAck(byte[] blockNumber){
		byte[] ACKMSG=new byte[4];
		opcode=opcodeEncoder(ackOpcode);
		System.arraycopy(opcode, 0, ACKMSG, 0, 2);
		System.arraycopy(blockNumber, 0, ACKMSG, 2, 2);
		
		return ACKMSG;
		
	}
	public void writeToFile(byte[] data,String fileName){
		
		try {
			File file = new File("E:\\tftpdownloads\\"+fileName);
			 out= new FileOutputStream(file,true);
		
			out.write(data);
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Program terminated. Bye!");
			System.exit(0);
			
		}
		
	}
	public byte[] createDataPacket(String fileName,byte[] currentBlockNumber){
		byte data [] = readFromFile(fileName);
		byte[] result;
		int length=data.length;
		int totalBlocks=(length/512)+1;
		int lastBlock = totalBlocks;
		/*if(totalBlocks>65535)
		{
			lastBlock = totalBlocks%65535;
		}*/
		int reserveBlocks=length%512;
		
		int block = byteToInt(currentBlockNumber);
		if(block==totalBlocks){
			result=new byte[4+length%512];
			opcode=opcodeEncoder(dataOpcode);
			System.arraycopy(opcode, 0, result, 0, 2);
			System.arraycopy(currentBlockNumber, 0, result, 2, 2);
			System.arraycopy(data, (block-1)*512, result, 4, length%512);
		}
		else{
			result=new byte[4+512];
		opcode=opcodeEncoder(dataOpcode);
		System.arraycopy(opcode, 0, result, 0, 2);
		System.arraycopy(currentBlockNumber, 0, result, 2, 2);
		System.arraycopy(data, (block-1)*512, result, 4, 512);
		//System.arraycopy(src, srcPos, dest, destPos, length);
		}
		return result;
	}
public  byte[] increment(byte[] valuea){
		
		 int value =  valuea[1] & 0xFF |
		            (valuea[0] & 0xFF) << 8 ;
		 value=value+1;
		return opcodeEncoder(value);
		            
	}
public int byteToInt(byte[] value){
	return value[1] & 0xFF |
     (value[0] & 0xFF) << 8 ;
}
	//read from file or make data packet
	public byte[] readFromFile(String fileName){

		try {
			 File file = new File("E:\\tftpuploads\\"+fileName);
			 byte[]  tempData=new byte[(int)file.length()];
			input=new FileInputStream(file);
			input.read(tempData);
			//writeToFile(tempData, fileName);	//Checking purposes
			input.close();
			return tempData;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println("Program terminated. Bye!");
			System.exit(0);
			return null;
		}
		
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
	public FileOutputStream getOut() {
		return out;
	}
	

}
