
package com.enjoybt.down;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NwpFileDownApp {

	private static final String API_URL = "http://203.247.66.28/url/nwp_file_down.php?nwp=r120&sub=pres&hh_ef=0&authKey=088da07b5d70ef0fc60b226a1a5ce0224f2e40e600851c2b7fc4fa57283173d01121ca2eb049413ead2e8684f878d0aaa20fe420aafcedef0109438d434a84a9&tmfc=";

	private static final String DOWN_PATH = "C:/Downloads";
	//private static final String DOWN_PATH = "\\\\192.168.0.151\\g$\\data\\unist\\RDAPS\\2015";

	public static void main(String[] args) {

		String curDate = "2018051000";

		String endDate = "2018051400";

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");

		while (Integer.parseInt(curDate) <= Integer.parseInt(endDate)) {

			String strUrl = API_URL + curDate;

			try {
				HttpDownloadUtil.downloadFile(strUrl, DOWN_PATH);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Date date = null;
			try {
				date = dateFormat.parse(curDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);

			curDate = dateFormat.format(cal.getTime());
		}
	}

}
