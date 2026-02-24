
__asm__(".code16gcc");

#include "history.h"
#include "allocator.h"

static unsigned int max_size, cur_size;
static char_t *ll_start;

void history_init(int size){

    max_size = size;
    cur_size = 0;
    ll_start = NULL;
}

/*
 * Puts a character into the history buffer. The buffer
 * should be implemented as a FIFO queue.
 *
 * There is only a single consumer and producer, and hence
 * no race conditions, allowing for a very simple
 * implementation without locks.
 */

void history_put(char s)
{
    // We don't want s to be \r :)
    s = (s == '\r') ? '\n' : s;

    /* Special case where buffer is empty */
    if(ll_start == NULL)
    {
        /* Creating the first character */
        ll_start = kzalloc(sizeof(char_t));
        ll_start->s = s;
        ll_start->next = NULL;
        ll_start->prev = NULL;

        cur_size++;
        return;
    }

    /* Advancing until we find an empty node (no memory stuff for now)*/
    char_t* currentNode = ll_start;
    while(currentNode->next != NULL) {currentNode = currentNode->next;}

    /* Special case where buffer is full */
    if(cur_size == max_size)
    {
        /* We want to replace the last element
           and it's easier to just replace the
           value. */

        /* Replacing the value in the last node
           without changing any pointers */
        currentNode->s = s;

        return;
    }

    /* We now have an empty slot in currentNode -> next*/
    char_t* nextNode = kzalloc(sizeof(char_t));
    nextNode->s = s;
    nextNode->prev = currentNode;
    nextNode->next = NULL;
    currentNode->next = nextNode;
    cur_size++;
}

void history_write(void) 
{
    // Defining an empty string and an iterator
    char* str = kzalloc(sizeof(char) * cur_size + 1); int i = 0;

    // Defining a node to iterate over the history
    char_t* currentNode = ll_start;
    do 
    {
        *(str + i++) = currentNode->s;
    }
    while((currentNode = currentNode->next) != NULL);

    *(str + i) = '\0'; // Unneccesary, but for good measure

    write_line(str);
}