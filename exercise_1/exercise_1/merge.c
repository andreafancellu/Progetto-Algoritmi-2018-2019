#include "merge.h"

List *merge(List *list1, List *list2, ValueComparator comparator) {
    if (comparator == NULL) {
        return NULL;
    }

    if (is_list_empty(list1)) {
        if (is_list_empty(list2))
            // Both lists are empty
            return NULL;
        else {
            List *sortedList = new_list();
            copy_list_values(list2, sortedList);
            return sortedList;
        }
    } else {
        if (is_list_empty(list2)) {
            List *sortedList = new_list();
            copy_list_values(list1, sortedList);
            return sortedList;
        } else {
            List *sortedList = new_list();
            ListIterator *iterator1 = create_iterator(list1);
            ListIterator *iterator2 = create_iterator(list2);

            void *leftValue, *rightValue;

            while (is_iterator_valid(iterator1) && is_iterator_valid(iterator2)) {
                leftValue = get_current_iterator_element(iterator1);
                rightValue = get_current_iterator_element(iterator2);

                if (comparator(leftValue, rightValue) <= 0) {
                    add_elem_list_bottom(sortedList, new_int(RETRIEVE_INTEGER(leftValue)));
                    iterator_next(iterator1);
                } else {
                    add_elem_list_bottom(sortedList, new_int(RETRIEVE_INTEGER(rightValue)));
                    iterator_next(iterator2);
                }
            }

            if (is_iterator_valid(iterator1)) {
                copy_list_values_it(sortedList, iterator1);
            } else if (is_iterator_valid(iterator2)) {
                copy_list_values_it(sortedList, iterator2);
            }

            return sortedList;
        }
    }
}

/* *Ptr1 =? *Ptr2  */
int compare_int_ptr(void *e1, void *e2) {
    int int1 = RETRIEVE_INTEGER(e1);
    int int2 = RETRIEVE_INTEGER(e2);

    if (int1 == int2) {
        return 0;
    } else if (int1 < int2) {
        return -1;
    } else
        return 1;
}