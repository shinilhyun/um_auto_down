package com.enjoybt.um_downloader;

import com.enjoybt.um_downloader.common.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 0. Project  : 화산재해대응시스템
 * 1. Package : um_downloader
 * 2. Comment : 
 * 3. Auth  : aiden_shin
 * 4. Date  : 2018-02-09 오전 10:59
 * 5. History : 
 * 이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * aiden_shin : 2018-02-09 :            : 신규 개발.
 *
 */
public class FileListView {

    public static String sUrl = Constants.LIST_API_URL;
    public static String nwp = "r120";
    public static String sub = "pres";
    public static String tmfc = "20180103";



    public static void main(String[] args) {

        URL url = null;

        URLConnection urlConnection = null;

        // URL 주소


        StringBuffer param = new StringBuffer();
        param.append("nwp=" + nwp);
        param.append("&sub=" + sub);
        param.append("&tmfc=" + tmfc);
        param.append("&authKey=" + Constants.AUTH_KEY);

        try {

            // Get방식으로 전송 하기

            url = new URL(sUrl + "?" + param.toString());

            urlConnection = url.openConnection();

            printByInputStream(urlConnection.getInputStream());

            // Post방식으로 전송 하기

            /*url = new URL(sUrl);

            urlConnection = url.openConnection();

            urlConnection.setDoOutput(true);

            printByOutputStream(urlConnection.getOutputStream(), paramName + "=" + paramValue);

            printByInputStream(urlConnection.getInputStream());*/


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // 웹 서버로 부터 받은 웹 페이지 결과를 콘솔에 출력하는 메소드

    public static void printByInputStream(InputStream is) {

        byte[] buf = new byte[1024];

        int len = -1;

        try {

            while ((len = is.read(buf, 0, buf.length)) != -1) {

                System.out.write(buf, 0, len);

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    // 웹 서버로 파라미터명과 값의 쌍을 전송하는 메소드
    public static void printByOutputStream(OutputStream os, String msg) {

        try {

            byte[] msgBuf = msg.getBytes("UTF-8");

            os.write(msgBuf, 0, msgBuf.length);

            os.flush();



        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
