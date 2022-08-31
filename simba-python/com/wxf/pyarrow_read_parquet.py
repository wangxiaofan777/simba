import pyarrow as pa
import pa.parquet as pq

pq.read_tablle(
    u'C:/Users/oceanum/Desktop/data/part-00000-f78a954a-89ba-463e-b449-3a96acae43f5.snappy.parquet').to_pandas
