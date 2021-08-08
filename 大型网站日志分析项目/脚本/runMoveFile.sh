#!/bin/bash
echo "***Flume正在上传文件...***"
##循环停留间隔移动文件
array=(`ls /opt/data/`)
num=${#array[*]}
echo "***正在移动文件,总文件数为$num***"
for((i=0;i<$num;));do
mv  /opt/data/${array[0]} /opt/spooldir/;
sleep 3s;
array=(`ls /opt/data/`);
num=${#array[*]};
done;
echo "***移动完毕***"
