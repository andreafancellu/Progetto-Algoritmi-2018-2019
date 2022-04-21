#pragma once

#include "common_inclusions.h"
#include "list_functions.h"
#include "types.h"
#include "test_utils.h"

/**
 * @brief Used for the creation of a new iterator
 * @param The List that the new iterator will point
 * @return A new iterator
 */
ListIterator *create_iterator(List *list);

/**
 * @brief Used to check if the iterator is valid
 * @param The Iterator we want to analyze
 * @return true if it's valid, false otherwise.
 */
bool is_iterator_valid(ListIterator *iterator);

/**
 * @brief Method used to get the current element of an iterator.
 * @param An iterator
 * @return A pointer to the current element.
 */
void *get_current_iterator_element(ListIterator *iterator);

/**
 * @brief Used to move the iterator to the next value of the object
 * @param l The iterator considered
 */
void iterator_next(ListIterator *iterator);

/**
 * @brief Method that retrieves the current element of an iterator and moves the pointer to the next object
 * @param iterator The iterator to use
 * @return The currently pointed object
 */
void *iterator_get_next(ListIterator *iterator);

/**
 * @brief Delete the iterator given
 * @param An iterator
 */
void delete_iterator(ListIterator *iterator);

/**
 * @brief Appends the values of an iterator to a list.
 * @param to The list to append the values to.
 * @param iterator The iterator to take the values from.
 */
void copy_list_values_it(List *to, ListIterator *iterator);

/**
 * @brief Appends all the values of a list to another list.
 * @param from The list to copy the values from
 * @param to The list to copy the values to.
 */
void copy_list_values(List *from, List *to);