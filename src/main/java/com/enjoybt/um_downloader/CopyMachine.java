package com.enjoybt.um_downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class CopyMachine {

    public static String target_path = "F:/화산 작업/171109_vdrs/행망에서 jbt/kma2volcano";
    
    public static String root_path = "D:/세종시준비/um";
    
    
    
    public void run() {
        File dir = new File(root_path);
        System.out.println("sourcePath="+root_path);
        
        File[] fileList = dir.listFiles();
        
        ArrayList<File> files = new ArrayList<File>();
        try {
            visitAllFiles(files, dir);
            
            for (int i = 0, len = files.size(); i < len; i++) {

                File file = files.get(i);
                String fileName = file.getName();
                
                String outFilePath = target_path + "/r120." + fileName.substring(25, 33) + ".t" + fileName.substring(33, 35) + "z";
                String outFileName = outFilePath + "/" + fileName;
                
                File targetFolder = new File(outFilePath);
                
                if (!targetFolder.exists()) {
                    targetFolder.mkdirs();
                    System.out.println("폴더생성");
                }
                
                fileCopy(file.getCanonicalPath(), outFileName);
                
            }
            
        }catch (Exception e) {
            // TODO: handle exception
        }

    }
    
    public static void visitAllFiles(ArrayList<File> files, File dir) throws IOException {
        
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File f : children) {
                // 재귀 호출 사용
                // 하위 폴더 탐색 부분
                visitAllFiles(files, f);
            }
        } else {
            if (dir.isFile() && dir.getName().contains("r120_v070")) {
                files.add(dir);
                System.out.println(dir.getCanonicalPath().toString());
            }
        }
    }
    
    public static void fileCopy(String inFileName, String outFileName) {
        try {
            System.out.println(inFileName + "복사시작");
                
            FileInputStream inputStream = new FileInputStream(inFileName);
            FileOutputStream outputStream = new FileOutputStream(outFileName);
              
            FileChannel fcin =  inputStream.getChannel();
            FileChannel fcout = outputStream.getChannel();
              
            long size = fcin.size();
            fcin.transferTo(0, size, fcout);
              
            fcout.close();
            fcin.close();
              
            outputStream.close();
            inputStream.close();

         System.out.println(inFileName + "복사시작 완료");
        } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
       }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("UM DownLoad----");
        CopyMachine t = new CopyMachine();
        t.run();
      
        
    }
    

}
