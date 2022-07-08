### 0. 环境配置

* SSH免密登录
* 调整ssh连接数
    * 修改 /etc/ssh/sshd_config 将 MaxSessions 调至 20。
    * 重启sshd服务：service sshd restart

### 1. 下载安装

```shell
curl --proto '=https' --tlsv1.2 -sSf https://tiup-mirrors.pingcap.com/install.sh | sh
```

### 2. 声明全局环境变量

```shell
source /root/.bash_profile
```

### 3. 安装Tiup cluster组件

```shell
tiup cluster
```

### 4. 部署cluster集群

> 编辑topo.yaml配置文件

```yaml
# # Global variables are applied to all deployments and used as the default value of
# # the deployments if a specific deployment value is missing.
global:
  user: "tidb"
  ssh_port: 22
  deploy_dir: "/tidb-deploy"
  data_dir: "/tidb-data"

# # Monitored variables are applied to all the machines.
monitored:
  node_exporter_port: 9100
  blackbox_exporter_port: 9115

server_configs:
  tidb:
    log.slow-threshold: 300
  tikv:
    readpool.storage.use-unified-pool: false
    readpool.coprocessor.use-unified-pool: true
  pd:
    replication.enable-placement-rules: true
    replication.location-labels: [ "host" ]
  tiflash:
    logger.level: "info"

pd_servers:
  - host: 10.50.30.177

tidb_servers:
  - host: 10.50.30.177

tikv_servers:
  - host: 10.50.30.177
    port: 20160
    status_port: 20180
    config:
      server.labels: { host: "optimus30a177" }

  - host: 10.50.30.177
    port: 20161
    status_port: 20181
    config:
      server.labels: { host: "optimus30a177" }

  - host: 10.50.30.177
    port: 20162
    status_port: 20182
    config:
      server.labels: { host: "optimus30a177" }

tiflash_servers:
  - host: 10.50.30.177

monitoring_servers:
  - host: 10.50.30.177

grafana_servers:
  - host: 10.50.30.177
```

```shell
tiup cluster deploy tidb_test 6.1.0 ./topo.yaml --user root -p
```

### 5. 启动并初始化集群

```shell
tiup cluster start tidb_test --init
```

### 6. 查看已部署集群

```shell
tiup cluster list
```

### 7. 查看集群拓扑结构

```shell
tiup cluster display tidb_test
```