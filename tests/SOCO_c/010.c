

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

int () {

 while (1) { 

    system("wget -p www.cs.rmit.edu.");\

    system("mkdir sitefiles");

    system("cp www.cs.rmit.edu./index.html sitefiles");
    system("diff sitefiles/index.html www.cs.rmit.edu./index.html | mail @cs.rmit.edu.");

    system("md5sum www.cs.rmit.edu./images/*.* > imageInfo.txt");
    system("diff imageInfo.txt sitefiles/imageInfo.txt | mail @cs.rmit.edu.");

    system("cp imageInfo.txt sitefiles");
    sleep(86400);
  }
 
}
