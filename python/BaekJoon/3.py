#3-1
N = int(input())

for i in range(1, N+1):
    print(i)

#3-2
N = int(input())

for i in range(N, 0, -1):
    print(i)

#3-3
N = int(input())

for i in range(1, 10):
    print('{} * {} = {}'.format(N, i, N*i))

#3-4
N = int(input())

for i in range(1, N+1):
    print('*'*i)

#3-5
N = int(input())

for i in range(1, N+1):
    print(' '*(N-i) + '*'*i)

#3-6
N = int(input())

for i in range(N, 0, -1):
    print('*'*i)

#3-7
N = int(input())

for i in range(N, 0, -1):
    print(' '*(N-i) + '*'*i)

#3-8
import datetime

month, day = map(int, input().split())

date_Data = datetime.datetime(2007, month, day)

print(date_Data.strftime("%a").upper())

#3-9
num = int(input())
sum = 0

for i in range(1, num+1):
    sum += i

print(sum)

#3-10
N = int(input())
nums = list(input())
sum = 0

for i in range(0, N):
    sum += int(nums[i])

print(sum)

#3-11
str = input()
for std in [str[i:i+10] for i in range(0, len(str), 10)]:
    print(std)
#1차적으로 str로 받아온 문자열에서 10개씩 잘라내어 리스트로 만든 후 그 리스트의 데이터들을 하나씩 불러와 하나씩 출력

#3-12
import sys
T = int(sys.stdin.readline())

for i in range(0, T):
    A, B = map(int, sys.stdin.readline().split())
    print(A + B)
#파이썬은 동적변수
#https://stackoverflow.com/questions/6966194/reading-a-line-from-standard-input-in-python
#input()은 반환하는 문자열에서 후행 줄 바꿈 문자를 제거하고 readline모듈이 로드 된 경우 기록을 지원
#readline()은 선택적 size 인수를 취하고 후행 줄 바꿈 문자를 제거하지 않으며 기록도 지원하지 않는다
#http://tkql.tistory.com/37
#상황에 따라서 써야할 듯, 확실히 input()과 sys.stdin.readline()의 속도 차이는 존재
#그 외에도 상황에 따라 자료구조를 선택하여 사용하면 속도 차이가 많이 남
#python자체가 c, 심지어 java보다 느리다면 느린 인터프린터 언어지만 자료구조와 readline()과 같은 것들을 적절히 잘 사용한다면 충분히 빠르게, 그리고 간단하게 짤 수 있다
