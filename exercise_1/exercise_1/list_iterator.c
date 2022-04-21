#include "list_iterator.h"

#if DYNAMIC_ARRAY == 1

ListIterator *create_iterator(List *list) {
    if(!list)
        return NULL;

    ListIterator *newIterator = malloc(sizeof(ListIterator));
    newIterator->index = 0;
    newIterator->list = list;
    return newIterator;
}

void *get_current_iterator_element(ListIterator *iterator) {
    if(is_iterator_valid(iterator)) {
        return get_list_elem_index(iterator->list, iterator->index);
    }
    else{
        return NULL;
    }
}

void iterator_next(ListIterator *iterator) {
    if (is_iterator_valid(iterator)) {
        iterator->index++;
    }
}

#elif DYNAMIC_ARRAY == 0

ListIterator *create_iterator(List *list) {
    if(!list)
        return NULL;

    ListIterator *new_iterator = malloc(sizeof(ListIterator));
    new_iterator->list = list;
    new_iterator->curr = new_iterator->list->head;
    new_iterator->index = 0;
    return new_iterator;
}

void *get_current_iterator_element(ListIterator *iterator) {
    return (iterator && iterator->curr && iterator->curr->value) ? iterator->curr->value : NULL;
}

void iterator_next(ListIterator *iterator) {
    if (is_iterator_valid(iterator)) {
        iterator->index++;

        if (is_iterator_valid(iterator)) {
            iterator->curr = iterator->curr->next;
        }
        else{
            iterator->index--;
        }
    }
}

#else

#error Please choose between DYNAMIC_ARRAY (0) or LINKED LIST (1).

#endif

void delete_iterator(ListIterator *iterator) {
    free(iterator);
}

bool is_iterator_valid(ListIterator *iterator) {
    return (iterator) ? iterator->index < get_list_size(iterator->list) : 0;
}

void *iterator_get_next(ListIterator *iterator) {
    if (!is_iterator_valid(iterator)) {
        return NULL;
    }

    void *element = get_current_iterator_element(iterator);
    iterator_next(iterator);
    return element;
}

//todo add tests if necessary
void copy_list_values_it(List *to, ListIterator *iterator) {
    while (is_iterator_valid(iterator)) {
        add_elem_list_bottom(to, new_int(RETRIEVE_INTEGER(iterator_get_next(iterator))));
    }
}

void copy_list_values(List *from, List *to) {
    ListIterator *iterator = create_iterator(from);

    copy_list_values_it(to, iterator);

    delete_iterator(iterator);
}