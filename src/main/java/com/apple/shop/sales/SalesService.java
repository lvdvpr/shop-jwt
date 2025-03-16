package com.apple.shop.sales;

import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;

    public void saveSales(String title,
                          Integer price,
                          Integer count,
                          Authentication auth) {
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(title);
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = new Member();
        member.setId(user.getId());
        sales.setMember(member);
        salesRepository.save(sales);
    }
}
