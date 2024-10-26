# rose-framework-spigot
마인크래프트 플러그인을 개발할 때 개인적으로 사용하려고 개발한 라이브러입니다.<br>
해당 깃헙 레포지토리에 올라오는 모든 플러그인에는 해당 라이브러리가 사용됩니다.

# 연동 방법
아래는 플러그인과 연동할 수 있는 방법들입니다.<br>
해당 라이브러리는 Maven Central(중앙 저장소)에 배포되어 있습니다.

#### Maven
```xml
<dependency>
    <groupId>dev.lone64</groupId>
    <artifactId>rose-framework-spigot</artifactId>
    <version>VERSION</version>
</dependency>
```

#### Gradle
```Groovy
implementation 'dev.lone64:rose-framework-spigot:VERSION'
```

#### Gradle Kotlin
```kotlin
implementation("dev.lone64:rose-framework-spigot:VERSION")
```

<br>

# 사용 방법
아래는 라이브러리를 플러그인 내에서 사용하는 방법입니다.

```java
public class Main extends RoseModule {
    
    @Override
    public void onInit() { }
    
    @Override
    public void onStart() { }
    
    @Override
    public void onStop() { }
    
}
```

```java
Spigot.async(Runnable);
Spigot.sync(Runnable);

Spigot.asyncLater(Runnable, Long);
Spigot.syncLater(Runnable, Long);

Spigot.asyncTimer(Runnable, Long, Long);
Spigot.syncTimer(Runnable, Long, Long);
```