package KarChat.Chat.Sound;

import java.io.*;

public class CopyMP3FileTool {

    public static void main(String[] args) {

        String inputPath = "E:\\";

        String outputPath = "D:\\JayChou\\";

        File file = new File(inputPath);

        copyFile(file, outputPath);

    }

    public static void copyFile(File file, String outputPath){

        if (file.isDirectory()) {

            File[] fileList = file.listFiles();

            if (fileList != null && fileList.length > 0){

                for (File file1: fileList) {

                    copyFile(file1, outputPath);

                }

            }

        } else if (file.isFile() && file.getName().endsWith(".mp3")) {

            BufferedInputStream bis = null;

            BufferedOutputStream bos = null;

            File outputFile = new File(outputPath + file.getName());

            boolean exists = outputFile.exists();

            try {

                bis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));

                bos = new BufferedOutputStream(new FileOutputStream(outputPath + file.getName()));

                int len = 0;

                byte[] bytes = new byte[2048];

                while ((len = bis.read(bytes)) != -1) {

                    bos.write(bytes, 0, len);

                }

                bos.flush();

                if (exists){

                    System.out.println(file.getName() + " is already exists will overwritten to " + outputPath);

                }else {

                    System.out.println(file.getName() + " is already copied to " + outputPath);

                }

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }finally {

                if (bis != null){

                    try {

                        bis.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

                if (bos != null){

                    try {

                        bos.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            }

        }

    }

}
