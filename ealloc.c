//Charlie LeWarne
//CSCI 444
//April 14, 2021
//This program is being weird with the tests that were given
//I wrote my own tests in the main method to show what it does.

#include "ealloc.h"
#include <stdio.h>




void init_alloc(){
	head1 = mmap(NULL,4096,PROT_READ | PROT_WRITE, MAP_ANON |MAP_SHARED,0,0);
    firstAlloc = 1;
    numPages = 0;
    memInPage = 0;
	
}

char *alloc(int input){
	//struct memAdd memTemp =;
	if(input % 256){
		printf("Invalid input: Please input a number divisible by 256");
		return;
	}
	if(firstAlloc){
		char *output = mmap(NULL,4096,PROT_READ | PROT_WRITE, MAP_ANON |MAP_SHARED,0,0);
		firstAlloc = 0;
        head1->size = 4096;
        head1->next = NULL;
        head1->free = 1;
        head1->prev = NULL;
        numPages = 1;

	}
    memInPage += input;
	memNode * tempLast = head1;
	while((tempLast->free == 0 || tempLast->size < input || memInPage == 4096)){
        if(tempLast->next == NULL){
			if(tempLast->size > input){
				break;
			} else {
                if(memInPage == 4096){
                    tempLast->size = input;
                    tempLast->free = 0;
                    memInPage = input;
                }
				printf("ALLOCATING NEW PAGE\n");
                if(numPages == 1){
				    head2 = mmap(NULL,4096,PROT_READ | PROT_WRITE, MAP_ANON |MAP_SHARED,0,0);
				    head2->size = 4096;
				    head2->next = NULL;
                    head2->prev = tempLast;
				    head2->free = 1;
				    tempLast->next = head2;
				    tempLast = tempLast->next;
                }
                if(numPages == 2){
				    head3 = mmap(NULL,4096,PROT_READ | PROT_WRITE, MAP_ANON |MAP_SHARED,0,0);
				    head3->size = 4096;
				    head3->next = NULL;
                    head3->prev = tempLast;
				    head3->free = 1;
				    tempLast->next = head3;
				    tempLast = tempLast->next;
                }
                if(numPages == 3){
				    head4 = mmap(NULL,4096,PROT_READ | PROT_WRITE, MAP_ANON |MAP_SHARED,0,0);
				    head4->size = 4096;
				    head4->next = NULL;
                    head4->prev = tempLast;
				    head4->free = 1;
				    tempLast->next = head4;
				    tempLast = tempLast->next;
                }
                numPages++;
				break;
			}
		}
		tempLast = tempLast->next;
	} 
    if(tempLast->next != NULL){
        if(tempLast->size == input){
            tempLast->free = 0;
            return tempLast;
        } else {
            memNode *newOpen = tempLast + (input)/32;
            newOpen->free = 1;
            newOpen->next = tempLast->next;
            newOpen->prev = tempLast;
            newOpen->size = tempLast->size - input;
            tempLast->free = 0;
            tempLast->next = newOpen;
            tempLast->size = input;
            return tempLast;
        }
    }
	int tempLen = tempLast->size;
	
	tempLast->size = input;
    //The way C rounds casts from a double to an int would cause input/24 to be under 512. 
    //With (input + 24)/24, it is larger than 512, 
    //but you said that we would rather have it be over than under so I went with that 
	memNode * nextArr = tempLast + (input)/32;
    //memNode * nextArr = tempLast + 2/24;
	//nextArr += ((input + 12)/sizeof(int));
	nextArr->size = tempLen - input;
	nextArr->next = NULL;
    nextArr->prev = tempLast;
	nextArr->free = 1;
	//tempLast->size = input;
	tempLast->next = nextArr;
	tempLast->free = 0;
	
	//char *output = mmap(tempLast + 12 + tempLast->size, input, PROT_READ | PROT_WRITE, MAP_ANON |MAP_SHARED,0,0);
	return (char *)tempLast;
		
	//printf("%d\n",firstAlloc);
	
	//struct memAdd = {input, 
	//char output[input];
	//void *output = mmap(NULL,input,PROT_READ | PROT_WRITE, MAP_ANON |MAP_SHARED,0,0);
	//return output;
}

void dealloc(char *memAdd){
	memNode * temp = head1;
	while(memAdd != temp){
		temp = temp->next;
	}
    memNode *tempPrev = head1;
    while(tempPrev->next != temp){
        tempPrev = tempPrev->next;
    }
    if(tempPrev->free){
        memNode *newFree = tempPrev;
        newFree->free = 1;
        newFree->next = temp->next;
        newFree->size = tempPrev->size + temp->size;
        if(tempPrev = head1){
            newFree->prev = NULL;
        } else {
            newFree->prev = tempPrev->prev;
            temp->prev->next = newFree;
        }
    }
	temp->free = 1;
	if(temp->next->free){
		memNode * newFree = temp;
        newFree->free = 1;
        newFree->size = temp->size + temp->next->size;
        newFree->next = temp->next->next;
        temp->prev->next = newFree;
       // newFree->size = temp->size + temp->next->size;
	}
}

void cleanup(){
    //Simple if statements because number of pages should never exceed 4
    //according to the directions.
    if(numPages >= 1){
        munmap(head1,4096);
    }
    if(numPages >= 2){
        munmap(head2,4096);
    }
    if(numPages >= 3){
        munmap(head3,4096);
    }
    if(numPages == 4){
        munmap(head4,4096);
    }
    return;
}


void main(){
	init_alloc();
   // char *t = alloc(4096);
   // char *r = alloc(4096);
	char *a = alloc(256);
    printf("Address of the start of the page:\n%p\n\n",head1);
    char *b = alloc(512);
    printf("Size of first and second alloc:\n%d\n%d\n\n",head1->size,head1->next->size);
	char *c = alloc(256);
    char *d = alloc(256);
    printf("The second and third allocations will be deallocated and coalesced into one free space:\n");
    dealloc(b);
    dealloc(c);
    printf("Size of the deallocated chunk: %d\nOutput of a check that it is free: %d\n\n",head1->next->size,head1->next->free);
    printf("A new chunk will now be allocated into the space that was just deallocated: \n");
    char *e = alloc(512);
    printf("Size of the allocated chunk: %d\nOutput of a check that it is free: %d\n\n",head1->next->size,head1->next->free);
    printf("A new allocation of 4096 will be done. This will cause a new page to be mmaped:\n");
    char *f = alloc(4096);
    printf("Here is the address for the start of the page: %p\nThis is the size of the new page, it is all taken up by the 4096 allocation: %d\n\n",head2,head2->size);
    printf("The cleanup function will now be called:\n");
    cleanup();


}