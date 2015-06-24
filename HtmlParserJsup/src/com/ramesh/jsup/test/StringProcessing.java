package com.ramesh.jsup.test;

public class StringProcessing {
	private String tempCommentaryBuffer;
	private char[] chararray;
	private String scoringClub;
	private String againstClub;

	public void FindKeyEvent(String Class, String Commentary, String time) {
		if (Class.equalsIgnoreCase("no-icon")) {
			chararray=Commentary.toCharArray();
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<chararray.length;i++){
				if(chararray[i]!=' '){
					sb.append(chararray[i]);
				}
			}
			//String sanitized = tempCommentaryBuffer.replaceAll("[\uFEFF-\uFFFF]", ""); 
			//System.out.println(sb.toString());
			tempCommentaryBuffer=sb.toString();
			System.out.println(tempCommentaryBuffer.subSequence(0,5));
			
		} else if (Class.equalsIgnoreCase("goal")) {
			tempCommentaryBuffer=Commentary.trim();
			System.out.println(tempCommentaryBuffer.subSequence(0, 5)+"I am from goal.............................");
			System.out.println(tempCommentaryBuffer.split(".").toString());

		} else if (Class.equalsIgnoreCase("half-time")) {

		} else if (Class.equalsIgnoreCase("substitution")) {

		} else if (Class.equalsIgnoreCase("yellow-card")) {

		} else if (Class.equalsIgnoreCase("red-card")) {

		}
		else{
			
		}
	}
}
