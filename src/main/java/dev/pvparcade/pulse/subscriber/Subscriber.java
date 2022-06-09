package dev.pvparcade.pulse.subscriber;

import dev.pvparcade.pulse.Pulse;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber {
  private Pulse pulse;
  private String channel;

  private Jedis jedis;
  private JedisPubSub pubSub;

  public Subscriber(Pulse pulse, String channel, SubscriptionHandler handler) {
    this.pulse = pulse;
    this.channel = channel;

    this.pubSub = new JedisPubSub() {
      @Override
      public void onMessage(String channel, String message) {
        handler.handleMessage(message);
      }
    };

    this.connect();
    this.subscribe();
  }

  private void connect() {
    this.jedis = new Jedis(this.pulse.getHost(), this.pulse.getPort());

    if (this.pulse.isAuthenticate()) {
      this.jedis.auth(this.pulse.getPassword());
    }
  }

  private void subscribe() {
    new Thread(() -> {
      try {
        this.jedis.subscribe(this.pubSub, this.channel);
      } catch (Exception exception) {
        exception.printStackTrace();

        this.close();
        this.connect();
      }
    }).start();
  }

  public void close() {
    this.pubSub.unsubscribe();
    this.jedis.close();
  }
}
