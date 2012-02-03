A job to enable jenkins to download from chassis.

Configure in /etc/chassis/download/chassi-download.properties
wwarn-live-user=timp
wwarn-live-password=sEcrEt
downloadDirectory=/var/chassis-download/

Ensure that the directory exists and contains a writable directory www.wwarn.org

Run with
mvn install  

