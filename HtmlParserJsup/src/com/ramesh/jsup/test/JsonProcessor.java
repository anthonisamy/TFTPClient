package com.ramesh.jsup.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

import javax.json.*;
import javax.json.stream.JsonGenerator;

public class JsonProcessor {
	public void makeJson(String time, String Commentary) {
		try {
 Date date=new Date();
 String dateString=date.toString();
			File file = new File("G:\\semanticWebProject\\frankfurtvslever.json");
			// BufferedWriter writer=new BufferedWriter(new
			// OutputStreamWriter(new FileOutputStream(file)));
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(file, true)));
			JsonGenerator jsonGenerator = Json.createGeneratorFactory(null)
					.createGenerator(out);
			jsonGenerator.writeStartObject();
			jsonGenerator.write("TIME", time);
			jsonGenerator.write("COMMENTARY", Commentary);
			jsonGenerator.write("TAKEN",dateString);
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
			jsonGenerator.close();
			// writer.close();
			out.close();

		} catch (Exception ex) {

		}

	}

}
