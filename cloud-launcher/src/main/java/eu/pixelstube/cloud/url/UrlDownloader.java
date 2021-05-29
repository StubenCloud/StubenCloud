package eu.pixelstube.cloud.url;

/*

  » de.thundercloud.launcher.url

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 18.04.2021 / 18:36

 */

import me.tongfei.progressbar.ProgressBar;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlDownloader {

    private final String urlLink;
    private final File file;
    private final DownloadType downloadType;

    public UrlDownloader(String urlLink, File file, DownloadType downloadType) {
        this.urlLink = urlLink;
        this.file = file;
        this.downloadType = downloadType;
    }

    public UrlDownloader(String urlLink, File file) {
        this.urlLink = urlLink;
        this.file = file;
        this.downloadType = DownloadType.UNKOWN;
    }

    public void download(){
        try {
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");

            URL url = new URL(urlLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
            long fileSize = connection.getContentLengthLong();

            ProgressBar progressBar = new ProgressBar(file.getName(), (fileSize / 1000));

            if (downloadType.equals(DownloadType.MINECRAFT_JAR)) {
                progressBar.start();
            }

            byte[] buffer = new byte[1024];
            int read;
            while((read  = bufferedInputStream.read(buffer, 0, 1024)) >= 0){
                bufferedOutputStream.write(buffer, 0, read);

                if (downloadType.equals(DownloadType.MINECRAFT_JAR)) {
                    progressBar.stepBy(read / 1000);
                }

            }

            if (downloadType.equals(DownloadType.MINECRAFT_JAR)) {
                progressBar.stop();
            }

            bufferedOutputStream.close();
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
