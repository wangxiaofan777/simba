
# 3.10 support
def http_error(status):
    match status:
        case 400:
            print('bad request')
        case 404:
            print('Not found')
        case _:
            print('something is wrong')


def point(point):
    match point:
        case (0, 0):
            print("Origin")
        case (0, y):
            print(f"Y={y}")
        case (x, 0):
            print(f"X={x}")
        case (x, y):
            print(f"X={x}, Y={y}")
        case _:
            raise ValueError("Not a point")
