package test;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import net.sf.clipsrules.jni.*;

public class ClipsAgent extends Agent {
  Environment clips;
  protected void setup() {
    try{
      clips = new Environment();
    }
    catch(Exception e){
      System.out.println("Error: " + e);
    }
    addBehaviour(new TellBehaviour());
    addBehaviour(new AskBehaviour());
  } 

  private class TellBehaviour extends Behaviour {

    boolean tellDone = false;

    public void action() {
      try{
        clips.eval("(clear)");
        clips.build("(deftemplate product(slot part-number)(slot name)(slot category)(slot price))");
        clips.assertString("(product (part-number 1)(name \"Widget\")(category \"Electronics\")(price 100))");
        clips.assertString("(product (part-number 2)(name \"Sprocket\")(category \"Automotive\")(price 10))");
        clips.assertString("(product (part-number 3)(name \"Spring\")(category \"Automotive\")(price 5))");

        clips.build("(deftemplate customer (slot customer-id)(multislot name)(multislot address)(slot phone))");
        clips.assertString("(customer (customer-id 1)(name \"John\" \"Doe\")(address \"Anytown, CA\")(phone \"555-1212\"))");
        clips.assertString("(customer (customer-id 2)(name \"Jane\" \"Marston\")(address \"Someplace, CA\")(phone \"555-1212\"))");
        clips.assertString("(customer (customer-id 3)(name \"Mary\" \"Smith\")(address \"Somewhere, CA\")(phone \"555-1212\"))");

        clips.build("(deftemplate order (slot order-number)(slot customer-id))");
        clips.assertString("(order (order-number 1)(customer-id 1))");
        clips.assertString("(order (order-number 2)(customer-id 2))");
        clips.assertString("(order (order-number 3)(customer-id 3))");

        clips.build("(deftemplate line-item (slot order-number)(slot part-number)(slot customer-id)(slot quantity (default 1)))");
        clips.assertString("(line-item (order-number 1)(part-number 1)(customer-id 1))");
        clips.assertString("(line-item (order-number 1)(part-number 2)(customer-id 1))");
        clips.assertString("(line-item (order-number 1)(part-number 3)(customer-id 1)(quantity 10))");

        clips.load("C:\\Users\\destr\\Documents\\7mo Semestre\\KBS\\CLIPS actividades\\HandsOn3\\work\\resources\\load-rules.clp");
      }
      catch(Exception e){
        System.out.println("Error: " + e);
      }
      tellDone = true;
    } 
    
    public boolean done() {
      if (tellDone) 
        return true;
      else
	      return false;
    }
  }

  private class AskBehaviour extends Behaviour {

    boolean askDone = false;

    public void action() {
      try{
        clips.eval("(rules)");
        clips.eval("(facts)");
        clips.run();
      }
      catch(Exception e){
        System.out.println("Error: " + e);
      }
      askDone = true;
    }
    
    public boolean done() {
      if (askDone) 
        return true;
      else
        return false;
    }
    
    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    }
  }	
}
