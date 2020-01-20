package topicmanager;

import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import publisher.Publisher;
import publisher.PublisherImpl;
import subscriber.Subscriber;

public class TopicManagerImpl implements TopicManager {

  private Map<Topic, Publisher> topicMap;

  public TopicManagerImpl() {
    topicMap = new HashMap<Topic, Publisher>();
  }

  @Override
  public Publisher addPublisherToTopic(Topic topic) {
    Publisher publisher = new PublisherImpl(topic);
    if(topicMap.containsKey(topic))
    {
        publisher = topicMap.get(topic);
        
    }
    else 
    {
        topicMap.put(topic, publisher);
    }
    publisher.incPublishers();
    return publisher;
  }

  @Override
  public void removePublisherFromTopic(Topic topic) {
    if(topicMap.containsKey(topic))
    {
        topicMap.remove(topic);
    }
  }

  @Override
  public Topic_check isTopic(Topic topic) {
    Topic_check tc = new Topic_check(topic,true);
      if(topicMap.containsKey(topic))
      {
          tc.isOpen = true ; 
      }
      else 
      {
          tc.isOpen = false ;
      }
      return tc ; 
  }

  @Override
  public List<Topic> topics() {
    List topics = new ArrayList <Topic>();
    Iterator it = topicMap.entrySet().iterator();
    while(it.hasNext())
    {
       Map.Entry keyvalue = (Map.Entry) it.next();
       topics.add((Topic) keyvalue.getKey());
    }
    return topics ;
  }
  

  @Override
  public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
    Subscription_check.Result result = Subscription_check.Result.NO_TOPIC;
    Subscription_check sc = new Subscription_check(topic, result);
    if(isTopic(topic).isOpen)
    {
       Publisher publisher = new PublisherImpl(topic);
       publisher.attachSubscriber(subscriber);
       sc.result = sc.result.OKAY;
       
    }
    return sc ;
  }

  @Override
  public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
    Subscription_check.Result result = Subscription_check.Result.NO_TOPIC;
    Subscription_check sc = new Subscription_check(topic, result);
    if(isTopic(topic).isOpen)
    {
        Publisher publisher = new PublisherImpl(topic);
       publisher.detachSubscriber(subscriber);
        sc.result = sc.result.NO_SUBSCRIPTION;
    }
    return sc ;
  }
  
  public Publisher publisher(Topic topic){
    return topicMap.get(topic);
  }
  
}
