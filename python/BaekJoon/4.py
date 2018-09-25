#4-1 https://www.acmicpc.net/problem/9498
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

#4-2 https://www.acmicpc.net/problem/10817
num = list(map(int, input().split()))
num.sort()
print(num[1])
#숫자 3개를 입력받아 리스트로 만든 후 내림차순 정렬하여 인덱스1을 출력하면 2번째로 큰 숫자이다

#4-3 https://www.acmicpc.net/problem/10871
N, X = map(int, input().split())
Num = list(map(int, input().split()))

a = [i for i in Num if i < X]
for i in a:
    print(i, end=" ")

#4-4 https://www.acmicpc.net/problem/1546
N = int(input())
score = list(map(int, input().split()))
sum = 0
#점수들을 리스트로 받아온다

score.sort(reverse = True)
#오름차순으로 정렬

score1 = [i / score[0] * 100 for i in score if i <= score[0]]
#오름차순으로 정렬하였으므로 첫번째 값이 가장 큰 점수이다
#이 점수를 기준으로 첫번째 값부터 점수를 변환시켜 새로운 리스트를 만든 후
#리스트의 각 요소들을 더한 후 리스트의 크기만큼 나눠준다

for i in score1:
    sum += i
print(sum / len(score))

#4-5 https://www.acmicpc.net/problem/4344
C = int(input())
#입력받은 표본의 수

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
    #표본의 수만큼 반복을 돌린다
    #각 표본마다 모든 점수를 합한 후 평균을 구하고 평균을 넘은 사람의 수를 구하고
    #전체 수로 나누어서 평균을 넘긴 사람을 소숫점 3자리까지 %로 표현

#4-6 https://www.acmicpc.net/problem/1110
N = temp = input()
next, temp
cnt = 1
#0~99
#10보다 작을 경우 각 자리를 더해서 나온 값과 더하기 전 값은 같다
#즉, 같은 숫자 2개로 구성된 숫자가 됨
#그 외에는 문제에서 원하는대로 숫자를 각 자리별로 리스트화 시킨 후 각각 더하고
#더한 값 중 1의 자리와 더하기 전 1의 자리를 합쳐서 새로운 숫자를 만들고
#이 숫자가 맨 처음 받아낸 숫자와 같은지 확인하고 같으면 지금까지 카운팅 한 숫자를 출력하고
#같지 않다면 카운트를 1 증가시키고 찾을때까지 반복한다

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
