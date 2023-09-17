redis客户端有很多 只会挑一部分实现

- Jedis：Jedis是Redis官方推荐的Java客户端之一，提供了完整的Redis命令操作和功能支持。它是一个轻量级、易于使用的库，具有良好的性能和稳定性。  
- Lettuce：Lettuce是另一个流行的Redis Java客户端，与Jedis相比，它基于Netty框架实现，提供了异步和响应式的编程模型。Lettuce支持Redis的高级功能，如集群、哨兵和Redis Streams。  
- Redisson：Redisson是一个功能丰富的Redis Java客户端和分布式对象框架。除了基本的Redis操作外，Redisson还提供了分布式锁、分布式集合、分布式对象等功能，使得在Java应用中使用Redis更加方便，锁提供的很全面 读写锁之类的也有。 
- Jedisson：Jedisson是另一个基于Jedis开发的Redis Java客户端，提供了对Redis的基本操作以及一些额外的功能，如连接池管理、对象映射等。  
- RedisTemplate：RedisTemplate是Spring Framework提供的一个Redis客户端，封装了对Redis的常见操作和功能。它与Spring集成紧密，可以与Spring的事务管理和缓存机制无缝配合使用。


