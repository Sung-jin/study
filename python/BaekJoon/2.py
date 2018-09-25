#2-1 https://www.acmicpc.net/problem/1000
num = input().split()
print(int(num[0]) + int(num[1]))

#2-2 https://www.acmicpc.net/problem/1001
num = input().split()
print(int(num[0]) - int(num[1]))

#2-3 https://www.acmicpc.net/problem/10998
num = input().split()
print(int(num[0]) * int(num[1]))

#2-4 https://www.acmicpc.net/problem/1008
num = input().split()
if(float(num[1] != 0)):
    print('%0.9f'%(float(num[0]) / float(num[1])))

#2-5 https://www.acmicpc.net/problem/10869
num = [int(x) for x in input().split()]
if(int(num[1] != 0)):
    print(num[0] + num[1])
    print(num[0] - num[1])
    print(num[0] * num[1])
    print(int(num[0] / num[1]))
    print(num[0] % num[1])

#2-6 https://www.acmicpc.net/problem/10430
num = [int(x) for x in input().split()]
print((num[0] + num[1]) % num[2])
print(((num[0] % num[2]) + (num[1] % num[2])) % num[2])
print((num[0] * num[1]) % num[2])
print(((num[0] % num[2]) * (num[1] % num[2])) % num[2])

#2-7 https://www.acmicpc.net/problem/2558
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
#문제에서는 \n을 기준으로 정수 2개라고는 하지만 확장하여
#여러개를 입력 받을 수 있도록 만듬

#2-8 https://www.acmicpc.net/problem/2839
num = int(input())

sugar_5 = int(num / 5)
#5키로로 최대한 나누고 나머지를 3키로로 나눠서 떨어지는게 제일 적은 설탕 봉지수 이므로
#일단 5로 나누어서 5로 가질수 있는 최대의 수를 알아낸다
#그후 나머지를 기준으로 3으로 나누어 떨어지면 최소 개수이고 안떨어지면 5키로 봉지 1개씩 줄이고 3으로 나누어 떨어질때까지 반복
#5키로 봉지가 0보다 작아지면 5와 3으로 나눌 수 없는 양이므로 -1을 출력

while True:
    if(sugar_5 == 0 and (num % 3) != 0):
        print(-1)
        break;

    if(((num - sugar_5 * 5) % 3) != 0):
        sugar_5 -= 1
    else:
        print(sugar_5 + int((num - sugar_5 * 5) / 3))
        break;
