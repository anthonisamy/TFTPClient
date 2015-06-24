package com.ramesh.jsup.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Date;

public class ThreadedClass implements Runnable {
	private static String commentaryId=null;
	String ID = new String();
	

	public ThreadedClass() {
		
	}

	@Override
	public void run() {
		try {

			File file = new File("G:\\semanticWebProject\\logger.log");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((ID = reader.readLine()) != null) {
				commentaryId = ID;
				//System.out.println(commentaryId);

			}
			reader.close();
		} catch (Exception ex) {

		}


		while (true) {
			try {
				ElementSelector elementSelector = new ElementSelector();
				//System.out.println(commentaryId);
				Date date=new Date();
				System.out.println(date.toString());
				elementSelector.SelectElements(getCommentaryId());
				Thread.sleep(1000 * 15);
			} catch (Exception ex) {

			}
		}

	}

	public String getCommentaryId() {
		return commentaryId;
	}

	public void setCommentaryId(String commentaryId) {
		ThreadedClass.commentaryId = commentaryId;
	}

}
