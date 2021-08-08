#!/bin/bash
##模拟产生当天的日志,等待7s开启移动文件的脚本,只产生一个文件
source /etc/profile

##获取当前时间，精确到时
str=`date +%Y-%m-%d--%H`

##启动日志生成代码--默认七天
java -jar /opt/jardir/CreateLog-1.0.0.jar 1 2 $str 
sleep 10s

##启动移动文件的脚本
sh /opt/shellScript/runMoveFile.sh
