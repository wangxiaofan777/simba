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
python3 -m venv tutorial-env

source tutorial-env/bin/activate



python3 -m pip install --upgrade pip wheel setuptools
python3 -m pip uninstall datahub acryl-datahub || true  # sanity check - ok if it fails
python3 -m pip install --upgrade acryl-datahub
datahub version
```

### 启动

```shell
datahub docker quickstart
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

```shell
pip install 'acryl-datahub[hive]'

```

```yaml

source:
  type: hive
  config:
    # Coordinates
    host_port: 10.50.30.188:10000
    # database: DemoDatabase # optional, if not specified, ingests from all databases

    # Credentials
    # username: user # optional
    # password: pass # optional

    # For more details on authentication, see the PyHive docs:
    # https://github.com/dropbox/PyHive#passing-session-configuration.
    # LDAP, Kerberos, etc. are supported using connect_args, which can be
    # added under the `options` config parameter.
    options:
      connect_args:
        auth: KERBEROS
        kerberos_service_name: hive
    scheme: 'hive+http' # set this if Thrift should use the HTTP transport
    #scheme: 'hive+https' # set this if Thrift should use the HTTP with SSL transport
    #scheme: 'sparksql' # set this for Spark Thrift Server

sink:
  type: 'datahub-rest'
  config:
    server: 'http://localhost:8080'


```

* 提数MySQL数据

```shell
datahub ingest -c recipe.yml
```

* 导入样本数据

```shell
datahub docker ingest-sample-data
```

* 浏览器访问。datahub/datahub

```shell
http://10.50.30.177:9002
```