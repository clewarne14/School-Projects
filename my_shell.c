//Charlie LeWarne
//CSCI 444
//March 10, 2021

#include  <stdio.h>
#include  <sys/types.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdbool.h>
#include <sys/wait.h>

#define MAX_INPUT_SIZE 1024
#define MAX_TOKEN_SIZE 64
#define MAX_NUM_TOKENS 64

/* Splits the string by space and returns the array of tokens
*
*/
char **tokenize(char *line)
{
  char **tokens = (char **)malloc(MAX_NUM_TOKENS * sizeof(char *));
  char *token = (char *)malloc(MAX_TOKEN_SIZE * sizeof(char));
  int i, tokenIndex = 0, tokenNo = 0;

  for(i =0; i < strlen(line); i++){

    char readChar = line[i];

    if (readChar == ' ' || readChar == '\n' || readChar == '\t'){
      token[tokenIndex] = '\0';
      if (tokenIndex != 0){
	tokens[tokenNo] = (char*)malloc(MAX_TOKEN_SIZE*sizeof(char));
	strcpy(tokens[tokenNo++], token);
	tokenIndex = 0; 
      }
    } else {
      token[tokenIndex++] = readChar;
    }
  }
 
  free(token);
  tokens[tokenNo] = NULL ;
  return tokens;
}


int main(int argc, char* argv[]) {
	char  line[MAX_INPUT_SIZE];            
	char  **tokens;              
	int i;

	FILE* fp;
	if(argc == 2) {
		fp = fopen(argv[1],"r");
		if(fp < 0) {
			printf("File doesn't exists.");
			return -1;
		}
	}

	while(1) {			
		/* BEGIN: TAKING INPUT */
		bzero(line, sizeof(line));
		if(argc == 2) { // batch mode
			if(fgets(line, sizeof(line), fp) == NULL) { // file reading finished
				break;	
			}
			line[strlen(line) - 1] = '\0';
		} else { // interactive mode
			printf("$ ");
			scanf("%[^\n]", line);
			getchar();
		}
		//printf("Command entered: %s (remove this debug output later)\n", line);
		/* END: TAKING INPUT */

		line[strlen(line)] = '\n'; //terminate with new line
		tokens = tokenize(line);
   
       //do whatever you want with the commands, here we just print them

		char *tokentemp[10] = {"a", NULL, NULL, NULL, NULL, NULL,NULL,NULL,NULL,NULL}; 

		int singleAmpersand = 0;
		int doubleAmpersand = 0;
		int tripleAmpersand = 0;
		int increment = 0;
		int tempBotAmp = -1;
		int j = 0;
		int status = 0;
		int status2;
		int doubleinc = 0;
		int tripleinc = 0;
		int tokensInc = 0;
		int backResult = 0;
		int backPIDs[MAX_TOKEN_SIZE];
		int pidArr[MAX_TOKEN_SIZE];
		int pidArrTrip[MAX_TOKEN_SIZE];
		for(i=0;tokens[i]!=NULL;i++){
			increment++;
		}
		for(i=0;pidArr[i]!=0;i++){
			backResult = waitpid(pidArr[i], &status2, WNOHANG);
			if(backResult > 0){
				printf("%s","Background process is complete\n");
			}
		}
		char **tripleAmpArr;
		for(i=0;tokens[i]!=NULL;i++){
			if(!strcmp(tokens[i], "&")){
				singleAmpersand = 1;
				tokens[i] = NULL;
			}
			if(increment - i > 2){
				if(!strcmp(tokens[i + 1], "&&")){
					for (int l = tempBotAmp;l<i;l++){
						tokentemp[doubleinc]=tokens[l + 1];
						doubleinc++;
					}
					doubleinc=0;
					tempBotAmp = i+1;
					executeFunc(tokentemp,0,pidArr,pidArrTrip,0);
					for(int l=0;tokentemp[l]!=NULL;l++){
						tokentemp[l] = NULL;
					}
					doubleAmpersand = 1;
				} else if(!strcmp(tokens[i + 1], "&&&")){
					for (int l = tempBotAmp;l<i;l++){
						tokentemp[tripleinc]=tokens[l + 1];
						tripleinc++;
					}
					tripleinc=0;
					tempBotAmp = i+1;
					int fc = fork();
					if(fc == 0){
						executeFunc(tokentemp,0,pidArr,pidArrTrip,1);
						_exit(NULL);
						return(0);
					}else{
					}
					for(int l=0;tokentemp[l]!=NULL;l++){
						tokentemp[l] = NULL;
					}
					tripleAmpersand++;
				}
			}
		}
		
		
		if(doubleAmpersand){

			for (int l = tempBotAmp;l<increment;l++){
				tokentemp[doubleinc]=tokens[l + 1]; 					doubleinc++; 				
			}
			doubleinc=0;
			executeFunc(tokentemp,0,pidArr,pidArrTrip,0);
			for(int l=0;tokentemp[l]!=NULL;l++){
				tokentemp[l] = NULL;
			}

			tokentemp[0] = tokens[i - 1];
		}else if(tripleAmpersand){
			for(int l = tempBotAmp;l<increment;l++){
				tokentemp[tripleinc]=tokens[l + 1];
				tripleinc++;
			}
			tripleinc=0;
			executeFunc(tokentemp,0,pidArr, pidArrTrip,1);
			for(int l=0;tokentemp[l]!=NULL;l++){
				tokentemp[l] = NULL;
			}
			status = 0;
			tokentemp[0] = tokens[i - 1];

		}else if(!strcmp(tokens[0],"exit")){
			for(int a = 1; pidArr[a]!=0;a++){
				int status;
				int pid = pidArr[a];
				kill(pid,SIGTERM);
			}
			return 0;
		}else{
			
			executeFunc(tokens, singleAmpersand, pidArr, pidArrTrip, 0);
       		}
		if(tripleAmpersand){
			wait(NULL);
			tripleAmpersand = 0;
		}
		for(i=0;tokens[i]!=NULL;i++){
			free(tokens[i]);
		}
		free(tokens);

	}
	return 0;
}


int executeFunc(char *input[], int singleAmpersand, int *pidArr, int *pidArrTrip, int tripleAmpersand){
	int pid = getpid();
	int fc;
	int status;
	int result;
	int tokensInc = 0;
	
	if(!strcmp(input[0],"cd")){

		if(chdir(input[1]) == -1){
			perror("Shell: Incorrect command");
		}

	} else {
		result = waitpid(pid, &status, WNOHANG);
		fc = fork();
		
	}
	
	if(fc == 0){
			//child process
		if(input[0] == ""){
			_exit(NULL);
		}else if(!strcmp(input[0],"cd")) {			
			
		}else if(!strcmp(input[0],"exit")) {
			
		}else if(execvp(input[0], input) <= 0){
			
			perror("Please input a valid command");
			
			_exit(NULL);
			
		}
		
	} else {
			//parent process
		if(tripleAmpersand){
			for(int i = 0;pidArrTrip[i]!=0;i++){
				tokensInc++;
			}
			pidArrTrip[tokensInc] = pid;
			tokensInc = 0;
		}
		if(!singleAmpersand){
			wait(NULL);
		}else{
			for(int i = 0;pidArr[i]!=0;i++){
				tokensInc++;
			}
			pidArr[tokensInc] = fc;
		}
	}


	return 1;	
}

















