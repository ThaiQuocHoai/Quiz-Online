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
public class HistoryDTO implements Serializable, Comparable<HistoryDTO> {

    private int id;
    private float score;
    private String date;
    private int subjectID;
    private String email;

    public HistoryDTO() {
    }

    public HistoryDTO(int id, float score, String date, int subjectID, String email) {
        this.id = id;
        this.score = score;
        this.date = date;
        this.subjectID = subjectID;
        this.email = email;
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
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the subjectID
     */
    public int getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(HistoryDTO t) {
        if(this.id > t.getId()) return -1;
        return 1;
    }

}
