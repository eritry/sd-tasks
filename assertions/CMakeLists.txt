cmake_minimum_required(VERSION 3.21)
project(assertions)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(assertions
        LRU.h
        test.cpp)
