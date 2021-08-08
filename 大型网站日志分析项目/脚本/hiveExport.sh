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
