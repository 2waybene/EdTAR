#!/bin/bash

file=$1;
string=$2;
echo "Here is the string you want to count "
echo $string

function countStr{
        echo Here is the string 
	echo $1 
	echo "inside function"
}



countStr "$string"


