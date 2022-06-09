package dev.pvparcade.pulse.publisher;

import dev.pvparcade.pulse.Pulse;
import lombok.AllArgsConstructor;
import redis.clients.jedis.Jedis;

@AllArgsConstructor
public class Publisher {
  private Pulse pulse;
  private String channel;

  public void publish(String message) {
    try (Jedis jedis = this.pulse.getJedis()) {
      jedis.publish(this.channel, message);
    }
  }
}
