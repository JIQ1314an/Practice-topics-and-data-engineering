#! /bin/bash
source /etc/profile
table1=ip_access
table2=page_access
table3=top10_ip_access
table4=top10_page_access
table5=single_value_index
table6=new_add_user
table7=login_user
##加载分区
hive -e "alter table lams.website_log  add partition(createtime='$1');"
wait

##One.获得ip_access表
echo "**one.正在使用hive创建查询结果表$table1***"
hive -e "create  table if not exists lams.$table1 as select ip,count(distinct(url)) access_page_num,'$1' create_time  from lams.website_log where createtime='$1' group by ip;"
##table1导出到mysql
echo "**one.正在导出$table1***"
ssh node121 "sh /opt/shellScript/hiveExport.sh $table1"
wait
##删除表1
echo "**one.正在删除查询结果表$table1***"
hive -e "drop table lams.$table1;"

##Two.获得page_access表
echo "**two.正在使用hive创建查询结果表$table2***"
hive -e "create  table if not exists lams.$table2  as select url,count(1) page_accessed_num,'$1' create_time  from  lams.website_log where createtime='$1' group by url;"
##table2导出到mysql
echo "**two.正在导出$table2表***"
ssh node121 "sh /opt/shellScript/hiveExport.sh $table2"
wait
##删除表2
echo "**two.正在删除查询结果表$table2***"
hive -e "drop table lams.$table2;"

##Three.获得top10_ip_access表
echo "**three.正在使用hive创建查询结果表$table3***"
hive -e "create table if not exists lams.$table3 as select ip,count(url) access_page_num,'$1' create_time from lams.website_log where createtime='$1'  group by ip order by access_page_num desc limit 10;"
##table3导出到mysql
echo "**three.正在导出$table3***"
ssh node121 "sh /opt/shellScript/hiveExport.sh $table3"
wait
##删除表3
echo "**three.正在删除查询结果表$table3***"
hive -e "drop table lams.$table3;"

##Four.获得top10_page_access表
echo "**four.正在使用hive创建查询结果表$table4***"
hive -e "create table if not exists lams.$table4 as select url,count(1) page_accessed_num,'$1' create_time from  lams.website_log where createtime='$1' group by url order by page_accessed_num desc limit 10;"
##table3导出到mysql
echo "**four.正在导出$table4***"
ssh node121 "sh /opt/shellScript/hiveExport.sh $table4"
wait
##删除表4
echo "**four.正在删除查询结果表$table4***"
hive -e "drop table lams.$table4;"

##Five.获取single_value_index表
echo "**five.正在使用hive创建查询结果表$table5***"
hive -e "create table if not exists lams.$table5 as select count(distinct(ip)) total_ip_access,count(distinct(url)) total_ip_url,'$1' create_time  from lams.website_log where createtime='$1';"
##table5导出到mysql
echo "**five.正在导出$table5***"
ssh node121 "sh /opt/shellScript/hiveExport.sh $table5"
wait
##删除表5
echo "**five.正在删除查询结果表$table5***"
hive -e "drop table lams.$table5;"

##Six.获取new_add_user表
echo "**six.正在使用hive创建查询结果表$table6***"
hive -e "create table if not exists lams.new_add_user as select count(distinct(ip)) add_user_num,'$1' createtime from lams.website_log where createtime='$1' and accessmark=0;"
##table6导出到mysql
echo "**six.正在导出$table6***"
ssh node121 "sh /opt/shellScript/hiveExport.sh $table6"
wait
##删除表6
echo "**five.正在删除查询结果表$table6***"
hive -e "drop table lams.$table6;"

##Seven.获取login_user表
echo "**seven.正在使用hive创建查询结果表$table7***"
hive -e "create table if not exists lams.login_user as select count(distinct(ip)) login_user_num,'$1' createtime from lams.website_log where createtime='$1' and  accessmark=1;"
##table7导出到mysql
echo "**seven.正在导出$table7***"
ssh node121 "sh /opt/shellScript/hiveExport.sh $table7"
wait
##删除表7
echo "**seven.正在删除查询结果表$table7***"
hive -e "drop table lams.$table7;"
