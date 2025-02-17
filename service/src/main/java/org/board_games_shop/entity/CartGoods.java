package org.board_games_shop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.board_games_shop.entity.goods.Goods;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"goods", "cart", "order"}, callSuper = false)
@ToString(exclude = {"goods", "cart", "order"})
@Builder
@Entity
public class CartGoods extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    private Integer totalGoods;

    public void setCart(Cart cart) {
        this.cart = cart;
        this.cart.getCartGoods().add(this);
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
        this.goods.getCartGoods().add(this);
    }
}
