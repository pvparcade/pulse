# Pulse
A simple and easy to use Redis pub/sub helper

## How to use Pulse as a dependency?
Add this to your Gradle build config:
```
repositories {
    maven("https://repo.blueoxygen.net/releases")
}

dependencies {
    implementation("dev.pvparcade:pulse:1.0")
}
```

or in your Maven build config:

```
<repositories>
    <repository>
      <id>blueoxygen-releases</id>
      <name>BlueOxygen Repository</name>
      <url>https://repo.blueoxygen.net/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>dev.pvparcade</groupId>
        <artifactId>pulse</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```

## How to use Pulse?
```java
public class Example {
  private Pulse pulse;

  private Publisher publisher;
  private Subscriber subscriber;

  public void init() {
    this.pulse = new Pulse("127.0.0.1", 1337, "", false);

    this.publisher = new Publisher(this.pulse, "example:hello");
    this.subscriber = new Subscriber(this.pulse, "example:hello", new ExampleSubscriptionHandler());

    this.publisher.publish("Hello, world!");
  }

  public class ExampleSubscriptionHandler implements SubscriptionHandler {

    @Override
    public void handleMessage(String message) {
      System.out.println(String.format("Received message: %s", message));
    }
  }
}
```
