#include<stdio.h>
#include<stdlib.h>
#include<string.h>
()
{
FILE *pfile1;
int i,j;
i=1;
j=0;
char flag[3];
strcpy(flag,"");

while (i>0)
{
system("wget -p http://www.cs.rmit.edu./students/");
pfile1=fopen(" ./www.cs.rmit.edu./.txt","w");

system("mkdir ");


system("md5sum ./www.cs.rmit.edu./images/*.* > ./www.cs.rmit.edu./.txt ");


fclose(pfile1);


if (strcmp(flag,"yes")!=0)
{

system(" mv ./www.cs.rmit.edu./.txt ./.txt");
system(" cp ./www.cs.rmit.edu./students/index.html ./");

}


if (strcmp(flag,"yes")==0)
{


system("diff  ./www.cs.rmit.edu./students/index.html  .//index.html | mail @cs.rmit.edu. ");
system("diff  ./www.cs.rmit.edu./.txt  ./.txt | mail @cs.rmit.edu. ");

system(" mv ./www.cs.rmit.edu./.txt ./.txt");

}

sleep(86400);
j++;
strcpy(flag,"yes");
}

}
