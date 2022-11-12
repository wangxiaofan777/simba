* 下载镜像

```shell
docker pull elasticsearch:7.17.7
```

* 删除无效的镜像

```shell
docker image prune
```

* 创建工作目录

```shell
mkdir -p /Users/wangmaosong/data/docker/elasticsearch/config
mkdir -p /Users/wangmaosong/data/docker/elasticsearch/data
mkdir -p /Users/wangmaosong/data/docker/elasticsearch/plugins
```

* 配置文件

```shell
echo "http.host: 0.0.0.0" >> /Users/wangmaosong/data/docker/elasticsearch/config/elasticsearch.yml
```

* 创建服务

```shell
docker run --name elasticsearch \
          -p 9200:9200  -p 9300:9300 \
          -e "discovery.type=single-node" \
          -e ES_JAVA_OPTS="-Xms84m -Xmx512m" \
          -v /Users/wangmaosong/data/docker/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
          -v /Users/wangmaosong/data/docker/elasticsearch/data:/usr/share/elasticsearch/data \
          -v /Users/wangmaosong/data/docker/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
          -d elasticsearch:7.17.7
```

* 查看服务

```shell
docker ps
```

* 管理服务

```shell
docker start|stop|restart elasticsearch
```
