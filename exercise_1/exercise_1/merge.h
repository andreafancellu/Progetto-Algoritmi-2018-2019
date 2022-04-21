#pragma once

#include "common_inclusions.h"
#include "list_functions.h"
#include "list_iterator.h"
#include "types.h"
#include "test_utils.h"

List *merge(List *list1, List *list2, ValueComparator comparator);

int compare_int_ptr(void *e1, void *e2);
