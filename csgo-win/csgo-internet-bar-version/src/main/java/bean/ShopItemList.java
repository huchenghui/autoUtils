package bean;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopItemList {

    private String keywords;
    private String orderByKey;
    private String orderByAsc;
    private String exterior;
    private String quality;
    private String rarity;
    private String type;
    private String weapon;

}
