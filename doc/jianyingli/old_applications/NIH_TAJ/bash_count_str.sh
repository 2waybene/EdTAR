#!/bin/bash

file=$1
exec<$file

function count {
        grep -o "SpecStr" <<<"$1" | wc -l
}


while read line
   
   do
	tarString=${tarString}"_"${line}
   done

echo \"$tarString\" | tr [:space:]  _
echo $tarString
count $tarString


