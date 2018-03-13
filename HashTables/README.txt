# Hash Table Project

* Author: Lane Dirkes
* Class: CS321

## Overview

This program creates and tests two hash tables. The first one is a table
that uses linear hashing and the second one uses double hashing. It can
use three data sources to do this, which are described in the Usage
section of this text.

## Usage

java HashTest <data source> <load factor> [<debug level>]

data source: 1. random integer, 2. current system time ms (long),
             3. strings from word-list

load factor: 0 < LF < 1

debug level: 0 (or blank). print out summary to console, 1. print summary
             and dump tables to files

## Testing

To test this project, I created the HashTest class. It tests both linear and
double hashing tables with one of the three data sources listed in the Usage
section and the user specified load factor. It will print the table's
statistics to the console and will dump their contents to a file if the user
set the debug level to 1.

## Design decisions

The main design choice I made in this project pertains to the current system
time test. The test results in many duplicates for each object due to the fact
that the insertion works much faster than a millisecond. Rather than delay the
insertion method to prevent this, I simply made my insertion counter a long so
that I could see how my program handled over a billion duplicates.


## Sources used

How to create/write to a file in java:
https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java

## Known Issues

Currently, there are no known issues with this project.
