/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.answer;

import java.io.Serializable;

/**
 *
 * @author QH
 */
public class AnswerDTO implements Serializable{
    private int id;
    private String content;
    private int isCorrect;
    private int questionID;

    public AnswerDTO() {
    }

    public AnswerDTO(int id, String content, int isCorrect, int questionID) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
        this.questionID = questionID;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the isCorrect
     */
    public int getIsCorrect() {
        return isCorrect;
    }

    /**
     * @param isCorrect the isCorrect to set
     */
    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    /**
     * @return the questionID
     */
    public int getQuestionID() {
        return questionID;
    }

    /**
     * @param questionID the questionID to set
     */
    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
    
    
    
}
