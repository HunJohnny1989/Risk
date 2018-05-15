/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dto;

/**
 *
 * @author orsi
 */
public enum Phase {
    PLACE_TROOPS, ATTACK, REGROUP;
    
    @Override
    public String toString() {
        switch (this) {
            case PLACE_TROOPS:
                return "Place troops";
            case ATTACK:
                return "Attack territory";
            case REGROUP:
                return "Regroup troops";
        }
        return null;
    }
}
