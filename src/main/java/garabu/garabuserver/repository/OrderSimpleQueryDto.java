package garabu.garabuserver.repository;

import garabu.garabuserver.domain.Address;
import garabu.garabuserver.domain.Order;
import garabu.garabuserver.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderSimpleQueryDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
            this.orderId = orderId;
            this.name = name;
            this.orderDate = orderDate;
            this.address = address;
            this.orderStatus = orderStatus;
        }
    }

