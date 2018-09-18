# Python

python은 3.~ 버전과 2.~버전이 있으며, 버전에 따라 호환되는게 다르다
```python
print('Hello Python!')
```

PEP8
* [링크1](https://spoqa.github.io/2012/08/03/about-python-coding-convention.html) [링크2](https://b.luavis.kr/python/python-convention) [링크3](https://wayhome25.github.io/python/2017/05/04/pep8/) [공식문서](https://www.python.org/dev/peps/pep-0008/)
* Python Enhance Proposal
* 파이썬을 개선하기 위한 개선 제안서
  * 새로운 기능이나 구현을 제안하는 Standard Track
  * 파이썬의 디자인 이슈나 일반적인 지침, 혹은 커뮤니티에 정보를 제안하는 Informational
  * 파이썬 개발 과정의 개선을 제안하는 Process
* 파이썬 언어의 컨벤션을 이러한 제안서로 나타내고 있는 것을 PEP8

python의 3.~와 2.~에서의 차이를 한번 알아봅시다
```python
3/2
# 3.~에서는 1.5 / 2.~에서는 1
# 2.~에서 int / int는 항상 int
# 2.~에서 1.5가 나오게 하기 위해서는 최소 한쪽에서 .0을 붙여줘서 float으로 만들어 주거나
# from__future__import division을 최상단에 삽입하여 해결할 수 있다

# 2.~에서의 print는 statement
print "Hello, Python!"
print "Hello", "Python!"
print "Hello,",;print "Python"
# 모두 Hello, Python 이 출력이 된다

# 3.~에서의 print는 function이다
print("Hello, Python")
print("Hello,", "Python")
print("Hello,", end=" ");print("Python")
# 모두 Hello, python 이 출력이된다

```

[dict / Extended iterable unpack / concurrent.Futures / VirtualEnv의 내장 / 더 자세한 예외 표시  / Tulip / super() 등등 많은 차이가 존재](https://tech.ssut.me/2015/07/24/python-3-is-the-future/)

print() - 출력
> end - 출력에 끝을 지정할 수 있다<br>
이스케이프 코드 (\\n, \\t등등)

```python
print('Hello', end=' World')
#Hello World
print('Hello World\nBye\tWorld')
#Hello World
#Bye  World
```

input() - 입력

파이썬에 변수는 유니코드 방식으로 사용
> 변수명을 한글도 가능하고 영어의 대/소문자도 구분된다<br>
또한 숫자로 시작될 수 없고 띄어쓰기도 안된다

데이터 타입
* Numeric     정수 / 실수
* String      문자열
* Boolean     true / false
* List        변경 가능한 배열
* Tuple       변경 불가능한 배열 (immutable)
* Dictionary  Key : Value로 되어있는 Json과 같은 배열

type(데이터) - 객체의 타입을 알 수 있음

str(값) - String으로 형 변환

List(String) - 문자열에 들어있는 데이터가 다 분리되어 List로 형 변환

\# - 주석

"", ''으로 문자열을 만듬

'''여러 줄 문자열''', """여러 줄 문자열""" 도 가능
> """#뿐만 아니라 이것을 저장하지 않으면 주석으로도 사용할 수 있다"""<br>
'''이것을 Docstring이라 한다'''

%s - 문자열 / %d - 정수 / %f - 실수 (c-style)
> python에서는 %~로도 가능하지만 {}.format()을 사용 할 수 있다<br>
'Hello {}'.format('World')         - 한개<br>
'{} % {} = {}'.format(1, 2, 1 * 2) - 여러개<br>
'{1} % {0} = {2}'.format(1, 2, 1 * 2)<br>
위와같이 순서를 지정할 수 있다

index - 순번
> 인덱스를 통해 접근을 할 수 있음<br>


slice - 잘라내는 내장 함수(문자 자체를 교환하는게 아니라 복사해서 잘라내는 작업을 한다)

split - 조건을 기준으로 잘라내는 리스트 함수(비어있으면 공백을 기준)

sort - 정렬하는 리스트 함수

count - 요소가 몇개 있는가를 알 수 있는 리스트 함수

len - 리스트에 몇개의 요소가 있는가를 알 수 있는 내장 함수
```python
foo = 'Hello World'
bar = 'Hello,World'
print(foo[6:])
#World
foo.split()
#['Hello', 'World']
bar.split(',')
#['Hello', 'World']

#List
name = ['철수', '영희', '바둑이']
name.append('짱구')
#['철수', '영희', '바둑이', '짱구']

#인덱싱
name[0]
#'철수'
name[1]
#'영희'
del name[4]
#['철수', '영희', '바둑이']

randomNum = [1, 3, 2, 6, 5, 4]
randomNum.sort()
#[1, 2, 3, 4, 5, 6]

name.append('철수')
name.count('철수')
#2
name.count('영희')
#1

len(randomNum)
#6

#튜플은 한번 초기화 한 후 변경이 불가능 하다
tuple_Num = (1, 2, 3)
#(1, 2, 3)
tuple_Num2 = 1, 2, 3
#(1, 2, 3)

my_dict = {}
type(my_dict) #<class 'dict'>
#dictionary를 만들때는 중광호로 만들면 된다
my_dict[0] = 'a'
my_dict
#{0: 'a'}
my_dict['b'] = 2
my_dict
#{0: 'a', 'b': 2}
print(my_dict['b'])
#2
del my_dict['1']
my_dict
#{'b': 2}
my_dict['c'] = 3
my_dict['d'] = 4

#Dictionary의 메서드 values(), keys(), items()
for std in my_dict.values():
  print(std)
#2 3 4
for std in my_dict.keys():
  print(std)
#b c d
for key, val in my_dict.items():
  print(key + ' : ' + val)
#b : 2 c : 3 d : 4
```

Packing / Unpacking
> 예를들어 튜플을 ()로 묶지 않고 넣더라도 하나로 묶여서 반환되는데 이걸 Packing<br>
packing_test = 1, 2, 3 = (1, 2, 3)<br>
리스트, 튜플 등의 요소들을 나눠서 저장 할 수 있는데 이를 Unpacking<br>
num1, num2, num3 = packing_test<br>
num1 = 1 / num2 = 2 / num3 = 3<br>
패킹과 언패킹을 같이 쓰는 예제<br>
num1, num2 = num2, num1 -> num1 = 2 / num2 = 1<br>

반복문 - for / while
> for 변수 in 컨테이너(리스트, 튜플 등 들어가 있는 데이터를 가지고 진행) :<br>
  코드 블럭<br>
들여쓰기 부분에서 띄어쓰기, 탭 다 상관없으나 코드 블럭에서 사용한게 다르다면 에러가 출력된다<br>
그리고 탭과 띄어쓰기 둘다 가능하지만 띄어쓰기 4개를 권장한다<br>
while 조건: 코드 블럭<br>

continue / break
> continue를 만나면 만난 지점 이후의 코드 블럭은 실행하지 않고 조건문으로 돌아간다<br>
break를 만나면 그 반복문에서 빠져나온다

조건문 - if ... elif ... else
> if(조건): 코드 블럭<br>
조건이 참일 시 코드 블럭이 실행됨<br>

range() - 범위를 지정하여 그 범위만큼 정수 리턴 내장 함수

comprehension - 리스트를 만들 때 for문과 if문 등을 이용하여 간결하게 필요한 데이터를 만드는 것
```python
count = [1, 2, 3, 4, 5]
for idx in count
    print(idx)
#1 2 3 4 5
for idx in range(3, 5)
    print(idx)
#3 4

numbers = range(1,11)
odd_numbers = []

for number in numbers:
  if number %2 == 1:
    odd_numbers.append(number)

#이 5줄과 아래 한줄의 결과는 같다
#크으.. 파이썬 이런게 겁나 신기하단말야
odd_comprehension = [number for number in numbers if number % 2 == 1]
```

연산자
> 할당 연산자 =, +=, -=, \*=, /=<br>
산술 연산자 +, -, \*, /, \*\*, //, %<br>
문자열 연산자 +, \*<br>
비교 연산자 ==, !=, >, <, >=, <=<br>
논리 연산자 and(&&가 아니다), or(||가 아니다), !<br>
멤버십 연산자 in, not in<br>
>>  name = ['철수', '영희', '바둑이']<br>
'철수' in name -> true<br>
'짱꾸' in name -> false<br>
'짱구' not in name => true<br>

함수
* 내장함수         - 기본으로 내장되어 있는 함수
* 모듈의 함수      - import를 해서 쓸 수 있는 함수
* 사용자 정의 함수 - 사용자가 직접 정의해서 만든 함수
> def 사용자 정의 함수이름(인자...):<br>
    코드블럭<br>
    return val... (필수는 아님)<br>
함수의 리턴값을 여러개로 할 수 있으며, 이때는 자동으로 패킹과 언팩킹이 된다<br>

```python
import random
#random 모듈사용

name = ['철수', '영희', '바둑이']

def Sum_Mul(num1, num2):
  return num1 + num2, num1 * num2;

my_Sum, my_Mul = Sum_Mul(1, 2)
#my_Sum = 3 / my_Mul = 2
#리턴으로 넘겨줄 때는 튜플로 자동으로 패킹이 되어지고 my_Sum과 my_Mul에 대입할 때는 자동으로 언패킹이 된다

print(random.choice(name))
#철수, 영희, 바둑이 중에 랜덤으로 하나가 출력됨
print(random.sample(name, 2))
#철수, 영희, 바둑이 중에 랜덤으로 2개가 출력됨
#두번째로 들어간 인자값 수 만큼 램덤으로 출력되는 모듈함수
print(random.randint(8,10))
#첫번째 인자와 두번째 인자 사이에 랜덤으로 하나 출력됨
```

객체

파이썬에서는 대부분의 모든 것들이 객체이다

함수와 데이로 구성되어 있다
