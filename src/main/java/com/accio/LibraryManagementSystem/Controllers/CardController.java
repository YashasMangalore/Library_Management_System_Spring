package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
public class CardController
{
    @Autowired
    private CardService cardService;

    @PostMapping("/add")
    public String addCard()
    {
        return cardService.addNewCard();
    }

    @PutMapping("/associate-card-student")
    public String associateCardAndStudent(@RequestParam("cardID")Integer cardID,
                                          @RequestParam("studentID")Integer studentID)
    {
        return cardService.associateCardAndStudent(cardID,studentID);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteCard(@RequestParam Integer cardId, @RequestParam String studentName) throws Exception
    {
        try
        {
            String response=cardService.deleteCard(cardId,studentName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
