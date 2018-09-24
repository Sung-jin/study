#4-1
score = int(input())

if score >= 90:
    print("A")
elif score >= 80:
    print("B")
elif score >= 70:
    print("C")
elif score >= 60:
    print("D")
else:
    print("F")

#4-2
num = list(map(int, input().split()))
num.sort()
print(num[1])

#4-3
N, X = map(int, input().split())
Num = list(map(int, input().split()))

a = [i for i in Num if i < X]
for i in a:
    print(i, end=" ")

#4-4
N = int(input())
score = list(map(int, input().split()))
sum = 0

score.sort(reverse = True)

score1 = [i / score[0] * 100 for i in score if i <= score[0]]

for i in score1:
    sum += i
print(sum / len(score))

#4-5
C = int(input())

for i in range(0, C):
    total = cnt = 0
    test = list(map(int, input().split()))
    for j in range(1, len(test)):
        total += test[j]
    avg = total / test[0]

    for k in range(1, len(test)):
        if test[k] > avg:
            cnt += 1

    print("{:.3f}".format(cnt / test[0] * 100) + "%")

#4-6
N = temp = input()
next, temp
cnt = 1

while 1:
    if int(temp) < 10:
        first = 0 + int(temp)
        temp = str(int(temp) % 10) + str(first)
    else:
        num = list(temp)
        next = int(num[0]) + int(num[1])
        temp = num[1] + str(next % 10)

    if int(temp) == int(N):
        print(cnt)
        break;
    else:
        cnt += 1
