package com.ramesh.jsup.test;

public class IdComparison {
	

	public String makeid(String _ID) {
		String idValue=new String();
		String tempCharHolder=new String();
		String intHolder=new String();
		int valueIncrementer=0;
		int lengthOfArg = 0;
		idValue = _ID;
		lengthOfArg = idValue.length();
		
		for (int i = 0; i < lengthOfArg; i++) {
			char a = idValue.charAt(i);
			if (Character.isDigit(a)) {
				
			intHolder+=a;
			}
			else
			{
				tempCharHolder += a;
			}
			
		}
		
		
		valueIncrementer=Integer.parseInt(intHolder);
		valueIncrementer=valueIncrementer+1;
		tempCharHolder=tempCharHolder+valueIncrementer;
		return tempCharHolder;
	}
	public boolean CompareId(String idFromWeb,String idFromFile){
		try{
		String tempDigitFile=new String();
		String tempDigitWeb=new String();
		String valueHolderFile=new String(idFromFile);
		String valueHolderWeb=new String(idFromWeb);
		int intForWeb=0;
		int intForFile=0;
		for(int i=0;i<valueHolderFile.length();i++){
			char a=valueHolderFile.charAt(i);
			if(Character.isDigit(a)){
				tempDigitFile+=a;
			}
		}
		intForFile=Integer.parseInt(tempDigitFile);
		for(int i=0;i<valueHolderWeb.length();i++){
			char a=valueHolderWeb.charAt(i);
			if(Character.isDigit(a)){
				tempDigitWeb+=a;
			}
		}
		intForWeb=Integer.parseInt(tempDigitWeb);
		if((intForFile+1)==intForWeb){
			return true;
		}
		else return false;
		}catch(Exception ex){
			return false;
		}
	}

}
