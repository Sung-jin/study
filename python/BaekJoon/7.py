# 7-1 https://www.acmicpc.net/problem/11654
print(ord(input()))

# 7-2 https://www.acmicpc.net/problem/10809
S = list(input())
index = {}
for i in range(97, 123):
    index[chr(i)] = -1

for i in S:
    if index[i] == -1:
        index[i] = S.index(i)

for i in index:
    print(index[i], end=" ")

#딕셔너리로 a~z까지 카운팅을 만들어 준다
#확인할 문자열을 리스트로 바꾼후 그 리스트의 인덱스를 가져와서
#딕셔너리에 대입시켜준다

# 7-3 https://www.acmicpc.net/problem/2675
count = int(input())

for i in range(0, count):
    inputs = input().split(' ')
    R = inputs[0]
    S = list(inputs[1])

    for j in range(0, len(S)):
        print(S[j]*int(R), end="")
    print()
#그지같은... 입력부분에서 한줄 안띄어졌다고 틀리네
#count는 전체 로직 횟수
#띄어쓰기 기준으로 문자를 나눈 후 R숫자만큼 각각 문자를 반복하여 출력

# 7-4 https://www.acmicpc.net/problem/1157
str = list(input())
index = {}
ch = ''
count = 0
#확인할 입력 문자 리스트, 카운팅 딕셔너리, 가장 많이 쓰인 문자, 가장 많이 쓰인 카운트
for i in range(65, 91):
    index[chr(i)] = 0
#A~Z까지 카운트 딕셔너리를 0으로 초기화
for i in range(0, len(str)):
    #입력받은 문자를 싹 돌리면서 확인
    #확인중인 문자를 대문자로 변환시켜서 딕셔너리에 카운트 +1
    index[str[i].upper()] += 1
    #카운트 증가 후 그 카운트가 지금 최고 많이 나온 카운트보다 많을 경우
    #이 문자가 제일 많이 나온 것이므로 갱신
    #지금 확인중인 문자의 카운트가 현재 최대 카운트랑 같으면 가장 많이 나온게 중복
    #고로 가장 많이 나온 문자를 ?로 초기화
    if count < index[str[i].upper()]:
        count = index[str[i].upper()]
        ch = str[i].upper()
    elif count == index[str[i].upper()]:
        ch = '?'

print(ch)

# 7-5 https://www.acmicpc.net/problem/1316
#문제가 이해가 안되서 벙쪘네
#테스트 케이스 전체 중 그룹 단어가 몇개인가를 알아야 함
#지금 단어에서 그룹단어로 존재하는 글자수를 출력하는게 아니라
N = int(input())
count = 0
pre_ch = ''
index = {}

for i in range(0, N):
    for i in range(97, 123):
        index[chr(i)] = False

    str = list(input())
    pre_ch = str[0]
    index[str[0]] = True
    if len(str) == 1:
        count += 1
    else:
        for j in range(1, len(str)):
            if pre_ch != str[j] and index[str[j]] == True:
                break;
            elif j == len(str) - 1:
                count += 1
            else:
                pre_ch = str[j]
                index[str[j]] = True

print(count)
#모든 딕셔너리를 다시 False로 만들어주려고 origin_index도 만들었는데
#처음에 초기화 할 때 같은 데이터가 들어가서 그런지 같은 주소를 참조하는거 같다
#저 오리지널 인덱스로 인덱스를 덮어씌울려고하면 그 이전에 인덱스에서
#값이 변하면서 오리지널도 같이 변하는거 같음
#정확한 검색어는 모르겠어서 그냥 한번 뤂 돌릴때마다 원본을 초기화 하는 방식으로 바꿈
#이거땜시 겁나 삽질했네
#방식은 문자를 한글자씩 확인하며 확인된 이전 글자를 저장하고
#지금 확인하는 글자랑 이전 글자랑 다르고 지금 확인하는 글자가
#이전에 나왔었던 글자라면 이 글자는 그룹단어가 아니다

# 7-6 https://www.acmicpc.net/problem/1152
#6-1 이랑 문제 같음
#6-1에서 한줄로 변경함! ㅎ_ㅎ
print(len(list(input().split())))

# 7-7 https://www.acmicpc.net/problem/2908
num1, num2 = input().split()

num1 = num1[::-1]
num2 = num2[::-1]

print(num1) if num1 > num2 else print(num2)

# 7-8 https://www.acmicpc.net/problem/5622
time = 0
num = {}

for i in range(2, 8):
    num[chr(65 + (i * 3 - 6))] = i + 1
    num[chr(65 + (i * 3 - 5))] = i + 1
    num[chr(65 + (i * 3 - 4))] = i + 1
num[chr(83)] = 8
num[chr(84)] = 9
num[chr(85)] = 9
num[chr(86)] = 9
num[chr(87)] = 10
num[chr(88)] = 10
num[chr(89)] = 10
num[chr(90)] = 10
#아오.. 하필 7, 9 중간에 4개가 들어가버려서 노가다로 인덱싱 디렉토리

input = list(input())

for i in input:
    time += num[i]
print (time)
#1을 거는데 2초, 2를 거는데 3초... 즉, 걸려는 숫자에 +1을 해서 딕셔너리를 만든다
#입력받은 문자를 딕셔너리랑 맵핑하여 걸리는 시간을 구한다

# 7-9 https://www.acmicpc.net/problem/2941
croatia = ['c=', 'c-', 'dz=', 'd-', 'lj	', 'nj', 's=', 'z=']
input = input()

for i in range(len(croatia)):
    input.replace(croatia[i], '1')

print(input)
croatia = ['c=', 'c-', 'dz=', 'd-', 'lj	', 'nj', 's=', 'z=']
input = input()

for i in range(len(croatia)):
    input = input.replace(croatia[i], '1')

print(input)
