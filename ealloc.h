#include <stdio.h>
#include <string.h>
#include <sys/mman.h>
#include <unistd.h>
#include <stdlib.h>

//granularity of memory to mmap from OS
#define PAGESIZE 4096 

//minimum allocation size
#define MINALLOC 256

#define HEADER 16
// function declarations to support



typedef struct memNode {
	int size;
	struct memNode * next;
	struct memNode * prev;
	int free;
} memNode;

memNode * head1;
memNode * head2;
memNode * head3;
memNode * head4;
int numPages;
int memInPage;
int firstAlloc;
void init_alloc();
char *alloc(int);
void dealloc(char *);
void cleanup();
void main();

