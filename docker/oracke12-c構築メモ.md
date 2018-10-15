
#  DBをコンテナ化して快適にする話（まともなやり方編） #

## 0.前提条件 ##
- docker for windows を利用している
- linux subsystem for windows を利用している

## 1.oacleプロファイルの作成 ##

 ### まずはoracleにアカウントを登録すル ###　



勤務先の情報入れるところがあるけど、別に連絡とかは来ないらしいので大丈夫。でもなんか抵抗あるので、自分は個人メールアドレスで登録した。
会社名のところとかは「自宅用」とか入れておけばいいらしい。
住所は自宅の住所を入れておけばいい。

↓プロファイルについて
https://www.oracle.com/jp/corporate/contact/contact-accounthelp-078887-ja.html

## 2.バイナリの入手 ##
 
 ### 評価版のバイナリを入手する ###

 これがないとイメージのビルドができない。
 以下からダウンロードします。
https://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html?ssSourceSiteId=null
 
11-gのexpress版のDBを取ってくればよし！

[重要]ここでダウンロードすべきは **Oracle Database Express Edition 11g Release 2 for Linux x64**。dockerはLINUX。


## 2.oracle公式からdockerビルドファイルを手に入れる ##


### ホストPC（windows）上にダウンロードしてくる  ###

linuxのなかでcloneしてもいいのだが、後々編集するのが大変なのでwindows上に入れる。こいつをlinux側から読みに行ってあげるようにする。

```cmd:cmd

cd [どっか]
git clone https://github.com/oracle/docker-images.git

```


そして先ほどインストールしたバイナリをdocker-images内のここに置く
`docker-images\OracleDatabase\SingleInstance\dockerfiles\[DB-version]`




注意：バイナリは解凍しちゃいけない。例えば、oracle-xe-11.2.0-1.0.x86_64.rpm.zipのまま。


これで準備完了。

## 3.イメージをbuildする ##

まずLINUXを立ち上げて、docker-imagesのダウンロード先に行く。

``` terminal : terminal

 cd /mnt
 cd [ダウンロード先フォルダ]/docker-images/OracleDatabase/SingleInstance/dockerfiles

```

oracleが作ってくれたビルドファイル呼ぶ

``` terminal : terminal
#help
./buildDockerImage.sh -h

#build oracle-11-g express edition 
./buildDockerImage.sh -v 11.2.0.2 -x -i

```

なぜか-iオプションをつけないとファイルが見つからないとかで落ちる。。。とりまオプション付ければよし


そしてコンテナを立ち上げる。

```terminal : terminal

#run built image
 docker run -d --shm-size=1g -e ORACLE_PWD=oracle -p 49261:1521 -v /u01/app/oracle/oradata oracle/database:11.2.0.2-xe

#show log cmd + c to escape
 docker logs -f [container_id]


 ```

 windows側からSQLPLUSで接続してみる

 ```cmd : cmd
 
 sqlplus system/oracle@//localhost:49261/ex
 
 ```

 後はDMP入れて固めればOK


以下セットアップ用SQL

``` sql : sql
CREATE TABLESPACE usr_cim
DATAFILE 'usr_index01.dbf' SIZE 100M
AUTOEXTEND ON NEXT 500K MAXSIZE 1024M;

CREATE TABLESPACE usr_index01
DATAFILE 'usr_index01.dbf' SIZE 100M
AUTOEXTEND ON NEXT 500K MAXSIZE 1024M;

CREATE TABLESPACE usr_index02
DATAFILE 'usr_index02.dbf' SIZE 100M
AUTOEXTEND ON NEXT 500K MAXSIZE 1024M;

CREATE USER cimat IDENTIFIED BY "cimat" DEFAULT TABLESPACE usr_cim TEMPORARY TABLESPACE temp;

GRANT DBA TO cimat;
GRANT UNLIMITED TABLESPACE TO cimat;
CREATE TABLESPACE usr_cim
DATAFILE 'usr_cim.dbf' SIZE 100M
AUTOEXTEND ON NEXT 500K MAXSIZE 1024M;

```
