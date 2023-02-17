FROM java:8u111
# 作者
MAINTAINER WangMaoSong

ARG JAR_FILE=target/simba-auth-1.0-SNAPSHOT.jar
# 堆最小初始化内存
ARG HEAP_INITIAL_SIZE=128m
# 堆最大内存
ARG HEAP_MAX_SIZE=512g
# 新生代最大使用内存
ARG HEAP_NEW_GENERATION_SIZE=128m
# 环境变量
ARG SPRING_PROFILES_ACTIVE=dev
# 服务端口
ARG SERVER_PORT=9001

# 环境变量
#时区
ENV TZ=Asia/Shanghai
# JVM配置
ENV JAVA_OPTS="-server -Xms$HEAP_INITIAL_SIZE -Xmx$HEAP_MAX_SIZE -Xmn$HEAP_NEW_GENERATION_SIZE \
                 -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m  -Xss512k -XX:+UseParNewGC -XX:+UseConcMarkSweepGC \
                 -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseCMSInitiatingOccupancyOnly \
                 -XX:CMSInitiatingOccupancyFraction=70 -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError  \
                 -XX:HeapDumpPath=dump.hprof \
                 -Xloggc:logs/gc.log \
                 --spring.profiles.active=$SPRING_PROFILES_ACTIVE"

ADD $JAR_FILE app.jar

CMD java -jar app.jar $JAVA_OPTS

EXPOSE $SERVER_PORT:9001