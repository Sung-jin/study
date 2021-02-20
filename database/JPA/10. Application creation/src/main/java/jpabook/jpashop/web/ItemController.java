package jpabook.jpashop.web;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 4. Time: 오후 9:07
 */
/*
스프링 MVC 는 HTTP 요청 정보와 @RequestMapping 의 속성 값을 비교해서 실행할 메소드를 찾는다.
 */
@Controller
public class ItemController {

    @Autowired ItemService itemService;

    @RequestMapping(value = "/items/new", method = RequestMethod.GET)
    public String createForm() {
        return "items/createItemForm";
    }

    @RequestMapping(value = "/items/new", method = RequestMethod.POST)
    public String create(Book item) {

        itemService.saveItem(item);
        return "redirect:/items";
        // 문자를 반환하는데, MVC 의 뷰 리졸버는 해당 정보를 바탕으로 실행할 뷰를 찾는다.
    }

    /**
     * 상품 수정 폼
     */
    @RequestMapping(value = "/items/{itemId}/edit", method = RequestMethod.GET)
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Item item = itemService.findOne(itemId);
        model.addAttribute("item", item);
        return "items/updateItemForm";
    }

    /**
     * 상품 수정
     */
    @RequestMapping(value = "/items/{itemId}/edit", method = RequestMethod.POST)
    public String updateItem(@ModelAttribute("item") Book item) {
        /*
        상품 수정을 통해 전달받은 객체는 엔티티 준영속 상태이다.
        그래서 해당 객체를 이용하여 수정해도 변경 감지 기능은 동작하지 않는다.
        이러한 준영속 상태의 객체를 수정하는 방법
            1. 변경 감지 기능 사용
                * 영속성 컨텍스트에서 엔티티를 다시 조회한 후, 데이터를 수정한다.
                * 전달받은 객체의 식별자 값(id) 를 이용하여 데이터베이스에서 조회를 한다.
                * 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있다.
            2. 병합 사용
                * 변경 감지와 같이 식별자 값으로 조회한 후에, merge 를 이용하여 해당 객체를 뷰단에서 전달받은 객체로 값을 채워넣는다.
                * 병합을 사용할 경우, 모든 속성이 변경된다.
         */

        itemService.saveItem(item);
        return "redirect:/items";
    }

    /**
     * 상품 목록
     */
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String list(Model model) {
        // 스프링 MVC 에서 제공하는 Model 객체에 데이터를 추가하면, 해당 정보를 뷰에 전달할 수 있다.

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

}
