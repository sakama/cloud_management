# AWS Management Plugin for Jenkins

#What's this?#

----------
Jenkinsのビルド処理において、AWS ([Amazon Web Service](http://aws.amazon.com/)) 上にテストのためのサーバをセットアップするプラグインです。

Chef、Fabric両対応の予定。Puppetは周りにニーズがないので予定なし。



# How to install #

----------

公式プラグインではないので以下の手順でパッケージ化した後、Jenkinsの管理画面からアップロードしてください。

    mvn clean package
    
で.hpiファイルを作成。その後Jenkinsの管理画面＞プラグインの管理＞高度な設定からアップロード。


# How to use #

----------

TODO そのうち書く



# Development #

----------

    mvn hpi:run
でローカルのJettyサーバ上でJenkinsが動作します。http://localhost:8080にアクセスして下さい。既にスタンドアローンのJenkinsをインストールしていたりして8080番ポートが使用されている場合、実行できずにエラーとなります。

## License ##

----------

TODO そのうち考える
