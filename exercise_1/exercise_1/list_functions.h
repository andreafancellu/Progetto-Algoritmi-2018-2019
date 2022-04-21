#pragma once

#include "common_inclusions.h"
#include "types.h"

/**
 * @brief Create a new instance of a list.
 * @return The newly created list
 */
List *new_list();

/**
 * @brief Delete an instance of a list.<br>
 * This method deallocates only the data structure, NOT the data contained.
 * @param list The list to delete.
 */
void del_list(List *list);

/**
 * @brief Check if the list is empty.
 * @param list Pointer to the list to check.
 * @return true if empty, false otherwise.
 */
bool is_list_empty(List *list);

/**
 * @brief Gets the size of the list.
 * @param list Pointer to the list to check.
 * @return Size of the list l.
 */
size_t get_list_size(List *list);

/**
 * @brief Adds an element to the bottom of the list.
 * @param list The list to add the element to.
 * @param element The element to add
 */
void add_elem_list_bottom(List *list, void *element);

/**
 * @brief Adds an element at the index indicated of the list.
 * @param array The list to add the element to.
 * @param element The element to add.
 * @param index At what index to add the element to the list.
 */
void add_elem_list_index(List *array, void *element, int index);

/**
 * @brief Deletes the element at the bottom of the list.
 * @param list The list to remove the element from
 */
void delete_list_elem_bottom(List *list);

/**
 * @brief Deletes the element at the indicated index of the list.
 * @param list The list to remove the element from.
 * @param index The index of the element to remove from the list.
 */
void delete_list_elem_index(List *list, int index);

/**
 * @brief Retrieves a pointer to an element from the list
 * @param list The list to retrieve the element from.
 * @param index The index of the element to retrieve.
 * @return A pointer to the retrieved element.
 */
void *get_list_elem_index(List *list, int index);
