


### 1. 安装Redis


#### 1.1 安裝依赖

```shell
tar -zxvf tcl8.6.1-src.tar.gz && cd tcl8.6.1/unix && ./configure && make && make install
```

#### 1.2 安装Redis服务

```shell
tar -zxvf redis-stable.tar.gz && cd redis-stable && make && make install && make test
```

#### 1.3 测试安装是否成功

```shell
[root@localhost redis-stable]# redis-server 
60513:C 07 Jun 2022 11:11:18.503 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
60513:C 07 Jun 2022 11:11:18.504 # Redis version=7.0.0, bits=64, commit=00000000, modified=0, pid=60513, just started
60513:C 07 Jun 2022 11:11:18.504 # Warning: no config file specified, using the default config. In order to specify a config file use redis-server /path/to/redis.conf
60513:M 07 Jun 2022 11:11:18.504 * monotonic clock: POSIX clock_gettime
                _._                                                  
           _.-``__ ''-._                                             
      _.-``    `.  `_.  ''-._           Redis 7.0.0 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._                                  
 (    '      ,       .-`  | `,    )     Running in standalone mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
 |    `-._   `._    /     _.-'    |     PID: 60513
  `-._    `-._  `-./  _.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |           https://redis.io       
  `-._    `-._`-.__.-'_.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |                                  
  `-._    `-._`-.__.-'_.-'    _.-'                                   
      `-._    `-.__.-'    _.-'                                       
          `-._        _.-'                                           
              `-.__.-'                                               

60513:M 07 Jun 2022 11:11:18.505 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
60513:M 07 Jun 2022 11:11:18.505 # Server initialized
60513:M 07 Jun 2022 11:11:18.505 # WARNING overcommit_memory is set to 0! Background save may fail under low memory condition. To fix this issue add 'vm.overcommit_memory = 1' to /etc/sysctl.conf and then reboot or run the command 'sysctl vm.overcommit_memory=1' for this to take effect.
60513:M 07 Jun 2022 11:11:18.505 * The AOF directory appendonlydir doesn't exist
60513:M 07 Jun 2022 11:11:18.506 * Ready to accept connections
^C60513:signal-handler (1654571480) Received SIGINT scheduling shutdown...
60513:M 07 Jun 2022 11:11:20.111 # User requested shutdown...
60513:M 07 Jun 2022 11:11:20.111 * Saving the final RDB snapshot before exiting.
60513:M 07 Jun 2022 11:11:20.141 * DB saved on disk
60513:M 07 Jun 2022 11:11:20.141 # Redis is now ready to exit, bye bye...
```



#### 1.4 配置Redis

> /etc/redis/6379.conf

```shell
port 6379
# 默认绑定
bind 0.0.0.0
# 是否守护进程启动
daemonize yes
# 日志级别：notice或者debug(开发)
loglevel notice
# 日志文件
logfile "/data/redis/logs/redis_6379.log"
# data路径
dir /data/redis/data
# 线程
pidfile /var/run/redis_6379.pid
# 开启日志Append
appendonly yes
# 最大使用内存
maxmemory 1048576
# aof文件名
appendfilename "appendonly.aof"
# 同步写入策略 always、everysec、no
appendfsync everysec

# RDB
#在900秒（15分钟）之后，如果至少有一个key发生变化，则dump内存快照
save 900 1
#在300秒（15分钟）之后，如果至少有10个key发生变化，则dump内存快照
save 300 10
#在60秒（1分钟）之后，如果至少有10000个key发生变化，则dump内存快照   
save 60 10000
# rdb文件名
dbfilename dump.rdb
# Redis默认是开启压缩的。
rdbcompression yes
# 用户密码
user admin on -DEBUG +@all ~* >lygr@0907
requirepass lygr@0907
```

#### 1.5 创建Redis脚本

> /etc/init.d/redisd

```shell
REDISPORT=6379
EXEC=/usr/local/bin/redis-server
CLIEXEC=/usr/local/bin/redis-cli

PIDFILE=/var/run/redis_${REDISPORT}.pid
CONF="/etc/redis/${REDISPORT}.conf"

case "$1" in
    start)
        if [ -f $PIDFILE ]
        then
                echo "$PIDFILE exists, process is already running or crashed"
        else
                echo "Starting Redis server..."
                $EXEC $CONF
        fi
        ;;
    stop)
        if [ ! -f $PIDFILE ]
        then
                echo "$PIDFILE does not exist, process is not running"
        else
                PID=$(cat $PIDFILE)
                echo "Stopping ..."
                $CLIEXEC -p $REDISPORT shutdown
                while [ -x /proc/${PID} ]
                do
                    echo "Waiting for Redis to shutdown ..."
                    sleep 1
                done
                echo "Redis stopped"
        fi
        ;;
    *)
        echo "Please use start or stop as first argument"
        ;;
esac
```


#### 1.6 启动Redis服务
```shell
chmod +x /etc/init.d/redisd
```


#### 1.7 启动Redis服务

```shell
 /etc/init.d/redisd start
```

#### 1.8 停止Redis服务

```shell
 /etc/init.d/redisd stop
```


### 2. 主从安装


### 3. cluster安装 





### 参考资料

```shell
https://www.bookstack.cn/read/redisson-wiki-zh/spilt.3.2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95.md
```

