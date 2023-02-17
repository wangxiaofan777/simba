person = {'name': 'wms', 'age': 30}

print(list(person))

print(sorted(person))

print(person)

print('name' in person)

del person['name']

print('name' not in person)
print(person)

dict = dict([('name', 'wms'), ('age', 30)])
print(dict)

# dict1 = dict(name=11, age=30)
# print(dict1)

dict2 = {x: x ** 2 for x in (2, 4, 6)}
print(dict2)


knights = {'gallahad': 'the pure', 'robin': 'the brave'}
for k,v in knights.items():
    print(k ,v)

for i,v in enumerate(knights):
    print(i, v)