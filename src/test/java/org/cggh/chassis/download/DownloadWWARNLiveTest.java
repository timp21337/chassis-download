/**
 * 
 */
package org.cggh.chassis.download;

import java.io.File;
import java.util.Date;


import junit.framework.TestCase;

/**
 * @author timp
 * @since 3 Feb 2012
 *
 */
public class DownloadWWARNLiveTest extends TestCase {

  public DownloadWWARNLiveTest(String name) {
    super(name);
  }

  public void testDownloadLiveWwarnFeeds() throws Exception {
      WWARNLiveChassisRepository repo = new WWARNLiveChassisRepository();
      
      Date start = new Date();
      System.err.println("Start:" + start);
      
      repo.downloadFeeds();
      assertTrue(new File(repo.getStudiesFileName()).exists());

      Date  end = new Date();
      System.err.println("End:" + end);
      long diff = end.getTime() - start.getTime();
      System.err.println("Elapsed:" + diff/1000 );
    }
   
}
