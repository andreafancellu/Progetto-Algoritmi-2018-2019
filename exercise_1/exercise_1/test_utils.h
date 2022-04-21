#pragma once

#include "common_inclusions.h"
#include "types.h"
#include "list_functions.h"

// Int methods
int *new_int(int v);

void add_int_range(List *list, int start, int end);

void add_int_array(List *list, const int *vec, int length);