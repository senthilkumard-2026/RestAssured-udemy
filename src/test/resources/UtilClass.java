import pojo_Payloads.OrderDetails;

public class UtilClass {

public CretaeOrderPayload OrderDetailsPL (Stirng prodId) {
		
		OrderDetails details = new OrderDetails();
		details.setCountry("India");
		details.setProductOrderedId(prodId);
		
		List<E> <OrderDetails> orderList = new ArrayList<OrderDetails>();
		orderList.add(details);
		
		CretaeOrderPayload order = new CretaeOrderPayload();
		order.setOrders(orderList);
		return order;
	}

}