### DefaultMessageCodesResolver 의 기본 메시지 생성 규칙

```
-- 객체 오류

1. code + "." + object name
2. code

ex) 오류 코드: reuqired, object name: item
1. required.item
2. required
```

```
--- 필드 오류

1. code + "." + object name + "." + field
2. code + "." + field
3. code + "." + field type
4. code

ex) 오류 코드: typeMismatch, object name "user", field "age", field type: int
1. "typeMismatch.user.age"
2. "typeMismatch.age"
3. "typeMismatch.int"
4. "typeMismatch"
```