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
  private String linkedStudiesFileName;

  public WWARNLiveChassisRepository() {
    super();
    this.configuration = new Configuration("chassis-download");
    downloadDirectory = configuration.getSetProperty("downloadDirectory");
    studiesFileName = downloadDirectory + "/www.wwarn.org/" + "studies.xml";
    linkedStudiesFileName = downloadDirectory + "/www.wwarn.org/" + "links.xml";
  }

  public void downloadFeeds() throws Exception {
    String wwarnUser = configuration.getSetProperty("wwarn-live-user");
    String wwarnPassword = configuration.getSetProperty("wwarn-live-password");
    
    CasProtectedResourceDownloaderFactory.downloaders.put(
            CasProtectedResourceDownloaderFactory.keyFromUrl("https://www.wwarn.org/"),
            new CasProtectedResourceDownloader("https://", "www.wwarn.org:443",
                    wwarnUser, wwarnPassword, "/tmp"));
    

    File studiesOut = new File(studiesFileName);
    studiesOut.delete();
    String studyFeedUrl = "https://www.wwarn.org/repository/service/content/studies";

    CasProtectedResourceDownloader downloader = CasProtectedResourceDownloaderFactory
            .getCasProtectedResourceDownloader(studyFeedUrl);
    downloader.downloadUrlToFile(studyFeedUrl, studiesOut);
    
    File linksOut = new File(linkedStudiesFileName);
    String linkedStudiesFeedUrl = "https://www.wwarn.org/repository/service/content/link";
    downloader = CasProtectedResourceDownloaderFactory
            .getCasProtectedResourceDownloader(linkedStudiesFeedUrl);
    downloader.downloadUrlToFile(linkedStudiesFeedUrl, linksOut);

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
