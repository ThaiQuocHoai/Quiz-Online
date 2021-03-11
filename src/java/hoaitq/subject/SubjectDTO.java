/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.subject;

import java.io.Serializable;

/**
 *
 * @author QH
 */
public class SubjectDTO implements Serializable{
    private int id;
    private String name;
    private int numques;
    private int numtime;

    public SubjectDTO() {
    }

    public SubjectDTO(int id, String name, int numques, int numtime) {
        this.id = id;
        this.name = name;
        this.numques = numques;
        this.numtime = numtime;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the numques
     */
    public int getNumques() {
        return numques;
    }

    /**
     * @param numques the numques to set
     */
    public void setNumques(int numques) {
        this.numques = numques;
    }

    /**
     * @return the numtime
     */
    public int getNumtime() {
        return numtime;
    }

    /**
     * @param numtime the numtime to set
     */
    public void setNumtime(int numtime) {
        this.numtime = numtime;
    }
    
    
}
