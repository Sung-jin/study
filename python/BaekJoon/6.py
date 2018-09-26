# 6-1 https://www.acmicpc.net/problem/1152
words = list(input().split())

print(len(words))

# 6-2 https://www.acmicpc.net/problem/2577
num = 1
num_count = {}

for i in range(0, 10):
    num_count[i] = 0

for i in range(0,3):
    num *= int(input())

nums = list(str(num))

for i in range(0, len(nums)):
    num_count[int(nums[i])] += 1

for i in range(0, 10):
    print(num_count[i])
#정수 3개를 입력받고 바로바로 곱해줘서 3개의 곱해진 숫자를 구한 뒤
#리스트로 자리수마다 숫자로 쪼개고
#딕셔너리로 0~9까지에 대한 인덱싱을 해줘서 리스트로 만들어진 숫자에 해당되는 걸 키값으로
#전체 자리수만큼 딕셔너리에서 1씩 증가시켜준다

# 6-3 https://www.acmicpc.net/problem/8958
#O의 연속된 숫자에 따라 각각의 점수가 1의 공차로 증가됨
#X를 만나는 순간 초기화가 됨
#즉, n(n + 1) / 2 공식을 쓰면됨 연속된 점수를 구하고자 하면
def calc_Score(num):
    return (num * (num + 1)) / 2

for i in range(0, int(input())):
    consecutive_Num = 0
    score = 0
    result = list(input())
    #연속 맞춘 문제의 카운트와 총점, 그리고 문제 샘플 저장 리스트

    for j in range(0, len(result)):
        if result[j] == 'O':
            #맞으면 연속 맞은 카운트 1을 증가시킨다
            consecutive_Num += 1
            if j == len(result) - 1:
                #맨 마지막 확인에서 O이면 연속 카운트 증가 및 계산을 시작해야 함
                score += calc_Score(consecutive_Num)
        elif result[j] == 'X' or j == len(result) - 1:
            #틀릴때마다 연속 문제 카운트를 0으로 초기화 시키고 여기까지에 대한 점수를 총점에 더해준다
            score += calc_Score(consecutive_Num)
            consecutive_Num = 0

    print(int(score))

# 6-4 https://www.acmicpc.net/problem/2920
compare = list('12345678')
compare_R = sorted(compare, reverse = True)
play = list(input().split(' '))

print('ascending') if compare == play else print('descending') if compare_R == play else print('mixed')
#파이썬에서 3항 연산을 표현하는 방법은 and or와 if else로 표현하는게 있다
#내가 사용한 방법은 if else를 이용한 3항 연산
#else if로 사용하는 3항 연산은 결과 if 조건 else 조건이 안맞으면 실행 될 것
#즉, ascending인지 확인하고 그다음으로는 descending인지 확인하고 둘다 아니면 mixed를 출력하는 3항연산을 2번 쓴것

# 6-5 https://www.acmicpc.net/problem/10039
total = 0
count = 0
for i in range(0, 5):
    now_score = int(input())
    if now_score >= 40:
        total += now_score
    else:
        total += 40

print(int(total / 5))
