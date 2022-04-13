package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @PostMapping("/add")
//    public String addItemV4(Item item) {
//        // 최종적으로 @ModelAttribute 를 생략이 가능하므로 위와 같이 표현할 수 있다
//
//        itemRepository.save(item);
//
//        return "basic/item";
//    }
    // 브라우저에서는 마지막으로 요청한 곳이 [POST] /add 이므로, 해당 end point 에서 새로고침을 할 경우
    // 다시 이전에 요청한 값과 함께 [POST] /add 로 요청을 하면서 중복된 데이터를 생성한다
    // 이를 해결하기 위해 Post/Redirect/Get 을 사용할 수 있다

//    @PostMapping("/add")
//    public String addItemV5(Item item) {
//        itemRepository.save(item);
//
//        return "redirect:basic/item/" + item.getId();
//    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        // RedirectAttributes 은 PathVariable 치환과 QueryParam 셋팅을 자동으로 해준다
        // PathVariable 치환은 같은 이름의 attribute key 가 있으면 해당 값으로 치환하고
        // 그 외에는 쿼리 파라미터로 만들어준다

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
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
