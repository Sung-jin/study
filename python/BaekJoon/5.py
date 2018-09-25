#5-1 https://www.acmicpc.net/problem/4673
selfNum = {}

for i in range(1, 10001):
    selfNum[i] = False
#각 1~10000까지 키값을 부여한 후 이 값이 셀프넘버인지 아닌지 확인하는 딕셔너리 생성
#false면 셀프넘버가 아니고 true면 셀프넘버

def d(n):
    if n >= len(selfNum):
        return
    #재귀호출이므로 무한으로 갈 수 있음
    #하지만 문제에서 요구하는 숫자는 10000 이하이므로 재귀호출로 호출된 확인 될 숫자가 10000보다 작은지 확인
    else:
        nums_sum = 0
        nums = list(str(n))
        #자리수 하나하나 다 더하기 위해 리스트로 쪼갬
        for i in nums:
            nums_sum += int(i)
        nums_sum += n
        #모든 숫자를 다 더한 후 자신과 더해서 셀프넘버로 만듬
        if nums_sum >= len(selfNum) + 1 or selfNum[nums_sum] == True:
            return
            #셀프넘버로 만든 숫자를 딕셔너리 인덱스에 넣어서 이 값이 true일 경우 이미 구한 숫자이므로 그 이후에 더 구하는건 의미없는 작업
            #구해진 숫자 이후의 셀프넘버는 이미 이 숫자로 다 구한 셀프넘버들이므로 재귀호출에서 빠져나감
        else:
            selfNum[nums_sum] = True
            d(nums_sum)
            return
            #딕셔너리에 인덱스로 주어서 확인했을 경우 false면 아직 못찾은 셀프넘버이므로
            #찾았다는 표시인 true로 변환시킨 후 지금 찾은 인덱스를 다음 재귀호출 인자값으로
            #그리고 재귀호출이 끝날때 함수를 끝내기 위해 return

for i in selfNum.keys():
    d(i)
#만들어진 딕셔너리 모든 키값을 for문을 돌려서 셀프넘버 찾기 시작

for i in selfNum.keys():
    if selfNum[i] == False:
        print(i)
#셀프넘버 찾기 완료한 딕셔너리에서 false는 셀프넘버가 아니므로 false인 값만 출력

#5-2 https://www.acmicpc.net/problem/1065
N = int(input())
hansu_count = 0
#한수 카운트를 위한 변수

def hansu(n):
    if n < 100:
        print(n)
        #100 미만의 숫자는 그 숫자만큼 한수를 가지고 있다
        #예를들어 10이면 1~10까지 10개의 한수가 있다
        #한자리의 경우 자기 자신만 봤을때 무조건 한수
        #두자리의 경우 각각 자리를 나누더라도 무조건 한수가 됨
        #ex) 10 -> 1 0로 나눠지며 1이 공차인 등차수열
        #ex) 28 -> 2 8로 나눠지며 6이 공차인 등차수열
    else:
        nums = list(str(n))
        count = 0
        minus = int(nums[1]) - int(nums[0])
        #100이상부터는 공차가 존재하게 됨
        #각 자리별로 나누고 두번째 숫자와 첫번째 숫자의 차이를 공차라고 기준을 잡았을 때
        #그 이후의 자리수마다 공차가 첫번째로 나온 공차와 모두 같아야 한수가 됨
        #즉, 두번째와 첫번째의 차이를 통해 이수로 공차라고 가정하고 모두 이 공차가 되는 횟수를 숫자의 길이 -2만큼이면 한수가 되는것임
        #-2를 하는 이유는 4자리일 경우 첫번째 두번째, 두번째 세번째, 세번째 네번째의 공차가 같으면 되야 하므로 3개, 즉 자리수 -1개의 공차가 같으면 됨
        #여기에 두번째와 첫번째의 공차를 기준으로 잡고 시작하기 때문에 이 카운트의 수를 빼줘야 하므로 -2만큼이면 한수
        for i in range(2, len(nums)):
            if minus == int(nums[i]) - int(nums[i-1]):
                count += 1
        if count == len(nums) - 2:
            return 1
            #모든 조건이 만족하여 한수가 되므로 1개 추가라는 의미로 1을 리턴
        else: return 0
            #모든 조건이 불만족하여 추가가 되지 않았으므로 0을 리턴

if N < 100:
    hansu(N)
    #100보다 작으면 그 숫자만큼의 공차를 가지고 있음
else:
    for i in range(100, int(N) + 1):
        hansu_count += hansu(i)
    print(hansu_count + 99)
    #한수 함수를 그 숫자 100 이후만큼 돌린 이후 나온 한수의 카운트와 100미만의 한수 즉, 99개를 더하여 출력

#5-3 https://www.acmicpc.net/problem/2448
#겁나 고민했는데 감이 안오네
#http://lazineer.tistory.com/179
#어렵다잉
