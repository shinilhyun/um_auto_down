package com.enjoybt.um_downloader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 0. Project  : 화산재해대응시스템
 * 1. Package : um_downloader
 * 2. Comment : 
 * 3. Auth  : aiden_shin
 * 4. Date  : 2018-05-15 오후 1:17
 * 5. History : 
 * 이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * aiden_shin : 2018-05-15 :            : 신규 개발.
 *
 */
public class AutoDownUm {

    @Value("${temp.folder}")
    String tempFolder;

    @Value("${target.folder}")
    String targetFolder;

    // 매일 22시에 실행
    @Scheduled(cron = "0 0 22 * * *")
    public void run() {

        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String now = df.format(d);

        String[] timeList = {
                now + "00",
                now + "06",
                now + "12",
                now + "18"
        };

        DownMachine down = new DownMachine();

        for (String time: timeList) {
            down.run(time, tempFolder,targetFolder);
        }

    }

}
