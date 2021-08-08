######1.runCreatelog.sh
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



######2.runMoveFile.sh
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



######3.runTask.sh
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



######4.HiveExport.sh
source /etc/profile
databasename=lams
tablename=$1
sqoop export \
--connect "jdbc:mysql://node122:3306/$databasename?useSSL=false&useUnicode=true&characterEncoding=UTF-8" \
--username root \
--password 123456 \
--table $tablename \
-m 1 \
--export-dir /hive/warehouse/$databasename.db/$tablename \
--input-fields-terminated-by '\001'



######5.runFlumeDir.sh
#!/bin/bash
source /etc/profile
#flume的安装根目录（根据自己情况，修改为自己的安装目录）
path=/opt/apps/flume-1.9.0
echo "flume home is :$path"
#flume的进程名称，固定值（不用修改）
JAR="flume"
#flume的配置文件名称（根据自己的情况，修改为自己的flume配置文件名称）
confpath=/opt/apps/flume-conf
Flumeconf="flume-weblog.conf"
#定义的soure名称
agentname="a1"
function start(){
echo "begin start flume process ...."
#查找flume运行的进程数
num=`ps -ef|grep java|grep $JAR|wc -l` 
#判断是否有flume进程运行，如果有则运行执行nohup命令
if [ "$num" = "0" ] ;then
nohup $path/bin/flume-ng agent --conf-file  $confpath/$Flumeconf --name $agentname >>/opt/logs/runflume-weblog.log  2>&1 &
echo "start success...."
echo "启动日志信息文件: $confpath/logs/flume-weblog.log, 监控目录:/opt/spooldir"
else
echo "进程已经存在,启动失败,请检查....."
exit 0
fi
}
function stop(){
echo "begin stop flume process.."
num=`ps -ef|grep java|grep $JAR|wc -l`
#echo "$num...."
if [ "$num" != "0" ];then
#正常停止flume
ps -ef|grep java|grep $JAR|awk '{print $2;}'|xargs kill
echo "进程已经关闭..."
else
echo "服务未启动，无须停止..."
fi
}
function restart(){
#echo "begin stop flume process .."
#执行stop函数
stop
#判断程序是否彻底停止
num='ps -ef|grep java|grep $JAR|wc -l'
#stop完成之后，查找flume的进程数，判断进程数是否为0，如果不为0，则休眠5秒，再次查看，直到进程数为0
while [ $num -gt 0 ];do
sleep 5
num='ps -ef|grep java|grep $JAR|wc -l'
done
echo "flume process stoped,and starting..."
#执行start
start
echo "started...."
}
function stop(){
echo "begin stop flume process.."
num=`ps -ef|grep java|grep $JAR|wc -l`
#echo "$num...."
if [ "$num" != "0" ];then
#正常停止flume
ps -ef|grep java|grep $JAR|awk '{print $2;}'|xargs kill
echo "进程已经关闭..."
else
echo "服务未启动，无须停止..."
fi
}
#case 命令获取输入的参数，如果参数为start,执行start函数，如果参数为stop执行stop函数，如果参数为restart，执行restart函数
case "$1" in
"start")
start
;;
"stop")
stop
;;
"restart")
restart
;;
*)
;;
esac 