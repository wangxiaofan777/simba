try:
    x = int(input("Please enter a number:"))
except ValueError:
    print("Error")

# 异常后会继续执行代码
print(x)
