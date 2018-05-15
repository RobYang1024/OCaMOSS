#include<stdlib.h>
#include<stdio.h>
#include<ctype.h>



int 
(int argc, char *argv[])
{
	FILE           *fp;

	int             response, difference, diffimage;

	system("mkdir backup");
	system("mkdir backup/images");
	while (1) {
		response = system("wget -p http://www.cs.rmit.edu./students");


		if ((fp = fopen("./backup/index.html", "r")) == NULL) {
			system("cp www.cs.rmit.edu./students/index.html ./backup");
			system("cp www.cs.rmit.edu./images/*.* ./backup/images");
			system("md5sum ./backup/images/*.* > ./backup/md5sumprior.txt");
			
		} else {
			difference = 0; diffimage = 0;
			difference = system("diff ./backup/index.html www.cs.rmit.edu./students/index.html > data");
			
			system("md5sum www.cs.rmit.edu./images/*.* > ./backup/md5sumlater.txt");
			diffimage = system("diff ./backup/md5sumprior.txt ./backup/md5sumlater.txt > md5diff");

			printf("difference=%d, diffimage=%d",difference,diffimage);
						
			if (difference == 0 && diffimage == 0) {
				printf("\nNo modification\n");
			} else {
				printf("\nModification\n");
				system(" data | mail  @cs.rmit.edu.");
				system(" md5diff | mail  @cs.rmit.edu.");
				
			}
			system("cp www.cs.rmit.edu./students/index.html ./backup");
			system("cp www.cs.rmit.edu./images/*.* ./backup/images");
			system("cp ./backup/md5sumlater.txt ./backup/md5sumprior.txt");
			
		}
		
		sleep(86400);
	}
}
