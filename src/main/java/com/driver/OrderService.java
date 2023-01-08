package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }
    public void addPartner(String partnerId){
        orderRepository.addPartner(partnerId);
    }
public void addOrderPartnerPair(String Oid,String Pid){
        orderRepository.addOrderPartnerPair(Oid,Pid);
}
    public Order getOrderById(String id){
        return orderRepository.getOrderById(id);
    }
    public DeliveryPartner getPartnerById(String id){
        return orderRepository.getPartnerById(id);
    }
public Integer getOrderCountByPartnerId(String id){
        return orderRepository.getOrderCountByPartnerId(id);
}
public List<String> getOrdersByPartnerId(String id){
        return orderRepository.getOrdersByPartnerId(id);
}
public List<String> getAllOrders(){
       return orderRepository.getAllOrders();
}
public Integer getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
}
public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String id){
      return  orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,id);

}
public String getLastDeliveryTimeByPartnerId(String id){
       return orderRepository.getLastDeliveryTimeByPartnerId(id);

}
public void deleteOrderById(String id){
        orderRepository.deleteOrderById(id);
}




public void deletePartnerById(String id){
        orderRepository.deletePartnerById(id);
}
}
