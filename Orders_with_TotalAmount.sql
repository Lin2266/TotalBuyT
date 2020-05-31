SELECT orders.id,customer_id,customers.name, order_time, 
SUM(price*quantity) AS total_amount, payment_type, shipping_type

FROM orders 
JOIN customers ON orders.customer_id=customers.id
JOIN order_items ON orders.id=order_items.order_id

WHERE customer_id="A223456781" and order_time >= "2015-9-21" AND order_time < "2015-9-23"
GROUP BY orders.id;
