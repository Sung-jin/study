#2-1
num = input().split()
print(int(num[0]) + int(num[1]))

#2-2
num = input().split()
print(int(num[0]) - int(num[1]))

#2-3
num = input().split()
print(int(num[0]) * int(num[1]))

#2-4
num = input().split()
if(float(num[1] != 0)):
    print('%0.9f'%(float(num[0]) / float(num[1])))

#2-5
num = [int(x) for x in input().split()]
if(int(num[1] != 0)):
    print(num[0] + num[1])
    print(num[0] - num[1])
    print(num[0] * num[1])
    print(int(num[0] / num[1]))
    print(num[0] % num[1])

#2-6
num = [int(x) for x in input().split()]
print((num[0] + num[1]) % num[2])
print(((num[0] % num[2]) + (num[1] % num[2])) % num[2])
print((num[0] * num[1]) % num[2])
print(((num[0] % num[2]) * (num[1] % num[2])) % num[2])

#2-7
num = []
sum = 0
while True:
    try:
        num.append(int(input()))
    except EOFError:
        break

for i in num:
    sum += i

print(sum)

#2-8
num = int(input())

sugar_5 = int(num / 5)

while True:
    if(sugar_5 == 0 and (num % 3) != 0):
        print(-1)
        break;

    if(((num - sugar_5 * 5) % 3) != 0):
        sugar_5 -= 1
    else:
        print(sugar_5 + int((num - sugar_5 * 5) / 3))
        break;
