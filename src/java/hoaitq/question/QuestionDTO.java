/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.question;

import java.io.Serializable;

/**
 *
 * @author QH
 */
public class QuestionDTO implements Serializable, Comparable<QuestionDTO>{
    private int id;
    private String content;
    private int status;
    private String date;
    private int subId;
    
    public QuestionDTO(int id, String content, int status, String date, int subId) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.date = date;
        this.subId = subId;
    }

    public QuestionDTO() {
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
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
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
     * @return the subId
     */
    public int getSubId() {
        return subId;
    }

    /**
     * @param subId the subId to set
     */
    public void setSubId(int subId) {
        this.subId = subId;
    }

    @Override
    public int compareTo(QuestionDTO t) {
        return this.content.compareTo(t.getContent()) ;
    }
}
