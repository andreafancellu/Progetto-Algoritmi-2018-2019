cmake_minimum_required(VERSION 3.13)

project(laboratorio-algoritmi-2018-19 C)

set(CMAKE_C_STANDARD 99)

set(unity_sources Unity/unity_internals.h Unity/unity.h Unity/unity.c)
set(list_sources list_functions.h list_functions.c list_iterator.h list_iterator.c)

add_executable(test test_list.c common_inclusions.h ${list_sources} types.h test_utils.c test_utils.h ${unity_sources} merge.c merge.h)