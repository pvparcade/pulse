package dev.pvparcade.pulse;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Getter
public class Pulse {
  private String host;
  private int port;
  private String password;
  private boolean authenticate;
  private JedisPool pool;

  public Pulse(String host, int port, String password, boolean authenticate) {
    this.host = host;
    this.port = port;
    this.password = password;
    this.authenticate = authenticate;

    this.pool = new JedisPool(new JedisPoolConfig(), host, port, 0, password);
  }

  public void close() {
    this.pool.destroy();
  }

  public Jedis getJedis() {
    return this.pool.getResource();
  }
}
