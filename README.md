# KarChat项目结构
## 数据访问层:dao

## 实体层:entity

## 服务层:service

## 工具类:util

##视图层:view

#注意事项:
>1.Email发送失败解决办法: <br>
找到 jdk->jre/lib/security/java.security
将第706行：jdk.tls.disabledAlgorithms=SSLv3, DSA, RSA，RC4, DES, MD5withRSA, \ <br>
改为：jdk.tls.disabledAlgorithms=RC4, DES, MD5withRSA, \

























<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
1 关于标题
# 这是 H1 <一级标题>
## 这是 H2 <二级标题>
###### 这是 H6 <六级标题>

2 文字格式<br>
**这是文字粗体格式**
<br>
*这是文字斜体格式*
<br>
~~在文字上添加删除线~~


2 关于列表
<br><br>
无序列表1
* 项目1
* 项目2
* 项目3
  <br><br>
  无序列表2
- 项目1
- 项目2
- 项目3
  <br><br>
  有序列表
1. 项目1
2. 项目2
3. 项目3
    - 二级项目Tab
    - 二级项目2

3 这是图片![绿叶](https://github.githubassets.com/images/icons/emoji/unicode/1f331.png)
<br>
4 这是链接[百度](https://www.baidu.com/)
<br>
5 引用
<br>
> 第一行引用文字
<br>

6 水平线
***


7 代码
`<hello world>`
<br>

8 代码块高亮
```
  function add(a, b){
    return a + b;
  }
```
9 表格
表头  | 表头
------------- | -------------
单元格内容  | 单元格内容
单元格内容l  | 单元格内容
