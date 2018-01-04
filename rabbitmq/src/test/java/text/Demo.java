package text;

import com.zhu.rabbitmq.Producer;
import com.zhu.rabbitmq.QueueConsumer;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by as on 2017/8/21.
 */
public class Demo {


    @Test
    public void tt() throws Exception{



        //生产者
        Producer producer = new Producer("producer", "queue");
        for (int i = 0; i < 1000000; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            //System.out.println("Message Number "+ i +" sent.");
        }

        //消费者
        QueueConsumer consumer1 = new QueueConsumer("work1","queue");
        Thread t1 = new Thread(consumer1);
        t1.start();

        QueueConsumer consumer2 = new QueueConsumer("work2","queue");
        Thread t2 = new Thread(consumer2);
        t2.start();



    }
}
