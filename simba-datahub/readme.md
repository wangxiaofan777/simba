### 安裝Python3

* pip下载whl太慢了

```shell
mkdir ~/.pip
echo '[global]index-url = https://pypi.tuna.tsinghua.edu.cn/simple' >> ~/.pip/pip.conf
```

* windows

```shell
python -m pip install --upgrade pip wheel setuptools
python -m pip uninstall datahub acryl-datahub || true  
python -m pip install --upgrade acryl-datahub
datahub version
```

* Linux

```shell
python3 -m pip install --upgrade pip wheel setuptools
python3 -m pip uninstall datahub acryl-datahub || true  # sanity check - ok if it fails
python3 -m pip install --upgrade acryl-datahub
datahub version
```

### 启动

```shell
datahub docker quick
```

停止

```shell
datahub docker quick --stop
```

```shell
pip install 'acryl-datahub[datahub-rest]'
pip install 'acryl-datahub[mysql]'
```

* recipe.yml

```yaml
source:
  type: mysql
  config:
    username: root
    password: Lygr@0907
    database: oc_extract_tool_web_service_uat
    host_port: '10.50.30.178:13306'
sink:
  type: 'datahub-rest'
  config:
    server: 'http://localhost:8080'
```

* 提数MySQL数据

```shell
datahub ingest -c recipe.yml
```

* 浏览器访问。datahub/datahub
```shell
http://10.50.30.177:9002
```