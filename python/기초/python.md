# Python 1

python은 3.~ 버전과 2.~버전이 있으며, 버전에 따라 호환되는게 다르다
```python
print('Hello Python!')
```

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
