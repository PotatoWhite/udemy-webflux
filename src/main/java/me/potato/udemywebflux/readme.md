M1 Macbook 에서는 Netty의 Arm Arch 지원을 위해 다음을 주입해야한다.

```groovy
// https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos
implementation 'io.netty:netty-resolver-dns-native-macos:4.1.79.Final'
```