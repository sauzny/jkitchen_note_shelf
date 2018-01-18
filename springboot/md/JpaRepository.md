# JpaRepository

JpaRepository支持接口规范方法名查询。意思是如果在接口中定义的查询方法符合它的命名规则，就可以不用写实现，目前支持的关键字如下。

Keyword | 举例 | JPQL snippet
-|-|-
IsNotNull | findByAgeNotNull | ...  where x.age not null
Like | findByNameLike | ...  where x.name like ?1
NotLike | findByNameNotLike | ...  where x.name not like ?1
StartingWith | findByNameStartingWith | ...  where x.name like ?1(parameter bound with appended %)
EndingWith | findByNameEndingWith | ...  where x.name like ?1(parameter bound with prepended %)
Containing | findByNameContaining | ...  where x.name like ?1(parameter bound wrapped in %)
OrderBy | findByAgeOrderByName | ...  where x.age = ?1 order by x.name desc
Not | findByNameNot | ...  where x.name <> ?1
In | findByAgeIn | ...  where x.age in ?1
NotIn | findByAgeNotIn | ...  where x.age not in ?1
True | findByActiveTrue | ...  where x.avtive = true
Flase | findByActiveFalse | ...  where x.active = false
And  | findByNameAndAge | ...  where x.name = ?1 and x.age = ?2
Or | findByNameOrAge | ...  where x.name = ?1 or x.age = ?2
Between | findBtAgeBetween | ...  where x.age between ?1 and ?2
LessThan | findByAgeLessThan | ...  where x.age  <  ?1
GreaterThan | findByAgeGreaterThan | ...  where x.age > ?1
After/Before | ... | ...
IsNull | findByAgeIsNull | ...  where x.age is null