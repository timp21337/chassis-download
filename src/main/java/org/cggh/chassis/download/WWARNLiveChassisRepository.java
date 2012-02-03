/**
 * 
 */
package org.cggh.chassis.download;

import java.io.File;

import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.CasProtectedResourceDownloaderFactory;


/**
 * @author timp
 * @since 3 Feb 2012
 * 
 */
public class WWARNLiveChassisRepository {

  private Configuration configuration = null;
  private String downloadDirectory;
  private String studiesFileName;

  public WWARNLiveChassisRepository() {
    super();
    this.configuration = new Configuration("chassis-download");
    downloadDirectory = configuration.getSetProperty("downloadDirectory");
    studiesFileName = downloadDirectory + "/www.wwarn.org/" + "studies.xml";
  }

  public void downloadFeeds() throws Exception {
    String wwarnUser = configuration.getSetProperty("wwarn-live-user");
    String wwarnPassword = configuration.getSetProperty("wwarn-live-password");
    
    CasProtectedResourceDownloaderFactory.downloaders.put(
            CasProtectedResourceDownloaderFactory.keyFromUrl("https://www.wwarn.org/"),
            new CasProtectedResourceDownloader("https://", "www.wwarn.org:443",
                    wwarnUser, wwarnPassword, "/tmp"));
    

    File studyOut = new File(studiesFileName);
    studyOut.delete();
    String studyFeedUrl = "https://www.wwarn.org/repository/service/content/studies";

    CasProtectedResourceDownloader downloader = CasProtectedResourceDownloaderFactory
            .getCasProtectedResourceDownloader(studyFeedUrl);
    downloader.downloadUrlToFile(studyFeedUrl, studyOut);

  }

  public String getDownloadDirectory() {
    return downloadDirectory;
  }

  public String getStudiesFileName() {
    return studiesFileName;
  }

  public static void main(String[] args) throws Exception {
    new WWARNLiveChassisRepository().downloadFeeds();
  }
  
}
