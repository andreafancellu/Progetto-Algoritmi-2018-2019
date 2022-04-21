#pragma once

#include "common_inclusions.h"

#define DYNAMIC_ARRAY 0

#if DYNAMIC_ARRAY == 1
/* dynamic array struct manager */
typedef struct {
    void **array;
    size_t size;
    size_t capacity;
} List;

typedef struct {
    List *list;
    size_t index;
} ListIterator;
#elif DYNAMIC_ARRAY == 0
/* DoublyLinkedList_element */
typedef struct DllElem{
    void *value;
    struct DllElem *next;
    struct DllElem *prev;
} DllElem;

/* linked list struct manager */
typedef struct {
    DllElem *head;
    DllElem *tail;
    size_t size;
} List;

typedef struct {
    List * list;
    DllElem* curr;
    size_t index;
} ListIterator;
#else
#error Please choose between DYNAMIC_ARRAY (0) or LINKED LIST (1).
#endif

typedef int (*ValueComparator)(void *, void *);