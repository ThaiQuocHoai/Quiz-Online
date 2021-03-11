/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.history;

import java.io.Serializable;

/**
 *
 * @author QH
 */
public class HistoryDetailDTO implements Serializable {

    private int hisID;
    private int quesID;
    private String question;
    private String correct_answer;
    private String user_answer;

    public HistoryDetailDTO() {
    }

    public HistoryDetailDTO(int hisID, int quesID, String question, String correct_answer, String user_answer) {
        this.hisID = hisID;
        this.quesID = quesID;
        this.question = question;
        this.correct_answer = correct_answer;
        this.user_answer = user_answer;
    }

    /**
     * @return the hisID
     */
    public int getHisID() {
        return hisID;
    }

    /**
     * @param hisID the hisID to set
     */
    public void setHisID(int hisID) {
        this.hisID = hisID;
    }

    /**
     * @return the quesID
     */
    public int getQuesID() {
        return quesID;
    }

    /**
     * @param quesID the quesID to set
     */
    public void setQuesID(int quesID) {
        this.quesID = quesID;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the correct_answer
     */
    public String getCorrect_answer() {
        return correct_answer;
    }

    /**
     * @param correct_answer the correct_answer to set
     */
    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    /**
     * @return the user_answer
     */
    public String getUser_answer() {
        return user_answer;
    }

    /**
     * @param user_answer the user_answer to set
     */
    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

}
