package org.cggh.chassis.download;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author timp
 * @since 2012-02-02
 * 
 * A configuration object which expects to find a properties file 
 * either in $APP_HOME/conf or /etc/$APP_NAME.
 * 
 * The main use case is so as not to put passwords in the SCM.
 * 
 * <code>
 *
 *     Configuration config = new Configuration("posql", dbName);
 *     String dbBaseUrl = config.getSetProperty("dbBaseUrl"); // "jdbc:mysql://localhost:3306/";
 *     String driver = config.getSetProperty("driver"); // "com.mysql.jdbc.Driver";
 *     String user = config.getSetProperty("user"); // "root";
 *     String password = config.get("password"); // optional
 * </code>
 * 
 */
public class Configuration {

  private String appName;
  private String homeVariableName;  
  private String configurationDirectoryName; 
  private String propertiesFileName;
  private Properties properties;
  private Properties defaults;
  
  public Configuration(String appName) {
    this(appName, appName);
  }
  public Configuration(String appName, String objectName) {
    this(appName, objectName, null);
  }
  public Configuration(String appName, String objectName, Properties defaults) {
    super();
    this.appName = appName;
    this.defaults = defaults;
    this.homeVariableName = appName.toUpperCase() + "_HOME";
    String envHome = System.getenv(this.homeVariableName);
    if (envHome == null) 
      this.configurationDirectoryName = "/etc/" + appName;
    else 
      this.configurationDirectoryName = envHome + "/conf";
    this.propertiesFileName = this.configurationDirectoryName + "/" + objectName + ".properties";
    File propertiesfile = new File(this.propertiesFileName);
    if (propertiesfile.exists())
      this.properties = fromFile(propertiesfile, defaults);
    else 
      if (defaults != null)
        this.properties = new Properties(defaults);
      else
        throw new RuntimeException(new FileNotFoundException("File " + propertiesFileName + " not found"));
  }

  public String getSetProperty(String key) {
    String s = get(key);
    if (s == null) 
      throw new NullPointerException("Property " + key + " is not set in configuration for " + appName);
    return s;
  }
  
  public String get(String key) {
    return properties.getProperty(key) == null ? null : properties.getProperty(key).trim();
  }  
  public String put(String key, String value) {
    return (String) properties.put(key, value);
  }
  
  public String getFileName() { 
    return propertiesFileName;
  }
  
  public boolean configFileExists() { 
    return new File(getFileName()).exists();
  }
  
  public String toString() { 
    StringBuffer val =  new StringBuffer("Set:\n");
    val.append(properties.toString());
    val.append("\nDefaults:\n");
    val.append(defaults.toString());
    return val.toString();
  }

  /**
   * @param existingFile must exist
   * @param defaults default Properties or null
   */
  public static Properties fromFile(File existingFile, Properties defaults) {
    InputStream data;
    try {
      data = new FileInputStream(existingFile);
    } catch (FileNotFoundException e1) {
      throw new IllegalArgumentException("Path (" + existingFile + ") does not exist");
    }
    Properties them = new Properties(defaults);
    try{
      them.load(data);
    } catch (IOException e) {
      throw new RuntimeException("Corrupt properties file `" + existingFile + "'", e);
    }

    return them;
  }
  
}
