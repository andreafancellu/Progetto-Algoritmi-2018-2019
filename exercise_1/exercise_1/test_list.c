#include "common_inclusions.h"
#include "types.h"
#include "list_functions.h"
#include "list_iterator.h"
#include "test_utils.h"

#include "merge.h"

#include "Unity/unity.h"
#include "Unity/unity_internals.h"

// todo implement builder for float and chars, then use same test to test the 3 types.
static List *build_list_int() {
    List *list = new_list();

    add_elem_list_bottom(list, new_int(1));
    add_elem_list_bottom(list, new_int(10));
    add_elem_list_bottom(list, new_int(4));
    add_elem_list_bottom(list, new_int(7));
    add_elem_list_bottom(list, new_int(3));

    return list;
}

/* ************************************  LIST TEST ************************************* */

static void test_new_list_not_null() {
    List *list = new_list();
    TEST_ASSERT_NOT_NULL(list)

    del_list(list);
}

static void test_empty_list_size() {
    List *list = new_list();
    TEST_ASSERT_EQUAL_INT(0, get_list_size(list));

    del_list(list);
}

static void test_list_del() {
    List *list = new_list();
    del_list(list);

    TEST_ASSERT_EQUAL_INT(1, 1);
}

static void test_NULL_list_del() {
    List *list = NULL;
    del_list(list);
    TEST_ASSERT_EQUAL_INT(1, 1);
}

static void test_empty_list() {
    List *list = new_list();
    TEST_ASSERT_EQUAL_INT(true, is_list_empty(list));

    del_list(list);
}

static void test_null_list_empty() {
    List *list = NULL;
    TEST_ASSERT_EQUAL_INT(true, is_list_empty(list));
    del_list(list);
}

static void test_list_empty() {
    List *list = build_list_int();
    TEST_ASSERT_EQUAL_INT(false, is_list_empty(list));

    del_list(list);
}

static void test_null_list_size() {
    List *list = NULL;
    TEST_ASSERT_EQUAL_INT(0, get_list_size(list));
    del_list(list);
}

static void test_list_size() {
    List *list = build_list_int();
    TEST_ASSERT_EQUAL_INT(5, get_list_size(list));

    del_list(list);
}

static void test_NULL_list_add_bottom() {
    List *list = NULL;
    add_elem_list_bottom(list, new_int(100));
    del_list(list);
    TEST_ASSERT_EQUAL_INT(1, 1);
}

static void test_empty_list_add_bottom() {
    List *list = new_list();
    add_elem_list_bottom(list, new_int(100));

    TEST_ASSERT_EQUAL_INT(100, RETRIEVE_INTEGER(get_list_elem_index(list, 0)));

    del_list(list);
}

static void test_list_add_bottom() {
    List *list = build_list_int();
    add_elem_list_bottom(list, new_int(100));

    TEST_ASSERT_EQUAL_INT(100, RETRIEVE_INTEGER(get_list_elem_index(list, 5)));

#if DYNAMIC_ARRAY == 0
    /* Specific test made to check that the end of a list has a pointer to NULL */
    TEST_ASSERT_NULL(list->head->next->next->next->next->next->next)
#endif

    del_list(list);
}

static void test_NULL_list_add_index() {
    List *list = NULL;
    add_elem_list_index(list, new_int(100), 0);
    del_list(list);
    TEST_ASSERT_EQUAL_INT(1, 1);
}

static void test_empty_list_add_index() {
    List *list = new_list();
    add_elem_list_index(list, new_int(2), 0);

    TEST_ASSERT_EQUAL_INT(1, get_list_size(list));

    add_elem_list_index(list, new_int(5), 5);

    TEST_ASSERT_EQUAL_INT(1, get_list_size(list));

    del_list(list);
}

static void test_list_add_index() {
    List *list = build_list_int();
    add_elem_list_index(list, new_int(44), 2);

    TEST_ASSERT_EQUAL_INT(44, RETRIEVE_INTEGER(get_list_elem_index(list, 2)));

    add_elem_list_index(list, new_int(56), 7);

    TEST_ASSERT_EQUAL_INT(6, get_list_size(list));

    add_elem_list_index(list, new_int(66), 0);

    TEST_ASSERT_EQUAL_INT(7, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(66, RETRIEVE_INTEGER(get_list_elem_index(list, 0)));

    add_elem_list_index(list, new_int(100), get_list_size(list));
    TEST_ASSERT_EQUAL_INT(8, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(100, RETRIEVE_INTEGER(get_list_elem_index(list, get_list_size(list) - 1)));


    del_list(list);
}

static void test_NULL_list_delete_bottom() {
    List *list = NULL;
    delete_list_elem_bottom(list);
    del_list(list);
    TEST_ASSERT_EQUAL_INT(1, 1);
}

static void test_empty_list_delete_bottom() {
    List *list = new_list();
    delete_list_elem_bottom(list);

    TEST_ASSERT_EQUAL_INT(1, 1);
    del_list(list);
}

static void test_list_delete_bottom() {
    List *list = build_list_int();
    delete_list_elem_bottom(list);

    TEST_ASSERT_EQUAL_INT(4, get_list_size(list));

    TEST_ASSERT_EQUAL_INT(1, RETRIEVE_INTEGER(get_list_elem_index(list, 0)));
    TEST_ASSERT_EQUAL_INT(10, RETRIEVE_INTEGER(get_list_elem_index(list, 1)));
    TEST_ASSERT_EQUAL_INT(4, RETRIEVE_INTEGER(get_list_elem_index(list, 2)));
    TEST_ASSERT_EQUAL_INT(7, RETRIEVE_INTEGER(get_list_elem_index(list, 3)));

    del_list(list);
}

static void test_NULL_list_delete_index() {
    List *list = NULL;
    delete_list_elem_index(list, 0);
    del_list(list);
    TEST_ASSERT_EQUAL_INT(1, 1);
}

static void test_empty_list_delete_index() {
    List *list = new_list();
    delete_list_elem_index(list, 0);
    del_list(list);
    TEST_ASSERT_EQUAL_INT(1, 1);
}

static void test_list_delete_index() {
    List *list = build_list_int();
    delete_list_elem_index(list, 4);

    TEST_ASSERT_EQUAL_INT(4, get_list_size(list));

    TEST_ASSERT_EQUAL_INT(1, RETRIEVE_INTEGER(get_list_elem_index(list, 0)));
    TEST_ASSERT_EQUAL_INT(10, RETRIEVE_INTEGER(get_list_elem_index(list, 1)));
    TEST_ASSERT_EQUAL_INT(4, RETRIEVE_INTEGER(get_list_elem_index(list, 2)));
    TEST_ASSERT_EQUAL_INT(7, RETRIEVE_INTEGER(get_list_elem_index(list, 3)));

    del_list(list);
}

#define HOW_BIG 1000

static void test_big_list_fun() {
    List *list = new_list();
    add_int_range(list, 0, HOW_BIG);
    for (int i = 0; i < get_list_size(list); i++) {
        TEST_ASSERT_EQUAL_INT(i, RETRIEVE_INTEGER(get_list_elem_index(list, i)));
    }
    del_list(list);
}

#undef HOW_BIG

#if DYNAMIC_ARRAY == 1

static void test_arraylist_increase() {
    List *list = new_list();

    TEST_ASSERT_EQUAL_INT(0, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(10, list->capacity);

    add_int_range(list, 0, 10);

    TEST_ASSERT_EQUAL_INT(10, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(10, list->capacity);

    add_elem_list_bottom(list, new_int(50));

    TEST_ASSERT_EQUAL_INT(11, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(20, list->capacity);

    del_list(list);
}

static void test_arraylist_decrease() {
    List *list = new_list();

    TEST_ASSERT_EQUAL_INT(0, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(10, list->capacity);

    add_int_range(list, 0, 40);

    TEST_ASSERT_EQUAL_INT(40, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(40, list->capacity);

    for (int i = 0; i < 30; i++) {
        delete_list_elem_bottom(list);
    }

    TEST_ASSERT_EQUAL_INT(10, get_list_size(list));
    TEST_ASSERT_EQUAL_INT(20, list->capacity);

    del_list(list);
}

#endif

/*
 * START negative index tests
 * neg_i means Negative Index
 */

static void test_NULL_list_add_index_neg_i() {
    List *list = NULL;

    add_elem_list_index(list, new_int(100), -1);
    TEST_ASSERT_EQUAL_PTR(NULL, list);

    add_elem_list_index(list, new_int(100), -1000);
    TEST_ASSERT_EQUAL_PTR(NULL, list);
    del_list(list);
}

static void test_empty_list_add_index_neg_i() {
    List *list = new_list();
    int size_before = get_list_size(list);

    add_elem_list_index(list, new_int(2), -1);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    add_elem_list_index(list, new_int(2), -1000);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    del_list(list);
}

static void test_list_add_index_neg_i() {
    List *list = build_list_int();
    int size_before = get_list_size(list);

    add_elem_list_index(list, new_int(44), -1);

    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    add_elem_list_index(list, new_int(56), -1000);

    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    del_list(list);
}

static void test_NULL_list_delete_index_neg_i() {
    List *list = NULL;
    int size_before = get_list_size(list);

    delete_list_elem_index(list, -1);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    delete_list_elem_index(list, -1000);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));
    del_list(list);
}

static void test_empty_list_delete_index_neg_i() {
    List *list = new_list();
    int size_before = get_list_size(list);

    delete_list_elem_index(list, -1);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    delete_list_elem_index(list, -1000);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));
    del_list(list);
}

static void test_list_delete_index_neg_i() {
    List *list = build_list_int();
    int size_before = get_list_size(list);

    delete_list_elem_index(list, -1);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    delete_list_elem_index(list, -1000);
    TEST_ASSERT_EQUAL_INT(size_before, get_list_size(list));

    del_list(list);
}

static void test_NULL_get_list_elem_neg_i() {
    List *list = NULL;
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, -1));
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, -1000));
    del_list(list);
}

static void test_empty_get_list_elem_neg_i() {
    List *list = new_list();
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, -1));
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, -1000));
    del_list(list);
}

static void test_get_list_elem_neg_i() {
    List *list = build_list_int();
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, -1));
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, -1000));
    del_list(list);
}

/*
 * END negative index tests
 */


static void test_get_list_elem() {
    List *list = build_list_int();

    TEST_ASSERT_EQUAL_INT(1, RETRIEVE_INTEGER(get_list_elem_index(list, 0)));
    TEST_ASSERT_EQUAL_INT(10, RETRIEVE_INTEGER(get_list_elem_index(list, 1)));
    TEST_ASSERT_EQUAL_INT(4, RETRIEVE_INTEGER(get_list_elem_index(list, 2)));
    TEST_ASSERT_EQUAL_INT(7, RETRIEVE_INTEGER(get_list_elem_index(list, 3)));
    TEST_ASSERT_EQUAL_INT(3, RETRIEVE_INTEGER(get_list_elem_index(list, 4)));
    del_list(list);
}

static void test_NULL_get_list_elem() {
    List *list = NULL;
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, 0));
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, 1));
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, 1000));
    del_list(list);
}

static void test_empty_get_list_elem() {
    List *list = new_list();
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, 0));
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, 1));
    TEST_ASSERT_EQUAL_PTR(NULL, get_list_elem_index(list, 1000));
    del_list(list);
}

void run_list_tests() {
    UNITY_BEGIN();
    RUN_TEST(test_new_list_not_null);
    RUN_TEST(test_empty_list_size);
    RUN_TEST(test_list_del);
    RUN_TEST(test_NULL_list_del);
    RUN_TEST(test_empty_list);
    RUN_TEST(test_null_list_empty);
    RUN_TEST(test_list_empty);
    RUN_TEST(test_null_list_size);
    RUN_TEST(test_list_size);
    RUN_TEST(test_NULL_list_add_bottom);
    RUN_TEST(test_empty_list_add_bottom);
    RUN_TEST(test_list_add_bottom);
    RUN_TEST(test_NULL_list_add_index);
    RUN_TEST(test_empty_list_add_index);
    RUN_TEST(test_list_add_index);
    RUN_TEST(test_NULL_list_delete_bottom);
    RUN_TEST(test_empty_list_delete_bottom);
    RUN_TEST(test_list_delete_bottom);
    RUN_TEST(test_NULL_list_delete_index);
    RUN_TEST(test_empty_list_delete_index);
    RUN_TEST(test_list_delete_index);
    RUN_TEST(test_big_list_fun);
#if DYNAMIC_ARRAY == 1
    RUN_TEST(test_arraylist_increase);
    RUN_TEST(test_arraylist_decrease);
#endif
    RUN_TEST(test_NULL_list_add_index_neg_i);
    RUN_TEST(test_empty_list_add_index_neg_i);
    RUN_TEST(test_list_add_index_neg_i);
    RUN_TEST(test_NULL_list_delete_index_neg_i);
    RUN_TEST(test_empty_list_delete_index_neg_i);
    RUN_TEST(test_list_delete_index_neg_i);
    RUN_TEST(test_NULL_get_list_elem_neg_i);
    RUN_TEST(test_empty_get_list_elem_neg_i);
    RUN_TEST(test_get_list_elem_neg_i);
    RUN_TEST(test_NULL_get_list_elem);
    RUN_TEST(test_get_list_elem);
    RUN_TEST(test_empty_get_list_elem);
    UNITY_END();
}

/* ************************************  ITERATOR TEST ************************************* */

static void test_iterator_valid() {
    List *list = new_list();
    ListIterator *iterator = create_iterator(list);
    TEST_ASSERT_EQUAL_PTR(list, iterator->list);
    TEST_ASSERT_EQUAL_PTR(NULL, get_current_iterator_element(iterator));

    list = build_list_int();
    iterator = create_iterator(list);
    TEST_ASSERT_EQUAL_INT(1, RETRIEVE_INTEGER(get_current_iterator_element(iterator)));
    iterator_next(iterator);
    TEST_ASSERT_EQUAL_INT(10, RETRIEVE_INTEGER(get_current_iterator_element(iterator)));
    iterator_next(iterator);
    TEST_ASSERT_EQUAL_INT(4, RETRIEVE_INTEGER(get_current_iterator_element(iterator)));
    iterator_next(iterator);
    TEST_ASSERT_EQUAL_INT(7, RETRIEVE_INTEGER(get_current_iterator_element(iterator)));
    iterator_next(iterator);
    TEST_ASSERT_EQUAL_INT(3, RETRIEVE_INTEGER(get_current_iterator_element(iterator)));
    iterator_get_next(iterator);

#if DYNAMIC_ARRAY == 1
    TEST_ASSERT_EQUAL_PTR(NULL, get_current_iterator_element(iterator));
#else
    TEST_ASSERT_EQUAL_PTR(3, RETRIEVE_INTEGER(get_current_iterator_element(iterator)));
#endif
    del_list(list);
    delete_iterator(iterator);
}

static void test_iterator_null() {
    List *list = new_list();
    ListIterator *iterator = NULL;

    TEST_ASSERT_NULL(iterator)
    iterator = create_iterator(list);
    TEST_ASSERT_NOT_NULL(iterator)
    TEST_ASSERT_NOT_NULL(iterator->list)
    TEST_ASSERT_EQUAL_PTR(NULL, get_current_iterator_element(iterator));

#if DYNAMIC_ARRAY == 0
    TEST_ASSERT_EQUAL_PTR(NULL, iterator->list->head);
    TEST_ASSERT_EQUAL_PTR(NULL, iterator->list->tail);
    TEST_ASSERT_EQUAL_PTR(NULL, iterator->curr);
#endif

    list = build_list_int();
    iterator = create_iterator(list);
    TEST_ASSERT_NOT_NULL(get_current_iterator_element(iterator))

#if DYNAMIC_ARRAY == 0
    TEST_ASSERT_NOT_NULL(iterator->list->head)
    TEST_ASSERT_NOT_NULL(iterator->list->head->value)
    TEST_ASSERT_NOT_NULL(iterator->list->head->next)
    TEST_ASSERT_NOT_NULL(iterator->list->tail)
    TEST_ASSERT_NOT_NULL(iterator->list->tail->value)
    TEST_ASSERT_NOT_NULL(iterator->list->tail->prev)
    TEST_ASSERT_NOT_NULL(iterator->curr)
#endif

    del_list(list);
    delete_iterator(iterator);
}

static void test_iterator_empty() {
    List *list = NULL;
    ListIterator *iterator = NULL;

    TEST_ASSERT_NULL(iterator)
    iterator = create_iterator(list);
    TEST_ASSERT_NULL(iterator)
    TEST_ASSERT_EQUAL_PTR(NULL, get_current_iterator_element(iterator));

    del_list(list);
    delete_iterator(iterator);
}

void test_iterator_read_full_list() {
    List *list = build_list_int();
    ListIterator *iterator = create_iterator(list);
    int count = 0;

    while (is_iterator_valid(iterator)) {
        iterator_next(iterator);
        count++;
    }

    TEST_ASSERT_EQUAL_INT(get_list_size(iterator->list), count);
    del_list(list);
    delete_iterator(iterator);
}

void run_iterator_tests() {
    UNITY_BEGIN();
    RUN_TEST(test_iterator_valid);
    RUN_TEST(test_iterator_null);
    RUN_TEST(test_iterator_empty);
    RUN_TEST(test_iterator_read_full_list);
    UNITY_END();
}

/* ************************************  MERGE TEST ************************************* */

void test_compare_int() {
    int *a = new_int(5);
    int *b = new_int(1);
    int *c = new_int(1);
    int *d = new_int(6);

    TEST_ASSERT_EQUAL_INT(0, compare_int_ptr(a, a));
    TEST_ASSERT_EQUAL_INT(0, compare_int_ptr(b, c));
    TEST_ASSERT_EQUAL_INT(1, compare_int_ptr(a, c));
    TEST_ASSERT_EQUAL_INT(-1, compare_int_ptr(a, d));
}

void test_merge_null_comparator() {
    TEST_ASSERT_NULL(merge(NULL, NULL, NULL))
}

void test_merge_null_lists() {
    List *l1 = new_list();
    add_elem_list_bottom(l1, new_int(5));
    List *l2 = new_list();
    add_elem_list_bottom(l2, new_int(6));

    TEST_ASSERT_NULL(merge(NULL, NULL, compare_int_ptr))
    TEST_ASSERT_EQUAL_INT(0, get_list_size(merge(NULL, NULL, compare_int_ptr)));
    TEST_ASSERT_EQUAL_INT(5, RETRIEVE_INTEGER(get_list_elem_index(merge(l1, NULL, compare_int_ptr), 0)));
    TEST_ASSERT_EQUAL_INT(6, RETRIEVE_INTEGER(get_list_elem_index(merge(NULL, l2, compare_int_ptr), 0)));
    del_list(l1);
    del_list(l2);
}

void test_merge_empty_list() {
    TEST_ASSERT_NULL(merge(new_list(), new_list(), compare_int_ptr));
    TEST_ASSERT_EQUAL_INT(0, get_list_size(merge(new_list(), new_list(), compare_int_ptr)));
    TEST_ASSERT_EQUAL_INT(0, get_list_size(merge(NULL, new_list(), compare_int_ptr)));
    TEST_ASSERT_EQUAL_INT(0, get_list_size(merge(new_list(), NULL, compare_int_ptr)));
}

void test_merge() {
    List *l1, *l2, *result;
    // Init lists
    l1 = new_list();
    l2 = new_list();

    // SORTED vectors of values to use to fill the lists
    const int a1[] = {1, 3, 4, 10, 21};
    const int a2[] = {2, 3, 5, 9, 11};
    const int expected[] = {1, 2, 3, 3, 4, 5, 9, 10, 11, 21};

    // Fill the list
    add_int_array(l1, a1, 5);

    // Check that the list has been filled correctly
    ListIterator *iterator = create_iterator(l1);
    int counter = 0;
    while (is_iterator_valid(iterator)) {
        TEST_ASSERT_EQUAL_INT(a1[counter], RETRIEVE_INTEGER(iterator_get_next(iterator)));
        counter++;
    }
    delete_iterator(iterator);


    add_int_array(l2, a2, 5);

    iterator = create_iterator(l2);
    counter = 0;
    while (is_iterator_valid(iterator)) {
        TEST_ASSERT_EQUAL_INT(a2[counter], RETRIEVE_INTEGER(iterator_get_next(iterator)));
        counter++;
    }
    delete_iterator(iterator);


    result = merge(l1, l2, compare_int_ptr);

    TEST_ASSERT_NOT_NULL(result)

    iterator = create_iterator(result);

    counter = 0;
    while (is_iterator_valid(iterator)) {
        TEST_ASSERT_EQUAL_INT(expected[counter], RETRIEVE_INTEGER(iterator_get_next(iterator)));
        counter++;
    }
    del_list(l1);
    del_list(l2);
    delete_iterator(iterator);
}

void run_merge_tests() {
    UNITY_BEGIN();
    RUN_TEST(test_compare_int);
    RUN_TEST(test_merge_null_comparator);
    RUN_TEST(test_merge_null_lists);
    RUN_TEST(test_merge);
    RUN_TEST(test_merge_empty_list);
    UNITY_END();
}

int main() {
    printf("\nList Unit Tests:\n\n");
    run_list_tests();

    printf("=============================================================\n"
           "\nIterator Unit Tests:\n\n");
    run_iterator_tests();

    printf("=============================================================\n"
           "\nMerge Unit Tests:\n\n");
    run_merge_tests();

    printf("\nSuccess: Managed to run all tests :)\n");

    return 0;
}
