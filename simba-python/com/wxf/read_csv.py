import pandas as pd
import csv

# CSV文件名
csv_filename = "/Users/wangmaosong/data/phzx-1000.csv"

# pandas读取csv
csv_data = pd.read_csv(csv_filename)
print(csv_data)

# pandas读取表格
csv_data = pd.read_table(csv_filename, ",")
print(csv_data)

# 标准库读取
csv_data = csv.reader(open(csv_filename))
for row in csv_data:
    print(row)
