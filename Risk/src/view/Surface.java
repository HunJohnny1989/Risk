/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import model.dto.GameField;
import model.dto.Territory;
import controller.ControllerInterface;
import model.dto.BattleResult;
import model.dto.Phase;

/**
 *
 * @author Eszti
 */
public class Surface extends javax.swing.JPanel{

    /**
     * Creates new form Surface
     */
    private GameField field;
    private Territory selectedTerritory;
    private Territory targetedTerritory;
    private java.util.List<Territory> subSelectedTerritories;
    private final AffineTransform transform = new AffineTransform();
    private InternalMapAction internalMapAction;
    private PlaceTroops placeTroopsDialog;
    private AttackTerritory attackTerritoryDialog;
    private RegroupTroops regroupTroopsDialog;
    private static final int HORIZONTALPADDING = 60;
    private static final int VERTICALPADDING = 60;
    private HashMap<model.dto.Color, Color> playerColors;
    private TexturePaint hatchTexturePaint;
    private Controller controller = new Controller();
    private boolean cancelledAction = false;
    private PlayerDefeated playerDefeatedDialog;

    public Surface() {
        initComponents();
        internalMapAction = InternalMapAction.NONE;

        placeTroopsDialog = new PlaceTroops((JFrame) SwingUtilities.windowForComponent(this), true, this);
        attackTerritoryDialog = new AttackTerritory((JFrame) SwingUtilities.windowForComponent(this), true, this);
        regroupTroopsDialog = new RegroupTroops((JFrame) SwingUtilities.windowForComponent(this), true, this);
        playerDefeatedDialog = new PlayerDefeated((JFrame) SwingUtilities.windowForComponent(this), true);
        playerColors = new HashMap<model.dto.Color, Color>() {
            {
                put(model.dto.Color.BLACK, Color.BLACK);
                put(model.dto.Color.BLUE, Color.BLUE);
                put(model.dto.Color.GREEN, Color.GREEN);
                put(model.dto.Color.PINK, Color.MAGENTA);
                put(model.dto.Color.RED, Color.RED);
                put(model.dto.Color.YELLOW, Color.YELLOW);
            }
        };
        hatchTexturePaint = getHatchPaint(Color.MAGENTA, Color.BLACK);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(153, 204, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (field == null)
        {
            return;
        }
        if (evt.getClickCount() > 1) {
            resetSurface();
            return;
        }

        Point2D transformedPoint = new Point2D.Double();
        try {
            transformedPoint = transform.inverseTransform(evt.getPoint(), transformedPoint);
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean myTerritory = false;
        for (Territory territory : field.getTerritories()) {
            if (territory.getShape().contains(transformedPoint)) {
                if (internalMapAction.equals(InternalMapAction.SELECT_NEIGHBOUR_ATTACK) || internalMapAction.equals(InternalMapAction.SELECT_NEIGHBOUR_REGROUP)) {
                    targetedTerritory = territory;
                } else {
                    selectedTerritory = territory;
                }
                if (territory.getOccupierPlayer().equals(controller.getCurrentPlayer())) {
                    myTerritory = true;
                }
            }
        }

        switch (controller.getCurrentPhase()) {
            case PLACE_TROOPS:
                if (!myTerritory) {
                    return;
                }
                internalMapAction = InternalMapAction.PLACETROOPS;
                break;
            case ATTACK:
                if (internalMapAction.equals(InternalMapAction.NONE) && myTerritory) {
                    internalMapAction = InternalMapAction.SELECT_NEIGHBOUR_ATTACK;
                } else if (internalMapAction.equals(InternalMapAction.SELECT_NEIGHBOUR_ATTACK) && subSelectedTerritories != null && subSelectedTerritories.contains(targetedTerritory)) {
                    internalMapAction = InternalMapAction.ATTACK;
                } else {
                    return;
                }
                break;
            case REGROUP:
                if (internalMapAction.equals(InternalMapAction.NONE) && myTerritory) {
                    internalMapAction = InternalMapAction.SELECT_NEIGHBOUR_REGROUP;
                } else if (internalMapAction.equals(InternalMapAction.SELECT_NEIGHBOUR_REGROUP) && subSelectedTerritories != null && subSelectedTerritories.contains(targetedTerritory)) {
                    internalMapAction = InternalMapAction.REGROUP;
                } else {
                    return;
                }
                break;
        }

        switch (internalMapAction) {
            case NONE:
                return;
            case PLACETROOPS:
                repaint();
                placeTroopsDialog.setRange(1, controller.getPlayerRemainingTroopCount());
                placeTroopsDialog.setVisible(true);
                if(selectedTerritory != null)
                {
                    addTroopToTerritory(selectedTerritory, placeTroopsDialog.getNumberOfTroops());
                }
                internalMapAction = InternalMapAction.NONE;
                break;
            case SELECT_NEIGHBOUR_ATTACK:
                subSelectedTerritories = selectedTerritory.getNeighbourEnemyTerritories();
                break;
            case SELECT_NEIGHBOUR_REGROUP:
                subSelectedTerritories = selectedTerritory.getNeighbourPlayerTerritories();
                break;
            case ATTACK:
                //TODO hány katonával támadsz még 1 panel mint a troopsdialog és kész
                attackTerritoryDialog.setRange(1, selectedTerritory.getTroopCount() - 1);//Legalább egy katonának maradnia kell
                attackTerritoryDialog.setVisible(true);

                if (cancelledAction || attackTerritoryDialog.getNumberOfTroops() == 0) {
                    cancelledAction = false;
                    return;
                }
                
                model.Player attackedPlayer = targetedTerritory.getOccupierPlayer();

                BattleResult result = controller.attackTerritory(selectedTerritory, targetedTerritory, attackTerritoryDialog.getNumberOfTroops());

                if (result.hasTerritoryBeenConquered()) 
                {
                    transferTroopToTerritory(selectedTerritory, targetedTerritory, attackTerritoryDialog.getNumberOfTroops() - result.getAttackerTroopLossCount());
                    subSelectedTerritories = selectedTerritory.getNeighbourEnemyTerritories();
                    if(attackedPlayer.hasKilledPlayer(attackedPlayer.getColor()))
                    {
                        playerDefeatedDialog.setText(attackedPlayer.getName());
                        playerDefeatedDialog.setVisible(true);
                    }
                }

                internalMapAction = InternalMapAction.SELECT_NEIGHBOUR_ATTACK;
                break;
            case REGROUP:
                regroupTroopsDialog.setRange(1, selectedTerritory.getTroopCount() - 1);//Legalább egy katonának maradnia kell
                regroupTroopsDialog.setVisible(true);

                if (cancelledAction || regroupTroopsDialog.getNumberOfTroops() == 0) {
                    cancelledAction = false;
                    return;
                }
                controller.transfer(selectedTerritory, targetedTerritory, regroupTroopsDialog.getNumberOfTroops());
                targetedTerritory = null;
                internalMapAction = InternalMapAction.SELECT_NEIGHBOUR_REGROUP;
                break;
        }
        this.repaint();
    }//GEN-LAST:event_formMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private TexturePaint getHatchPaint(Color backColor, Color stripeColor) {
        BufferedImage bufferedImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        /*g2.setColor(backColor);
        g2.fillRect(0, 0, 5, 5);*/
        g2.setColor(stripeColor);
        g2.drawLine(0, 2, 4, 2); // \
        //g2.drawLine(0, 5, 5, 0); // /

        // paint with the texturing brush
        Rectangle2D rect = new Rectangle2D.Double(0, 0, 5, 5);
        return new TexturePaint(bufferedImage, rect);
    }

    private void doDrawing(Graphics g) {
        if (field == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.transform(transform);
        for (Territory territory : field.getTerritories()) {
            g2d.setPaint(new Color(210, 180, 140));
            g2d.fill(territory.getShape());
            if (territory.getOccupierPlayer() != null) {
                
                colorOccupiedContinent(territory, g2d);       
                
                g2d.setPaint(playerColors.get(territory.getOccupierPlayer().getColor()));
                if (territory.getCenterPoint() != null) {
                    g2d.drawString(Integer.toString(territory.getTroopCount()), territory.getCenterPoint().x, territory.getCenterPoint().y);
                }
            }
        }
        if (selectedTerritory != null) {
            g2d.setPaint(this.hatchTexturePaint);
            g2d.fill(selectedTerritory.getShape());
            g2d.setPaint(Color.BLACK);
            g2d.draw(selectedTerritory.getShape());
            if (selectedTerritory.getOccupierPlayer() != null) {
                g2d.setPaint(playerColors.get(selectedTerritory.getOccupierPlayer().getColor()));
                if (selectedTerritory.getCenterPoint() != null) {
                    g2d.drawString(Integer.toString(selectedTerritory.getTroopCount()), selectedTerritory.getCenterPoint().x, selectedTerritory.getCenterPoint().y);
                }
            }
        }
        if (subSelectedTerritories != null) {
            for (Territory territory : subSelectedTerritories) {
                Color playerColor = playerColors.get(territory.getOccupierPlayer().getColor());
                g2d.setPaint(playerColor);
                g2d.draw(territory.getShape());
                /*g2d.setPaint(this.hatchTexturePaint);
                g2d.fill(territory.getShape());
                g2d.setPaint(playerColor);
                g2d.draw(territory.getShape());
                if (territory.getOccupierPlayer() != null){
                    g2d.setPaint(playerColor);
                    if (territory.getCenterPoint()!=null){
                        g2d.drawString(Integer.toString(territory.getTroopCount()), territory.getCenterPoint().x, territory.getCenterPoint().y);
                    }
                }*/
            }
        }
        g2d.dispose();
    }
    
    /**
    *
    * @author orsi
    */
    private void colorOccupiedContinent(Territory territory, Graphics2D g2d){
        if(territory.getContinent().getOccupierPlayer() != null){
                    Color c = playerColors.get(territory.getOccupierPlayer().getColor());
                    g2d.setPaint(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0x33));
                    g2d.fill(territory.getShape());
                }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    public void resetSurface() {
        this.selectedTerritory = null;
        this.targetedTerritory = null;
        this.subSelectedTerritories = null;
        this.repaint();
        internalMapAction = InternalMapAction.NONE;
    }

    private void addTroopToTerritory(Territory territory, int count) {
        controller.removeAvailableTroops(count);
        if(territory == null){
            System.out.println("ajaj");
        }
        territory.addTroops(count);
    }

    private void transferTroopToTerritory(Territory from, Territory to, int count) {
        controller.transfer(from, to, count);
    }

    void setGameField(GameField field) {
        this.field = field;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double horizontalScale = (screenSize.getWidth() - 2 * HORIZONTALPADDING) / field.getDimension().getWidth();
        double verticalScale = (screenSize.getHeight() - 2 * VERTICALPADDING) / field.getDimension().getHeight();
        double scale = Math.min(horizontalScale, verticalScale);
        transform.scale(scale, scale);
        transform.translate((screenSize.getWidth() - scale * field.getDimension().getWidth()) / 2 - 15, 0);
        repaint();
    }

    public boolean isCancelledAction() {
        return cancelledAction;
    }

    public void setCancelledAction(boolean cancelledAction) {
        this.cancelledAction = cancelledAction;
    }

}

enum InternalMapAction {
    NONE, SELECT_NEIGHBOUR_ATTACK, SELECT_NEIGHBOUR_REGROUP, PLACETROOPS, ATTACK, REGROUP
}
