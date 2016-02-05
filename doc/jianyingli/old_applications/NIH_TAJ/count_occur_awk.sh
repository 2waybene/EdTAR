#!/bin/bash
awk 'BEGIN{
 search="SpecStr"
 total=0
}
NR%10==0{
  c=gsub(search,"",s)
  total+=c  
}
NR{ s=s $0 }
END{ 
 c=gsub(search,"",s)
 print "total count: "total+c
}'

