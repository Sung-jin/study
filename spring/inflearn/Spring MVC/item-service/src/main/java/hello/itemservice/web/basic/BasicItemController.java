package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(
//            @RequestParam String itemName,
//            @RequestParam int price,
//            @RequestParam Integer quantity,
//            Model model
//    ) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item) {
//        itemRepository.save(item);
//
////        model.addAttribute("item", item);
//        // @ModelAttribute("item") 와 같이 지정된 String 으로 model 에 자동 추가된다
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item) {
//        // ModelAttribute 에 값을 지정하지 않으면, 해당 데이터 타입의 첫 글자를 소문자로 변경해서 넣는다
//        // Item -> item
//        // model.addAttribute("item", item);
//
//        itemRepository.save(item);
//
//        return "basic/item";
//    }

    @PostMapping("/add")
    public String addItemV4(Item item) {
        // 최종적으로 @ModelAttribute 를 생략이 가능하므로 위와 같이 표현할 수 있다

        itemRepository.save(item);

        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }
}
