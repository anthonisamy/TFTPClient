package com.ramesh.jsup.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElementSelector {
	public static Logger logger = null;
	private String holdme;
	private String time;
	private String comment;
	private String valueOfId;
	private String idFromWeb;

	public void SelectElements(String holdMeId) {
		try {

			ThreadedClass threadedClass = new ThreadedClass();
			IdComparison compareme = new IdComparison();
			logger = Logger.getRootLogger();
			BasicConfigurator.configure();
			PatternLayout layout = new PatternLayout();
			String conversionPattern = "[%p] %d %c %M - %m%n";
			layout.setConversionPattern(conversionPattern);

			// creates daily rolling file appender
			DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
			rollingAppender.setFile("G:\\semanticWebProject\\app.log");
			rollingAppender.setDatePattern("yyyy-MM-dd");
			rollingAppender.setLayout(layout);
			rollingAppender.activateOptions();
			logger.addAppender(rollingAppender);
			/*
			 * logger.debug("this is a debug log message");
			 * logger.info("this is a information log message");
			 * logger.warn("this is a warning log message");
			 */
// For Live Get
			//live connect
			 //Document doc = Jsoup .connect("http://www1.skysports.com/football/live/match/316481") .get();
			 
			File myfile = new File(
					"G:\\semanticWebProject\\bundusligalive.html");
			Document doc = Jsoup.parse(myfile, "ISO-8859-1");
			Elements commentary = doc.getElementsByAttributeValue("id",
					"live-commentary");
			Elements listsofitems = commentary.select("ol");
			Elements lll = listsofitems.select("li");
			/*
			 * Elements emm = lll.select("em"); Elements pp = lll.select("p");
			 * 
			 * Element first = listsofitems.select("li").first();
			 */
			// logger.info("your message displayed");
			// logger.info(first);logger.info(first1);
			// holdme = holdMeId;
			Element listFirstItem = listsofitems.select("li").first();
			idFromWeb = listFirstItem.attr("id").toString();
			// System.out.println(listFirstItem.toString());
			// idToCheck = holdMeId;

			if (compareme.CompareId(idFromWeb, holdMeId) == false) {
				for (Element lisElement : lll) {
					holdme = compareme.makeid(holdMeId);
					Element test = listsofitems.select("li[id=" + holdme + "]")
							.first();
					valueOfId = test.attr("id").toString();

					File file = new File("G:\\semanticWebProject\\logger.log");
					BufferedWriter writer = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(file)));
					writer.write(valueOfId);
					time = test.select("em").text();
					comment = test.select("p").text();

					/*
					 * logger.info("time:" + time + "\t Commentary:" + comment +
					 * "ID VALUE TO TEST:" + holdme + "VALUE OF ID:" +
					 * valueOfId);
					 */
					JsumImplement.blockingQueue.put(comment);
					System.out.println("time:" + time + "\t Commentary:"
							+ comment + "ID VALUE TO TEST:" + holdme
							+ "VALUE OF ID:" + valueOfId);
					JsonProcessor jsonProcessor = new JsonProcessor();
					jsonProcessor.makeJson(time, comment);
					holdMeId = valueOfId;
					writer.close();
					threadedClass.setCommentaryId(valueOfId);
					
				}

				System.out.println("your message displayed");
			}
			// Just to test whether program is working or not.

			// holdme = holdMeId;
			// for (Element listitem : lll) {

			// System.out.println(comm.select("ol li p").text());
			// logger.info(comm.select("ol li p").text());
			// System.out.println(comm.select("ol li em").text());
			// logger.info(listitem.select("em").text()+"MINS\t "+"COMMENT:"+listitem.select("p").
			// text()+"\tSITUATION:"+ listitem.select("p b").text());
			else {
				holdme = compareme.makeid(holdMeId);
				Element test = listsofitems.select("li[id=" + holdme + "]")
						.first();
				valueOfId = test.attr("id").toString();

				File file = new File("G:\\semanticWebProject\\logger.log");
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(file)));
				writer.write(valueOfId);
				time = test.select("em").text();
				comment = test.select("p").text();
				threadedClass.setCommentaryId(valueOfId);

				/*
				 * logger.info("time:" + time + "\t Commentary:" + comment +
				 * "ID VALUE TO TEST:" + holdme + "VALUE OF ID:" + valueOfId);
				 */

				writer.close();

				// }
				System.out.println("time:" + time + "\t Commentary:" + comment
						+ "ID VALUE TO TEST:" + holdme + "VALUE OF ID:"
						+ valueOfId);
				JsonProcessor jsonProcessor = new JsonProcessor();
				jsonProcessor.makeJson(time, comment);
			}
		} catch (Exception ex) {
			String msg = ex.getMessage();

		}
	}
}
