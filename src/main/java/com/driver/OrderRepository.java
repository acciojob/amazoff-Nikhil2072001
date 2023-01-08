package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
HashMap<String,Order> orderHashMap = new HashMap<>();
HashMap<String,DeliveryPartner>deliveryPartnerHashMap = new HashMap<>();
HashMap<String, List<String>> partnerOrderHashMap = new HashMap<>();
List <String> assignedOrders = new ArrayList<>();
    public void addOrder(Order order){
        String s = order.getId();
        orderHashMap.put(s,order);
    }
    public void addPartner(String id){
        DeliveryPartner p = new DeliveryPartner(id);
        deliveryPartnerHashMap.put(id,p);
    }
public void addOrderPartnerPair(String Oid,String Pid){
        List<String> l = new ArrayList<>();
        if(!assignedOrders.contains(Oid)){
            assignedOrders.add(Oid);

        if(partnerOrderHashMap.containsKey(Pid)){
            l=partnerOrderHashMap.get(Pid);
            if(l.contains(Oid))
                return;
            else{
                l.add(Oid);

            }
        }else{
            l.add(Oid);
            partnerOrderHashMap.put(Pid,l);
        }
        }
}
    public Order getOrderById(String id){
        Order order = orderHashMap.get(id);
        return order;
    }
    public DeliveryPartner getPartnerById(String id){
        DeliveryPartner dp = deliveryPartnerHashMap.get(id);
        return dp;
    }
    public Integer getOrderCountByPartnerId(String id){
        int n = partnerOrderHashMap.get(id).size();
        return n;
    }
    public List<String> getOrdersByPartnerId(String id){
        List<String> s = partnerOrderHashMap.get(id);
        return s;
    }
    public List<String> getAllOrders(){
        List<String> l = new ArrayList<>();
        for(String s:partnerOrderHashMap.keySet()){
            for(String p: partnerOrderHashMap.get(s)){
                if(!l.contains(p)){
                    l.add(p);
                }
            }
        }
        for(String s:orderHashMap.keySet()){
            if(!l.contains(s)){
                l.add(s);
            }
        }
        return l;
    }
    public Integer getCountOfUnassignedOrders(){
        List<String> l = new ArrayList<>();
        for(String s:orderHashMap.keySet()){
            if(l.contains(s)){
                l.add(s);
            }
        }
        int size = l.size();
        int count =0;
        for(String s:partnerOrderHashMap.keySet()){
            for(String p: partnerOrderHashMap.get(s)){
                if(l.contains(p)){
                  count++;
                }
            }
        }
        return size-count;
    }
//public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String id){
//        return 0;
//}
//public String getLastDeliveryTimeByPartnerId(String id){
//        return null;
//}

    public String getLastDeliveryTimeByPartnerId(String id){
        int maxTime = 0;
        if(deliveryPartnerHashMap.containsKey(id) && partnerOrderHashMap.containsKey(id)){
            List<String> orderIds = partnerOrderHashMap.get(id);
            for (String orderId : orderIds) {
                maxTime = Math.max(maxTime, orderHashMap.get(orderId).getDeliveryTime());
            }
        }

        int hh = maxTime/60;
        int mm = maxTime%60;
        String time = "";
        if(hh!=0) {
            if (hh < 10) {
                time += "0" + hh;
            } else {
                time += hh;
            }
        }else{
            time+="00";
        }
        time+=":";
        if(mm!=0) {
            if (mm < 10) {
                time += "0" + mm;
            } else {
                time += mm;
            }
        }else{
            time+="00";
        }
        return time;
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String id){
        int count =0;
        int givenTime =Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3,5));

        if(deliveryPartnerHashMap.containsKey(id) && partnerOrderHashMap.containsKey(id)){
            List<String> orderIds = partnerOrderHashMap.get(id);
            for (String orderId : orderIds) {
                if( orderHashMap.get(orderId).getDeliveryTime()>givenTime){
                    count++;
                }
            }
        }
        return count;
    }
    public void deletePartnerById(String Pid){
        List<String > l = new ArrayList<>();
        if(partnerOrderHashMap.containsKey(Pid))
            l = partnerOrderHashMap.get(Pid);
            partnerOrderHashMap.remove(Pid);
            for(int i=0;i<assignedOrders.size();i++){
                String s = assignedOrders.get(i);
                if(l.contains(s))
                    assignedOrders.remove(i);
            }
    }
    public void deleteOrderById(String id){
        List<String> l = new ArrayList<>();
        if(orderHashMap.containsKey(id))
            orderHashMap.remove(id);
        for(String s: partnerOrderHashMap.keySet()){
            l = partnerOrderHashMap.get(s);
            if(l.contains(id)){
               for(int i=0;i<l.size();i++){
                   if(l.get(i).equals(id)){
                       l.remove(i);

                   }
               }
               partnerOrderHashMap.put(s,l);
               break;
            }

        }
        if (assignedOrders.contains(id)) {

            for(int i=0;i<assignedOrders.size();i++){
                if(assignedOrders.get(i).equals(id)){
                    assignedOrders.remove(i);
                }
            }
        }
    }


}
