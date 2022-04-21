#include "test_utils.h"

/* Ptr to type builder */
int *new_int(int v) {
    int *res = malloc(sizeof(int));
    *res = v;
    return res;
}

/* Add range to specific list */
void add_int_range(List *list, int start, int end) {
    for (int i = start; i < end; i++) {
        add_elem_list_bottom(list, new_int(i));
    }
}

void add_int_array(List *list, const int *vec, int length) {
    for (int i = 0; i < length; i++) {
        add_elem_list_bottom(list, new_int(vec[i]));
    }
}