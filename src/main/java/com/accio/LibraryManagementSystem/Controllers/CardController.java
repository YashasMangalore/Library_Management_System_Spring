package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Enum.CardStatus;
import com.accio.LibraryManagementSystem.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
public class CardController
{
    @Autowired
    private CardService cardService;

    @PostMapping("add")
    public String addCard()
    {
        return cardService.addNewCard();
    }

    @PutMapping("associateCardAndStudent")
    public String associateCardAndStudent(@RequestParam("cardID")Integer cardID,
                                          @RequestParam("studentID")Integer studentID)
    {
        return cardService.associateCardAndStudent(cardID,studentID);
    }
}
