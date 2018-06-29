package mytest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class firstTest {

	public static void main(String[] args) {
		//连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        jedis.auth("123");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set("testKey", "myKey");
        System.out.println("redis存储的字符串为: "+ jedis.get("testKey"));
        //存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }
        Set<String> keys = jedis.keys("*"); 
        Iterator<String> it=keys.iterator() ;  
        List<String> l=new ArrayList<String>();
        while(it.hasNext()){
            String key = it.next();
            l.add(key); 
        }
        System.out.println(l);
        
        long start = System.currentTimeMillis();  
        for(int i=0; i<100000; i++) {  
            jedis.lpush("key", ""+i);  
        }  
        long end = System.currentTimeMillis();  
        System.out.println(end-start);  
        jedis.flushAll(); 
        
        long start2 = System.currentTimeMillis();  
        Pipeline pipeline = jedis.pipelined();  
        for(int i=0; i<100000; i++) {  
            pipeline.lpush("key", ""+i);  
        }  
        List<Object> list2 = pipeline.syncAndReturnAll();  
        long end2 = System.currentTimeMillis();  
        System.out.println(end2-start2);  
        jedis.flushAll();
	}

}
