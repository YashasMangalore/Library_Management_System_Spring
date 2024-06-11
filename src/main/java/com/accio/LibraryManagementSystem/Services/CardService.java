package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Enum.CardStatus;
import com.accio.LibraryManagementSystem.Models.LibraryCard;
import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Repository.CardRepository;
import com.accio.LibraryManagementSystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService
{
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private StudentRepository studentRepository;

    public String addNewCard()
    {
        LibraryCard card=new LibraryCard();
        card.setCardStatus(CardStatus.NEW);
        card.setNoOfBooksIssued(0);
        card=cardRepository.save(card);//after saving, it returns a modified card object will be returned as response
        return "The card has been generated with the ID: "+card.getCardID();
    }

    public String associateCardAndStudent(Integer studentID, Integer cardID)
    {
        LibraryCard card=cardRepository.findById(cardID).get();// .get() handles null also
        Student student=studentRepository.findById(studentID).get();
        card.setStudent(student);//this is how setting foreign keys
        card.setCardStatus(CardStatus.ACTIVE);

        cardRepository.save(card);
        return "Associating card and student cardID: "+cardID+" and studentID: "+studentID;
    }

    public String deleteCard(Integer cardId, String studentName) throws Exception
    {
        try
        {
            LibraryCard card = cardRepository.findById(cardId)
                    .orElseThrow(() -> new RuntimeException("Card-id not found"));
            Student student = studentRepository.findById(card.getStudent().getStudentID())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            if (!student.getName().equals(studentName)) {
                throw new Exception("Student name is not matching");
            }
            cardRepository.delete(card);
            return "The card of id: " + cardId + " has been successfully deleted.";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }
}
