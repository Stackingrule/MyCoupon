-- 登录 MySQL 服务器
mysql -hlocalhost -root -bd1998

-- 创建数据库 coupon_data
CREATE DATABASE IF NOT EXISTS coupon_data;

-- 登录 MySQL 服务器, 并进入到 imooc_coupon_data 数据库中
mysql -hlocalhost -uroot -pDjangobbs -Dimooc_coupon_data
