
# jarのビルド #

## 手順 ##
1. 実行したいクラスをコンパイル

``` 
javac hoge.java
```

2. jarコマンドでjarを作成

```
jar cvf hoge.jar huga
```

- c はjarファイルを新規作成する
- v は詳細な情報を画面に出力する
- f はjarファイル名を指定する