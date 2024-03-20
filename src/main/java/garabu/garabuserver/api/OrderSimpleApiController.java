package garabu.garabuserver.api;


import garabu.garabuserver.domain.Address;
import garabu.garabuserver.domain.Order;
import garabu.garabuserver.domain.OrderStatus;
import garabu.garabuserver.repository.OrderRepository;
import garabu.garabuserver.repository.OrderSearch;
import garabu.garabuserver.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ~~ToOne 일때.
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }
    //v3 v4 누가 좋다는 어렵다. 트레이드오프? v3는 order를 가지고왔을떄 원하는것만 join fetch함. 외부의 모습을 건드리지 않는다. order의 내부의 원하는것만.???
    //v4는 sql짜듯이 JPQL을 짠것. 화면에는 최적화 됨. 하지만 재사용성이 없음 해당 DTO가 필요할때만 쓸수 있음 성능면에선 v4가 좋다.
    //v3는 엔티티를 조회한것임. 비즈니스로직을써서 데이터를 변경할수 있음. v4 DTO로 조회한것이라 할수 있는 게 없음.
    @Data
static class SimpleOrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}