#include "list_functions.h"

#if DYNAMIC_ARRAY == 1

List *new_list() {
    List *l = calloc(1, sizeof(List));

    l->capacity = 10;
    l->array = calloc(l->capacity, sizeof(void *));
    l->size = 0;

    return l;
}

void del_list(List *l) {
    if (l == NULL)
        return;

    free(l->array);
    free(l);
}

bool is_list_empty(List *array) {

    return array == NULL || array->size == 0;
}

size_t get_list_size(List *array) {
    return array == NULL ? 0 : array->size;
}

//function used to check if it's necessary increase the size of the array to add a new element
void increase_list_size(List *array) {
    if (array && array->size == array->capacity) {
        array->capacity *= 2;
        array->array = realloc(array->array, sizeof(void *) * array->capacity);
    }
}

//function used to check if it's convenient reduce the size of the array
void reduce_list_size(List *array) {
    if (array && array->size <= (array->capacity / 4)) {
        array->capacity /= 2;
        array->array = realloc(array->array, sizeof(void *) * array->capacity);
    }
}

void add_elem_list_bottom(List *array, void *element) {
    if (array == NULL) {
        return;
    }

    size_t i;
    increase_list_size(array);

    i = get_list_size(array);
    array->array[i] = element;
    array->size += 1;
}

void add_elem_list_index(List *list, void *element, int index) {
    if (list == NULL || is_list_empty(list) || index < 0 || index > list->size - 1) {
        return;
    }

    size_t i;

    increase_list_size(list);

    for (i = get_list_size(list); i > index; --i) {
        list->array[i] = list->array[i - 1];
    }

    list->array[index] = element;

    list->size += 1;

}

void delete_list_elem_bottom(List *array) {
    if (array == NULL || is_list_empty(array)) {
        return;
    }

    array->size -= 1;
    reduce_list_size(array);
}

void delete_list_elem_index(List *array, int index) {
    if (array == NULL || is_list_empty(array) || index<0 || index >= array->size) {
        return;
    }

    size_t size;

    reduce_list_size(array);

    for (size = get_list_size(array); index < size; index++) {
        array->array[index] = array->array[index + 1];
    }

    array->size -= 1;
}

void *get_list_elem_index(List *array, int index) {
    return (array && array->array && index>=0 && index < array->size) ? array->array[index] : NULL;
}

#elif DYNAMIC_ARRAY == 0

List *new_list() {
    List *l = calloc(1, sizeof(List));

    l->size = 0;
    l->head = l->tail = NULL;

    return l;
}


void del_list(List *list) {
    if (!list) {
        return;
    }

    DllElem *current = list->head;
    DllElem *next;

    while (current) {
        next = current->next;
        free(current);
        current = next;
    }

    free(list);
}

DllElem *build_dll_elem(void *value, DllElem *next, DllElem *prev) {
    DllElem *e = malloc(sizeof(DllElem));
    e->value = value;
    e->next = next;
    e->prev = prev;

    return e;
}

bool is_list_empty(List *list) {
    return (list == NULL || !(list->head));
}

size_t get_list_size(List *list) {
    return (list != NULL) ? list->size : 0;
}

void add_elem_list_bottom(List *list, void *element) {
    if (list == NULL) {
        return;
    }

    DllElem *e = build_dll_elem(element, NULL, list->tail);

    if (is_list_empty(list))
        list->head = e;
    else
        list->tail->next = e;
    list->tail = e;
    list->size++;
}

/*
 * This method deletes the element at the indicated index.
 * If the index is greater than the size of the List, no element is removed.
 */
void add_elem_list_index(List *list, void *element, int index) {
    //!list is clearer way but it is already checked inside get_list_size
    if (!list || index < 0 || index > get_list_size(list)) {
        return;
    }

// support element is a temporary pointer which avoids losing data
    DllElem *e;
    DllElem *supp = list->head;

    for (int i = 0; i < index; ++i)
        supp = supp->next;

    if (supp == list->head) {
        e = build_dll_elem(element, supp, NULL);

        if (supp == NULL) {
            list->head = list->tail = e;
        } else {
//        e->next->prev = e;
            list->head->prev = e;
            list->head = e;
        }
    } else {
        if (supp == NULL) {
            e = build_dll_elem(element, NULL, list->tail);
            list->tail->next = e;
        } else {
            e = build_dll_elem(element, supp, supp->prev);
            e->next->prev = e;
            e->prev->next = e;
        }
    }

    list->size++;
}

/*
 * This method deletes the element at the bottom of the List.
 */
void delete_list_elem_bottom(List *list) {

    if (list == NULL || is_list_empty(list)) {
        return;
    }

    DllElem *oldTail = list->tail;

    list->tail = oldTail->prev;
    list->tail->next = NULL;

    free(oldTail);
    list->size--;
}

/*
 * This method iteratively finds and deletes the element at the specified index, but only if it doesn't exceed
 * the size of the List. In this case, instead, no reference gets deleted.
 */
void delete_list_elem_index(List *list, int index) {
    //!list is clearer but it is already checked inside get_list_size
    if (list == NULL || is_list_empty(list) || index < 0 || index >= get_list_size(list)) {
        return;
    }

    DllElem *oldElem;
    oldElem = list->head;

    for (int i = 0; i < index; ++i)
        oldElem = oldElem->next;

    // Found index  to remove!!
    if (oldElem != list->head)

        oldElem->prev->next = oldElem->next;
        oldElem->next->prev = oldElem->prev;
    else
        list->head = list->head->next;

    free(oldElem);
    list->size--;
}

/*
 * This method iteratively runs through the dllist elements and returns the one at the requested index.
 * If the index exceeds the size of the List, we instead return no element.
 */
void *get_list_elem_index(List *list, int index) {
    if (list == NULL || index < 0 || index >= get_list_size(list)) {
        return NULL;
    }

    DllElem *supp; // iteration support element
    supp = list->head;

    for (int i = 0; i < index; ++i)
        supp = supp->next;
    return supp->value;
}

#else
#error Please choose between DYNAMIC_ARRAY (0) or LINKED LIST (1).
#endif