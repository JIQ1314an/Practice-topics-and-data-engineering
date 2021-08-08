#!/bin/bash
##凌晨两点执行程序任务
source /etc/profile
##获取当前时间和前一天的时间
str1=`date -d "1 day ago" +%Y-%m-%d`

##执行清洗数据的jar包
echo "****清洗$str1""时候的数据****"
hadoop jar /opt/jardir/CleanLog-1.0.0.jar $str1

##删除清洗后在hdfs上临时输出文件夹SUCCESS
hadoop fs -rm -r  /CleanedData/$str1

#执行hive语句并且导出结果数据
sh /opt/shellScript/runHive.sh $str1
wait

##删除sqoop导出表时的.java文件
ssh node121 "rm -rf /root/*.java"
##关闭hive服务
#kill -9 $(ps -ef | grep RunJar | grep -v grep | awk '{print $2}')
