```shell

docker run -d --name pd1 \
  -p 2379:2379 \
  -p 2380:2380 \
  -v /etc/localtime:/etc/localtime:ro \
  -v /Users/wangmaosong/data/docker/tidb:/tidb \
  pingcap/pd:latest \
  --name="pd1" \
  --data-dir="/tidb/pd1" \
  --client-urls="http://0.0.0.0:2379" \
  --advertise-client-urls="http://127.0.0.1:2379" \
  --peer-urls="http://0.0.0.0:2380" \
  --advertise-peer-urls="http://127.0.0.1:2380" \
  --initial-cluster="pd1=http://127.0.0.1:2380"
```


```shell
docker run -d --name tikv1 \
  -p 20160:20160 \
  --ulimit nofile=1000000:1000000 \
  -v /etc/localtime:/etc/localtime:ro \
  -v /Users/wangmaosong/data/docker/tidb/tikv1:/tidb \
  pingcap/tikv:latest \
  --addr="0.0.0.0:20160" \
  --advertise-addr="127.0.0.1:20160" \
  --data-dir="/tikv1" \
  --pd="127.0.0.1:2379"
docker run -d --name tikv2 \
  -p 20161:20161 \
  --ulimit nofile=1000000:1000000 \
  -v /etc/localtime:/etc/localtime:ro \
  -v /Users/wangmaosong/data/docker/tidb/tikv2:/tidb \
  pingcap/tikv:latest \
  --addr="0.0.0.0:20161" \
  --advertise-addr="127.0.0.1:20161" \
  --data-dir="/tikv2" \
  --pd="127.0.0.1:2379"
  
docker run -d --name tikv3 \
  -p 20162:20162 \
  --ulimit nofile=1000000:1000000 \
  -v /etc/localtime:/etc/localtime:ro \
  -v /Users/wangmaosong/data/docker/tidb/tikv3:/tidb \
  pingcap/tikv:latest \
  --addr="0.0.0.0:20162" \
  --advertise-addr="127.0.0.1:20162" \
  --data-dir="/tikv3" \
  --pd="127.0.0.1:2379"
```


```shell
docker run -d --name tidb \
  -p 4000:4000 \
  -p 10080:10080 \
  -v /etc/localtime:/etc/localtime:ro \
  pingcap/tidb:latest \
  --store=tikv \
  --path="127.0.0.1:2379"
```