package org.board_games_shop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.board_games_shop.entity.goods.Accessories;
import org.board_games_shop.nodeModel.AddressNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "accessories")
@EqualsAndHashCode(callSuper = false, exclude = "accessories")
@Builder
@Entity
public class Producer extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;
    private String producerInfo;
    @JdbcTypeCode(SqlTypes.JSON)
    private AddressNode legalAddress;
    @OneToMany(mappedBy = "producer", cascade = CascadeType.PERSIST)
    private List<Accessories> accessories;
}
