package com.enjoybt.um_downloader;

import com.enjoybt.down.HttpDownloadUtil;
import com.enjoybt.um_downloader.common.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 0. Project  : 화산재해대응시스템
 * 1. Package : um_downloader
 * 2. Comment : 방재기상정보에서 UM 기상장 다운로드 (본사용)
 * 3. Auth  : aiden_shin
 * 4. Date  : 2018-02-09 오전 10:51
 * 5. History :
 * 이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * aiden_shin : 2018-02-09 :            : 신규 개발.
 *
 */
public class DownMachine {

    private static final Logger logger = LoggerFactory.getLogger(DownMachine.class);

    /**
     * 다운로드 요청 API URL
     */
    public static String sUrl = Constants.DOWN_API_URL;

    /**
     * 수치모델 (변경할 필요 X)
     */
    public static String nwp = "r120";

    /**
     * 실행 method
     */
    public static void run(String tmfc, String TEMP_FOLDER, String TARGET_FOLDER) {

        //api 요청해서 임시폴더에 파일다운로드하고 목록 저장
        ArrayList<String> fileList = downUmByApi(tmfc, TEMP_FOLDER);

        if (fileList == null) {
            return;
        }

        //임시폴더의 파일 유효성 체크 후 이동
        if (checkAndMove(fileList, TEMP_FOLDER, TARGET_FOLDER)) {
            logger.info("모든 파일 전송 성공");
            logger.info("완료  파일 경로 : " + TARGET_FOLDER);
        } else {
            logger.info("일부파일 누락 임시폴더에 남아있음");
            logger.info("임시폴더 경로 : " + TEMP_FOLDER);
        }
    }

    /**
     * Down um by api array list.
     *
     * @return the array list
     */
    public static ArrayList<String> downUmByApi(String tmfc, String TEMP_FOLDER) {

        ArrayList<String> fileList = new ArrayList<>();
        String[] subList = {"pres", "unis"};
        String fileName = "";

        for (String aSubList : subList) {

            for (int j = 0; j * 3 < 88; j++) {

                StringBuilder url = new StringBuilder(sUrl);
                url.append("?nwp=").append(nwp);
                url.append("&sub=").append(aSubList);
                url.append("&tmfc=").append(tmfc);
                url.append("&hh_ef=").append(j * 3);
                url.append("&authKey=").append(Constants.AUTH_KEY);

                try {
                    //임시폴더에 일단 다운로드
                    fileName = HttpDownloadUtil.downloadFile(url.toString(), TEMP_FOLDER);
//                        downUM(urlConnection.getInputStream(), TEMP_FOLDER + "/" + fileName);

                    if (fileName.contains("pres")) {
                        fileList.add(fileName);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }

    /**
     * Down um.
     *
     * @param is the is
     * @param fileName the file name
     */
// 임시폴더에 일단 다운로드
    public static void downUM(InputStream is, String fileName) {

        byte[] buf = new byte[1024];

        FileOutputStream fi = null;
        File f;

        try {
            f = new File(fileName);
            fi = new FileOutputStream(f);

            while (is.read(buf, 0, buf.length) != -1) {
                fi.write(buf);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                assert fi != null;
                fi.close();

            } catch (Exception ignored) {
            }
        }

    }

    /**
     * Check and move boolean.
     *
     * @param fileList the file list
     * @return the boolean
     */
    public static boolean checkAndMove(ArrayList<String> fileList, String TEMP_FOLDER, String TARGET_FOLDER) {
        boolean result = true;

        String targetFolder;
        for (String record : fileList) {

            String arr[] = record.split("_");
            String fileName2;

            if (arr[3].equals("pres")) {
                fileName2 = arr[0] + "_" + arr[1] + "_" + arr[2] + "_unis_" + arr[4];
                File f = new File(TEMP_FOLDER + "/" + fileName2);

                if (f.exists()) {
                    logger.info(arr[4] + "파일 검증 완료 파일이동 시작");

                    // 파일 1, 2 모두 경로 이동
                    targetFolder =
                            TARGET_FOLDER + "/r120." + record.substring(25, 33) + ".t" + record
                                    .substring(33, 35) + "z";

                    File tf = new File(targetFolder);

                    if (!tf.exists()) {
                        tf.mkdirs();
                    }

                    fileCopy(TEMP_FOLDER + "/" + record, targetFolder + "/" + record);
                    fileCopy(TEMP_FOLDER + "/" + fileName2, targetFolder + "/" + fileName2);
                } else {
                    logger.info(fileName2 + "파일이 존재하지 않으므로 파일 이동을 하지 않습니다.");
                    result = false;
                }
            }

        }
        return result;
    }

    /**
     * File copy.
     *
     * @param inFileName the in file name
     * @param outFileName the out file name
     */
    public static void fileCopy(String inFileName, String outFileName) {
        try {
            logger.info(inFileName + "복사시작");

            FileInputStream inputStream = new FileInputStream(inFileName);
            FileOutputStream outputStream = new FileOutputStream(outFileName);

            FileChannel fcin = inputStream.getChannel();
            FileChannel fcout = outputStream.getChannel();

            long size = fcin.size();
            fcin.transferTo(0, size, fcout);

            fcout.close();
            fcin.close();

            outputStream.close();
            inputStream.close();

            logger.info(inFileName + "복사 완료");

            //복사후 파일 삭제
            File f = new File(inFileName);
            f.delete();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
